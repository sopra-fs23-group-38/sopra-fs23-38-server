package ch.uzh.ifi.hase.soprafs23.service;

import ch.uzh.ifi.hase.soprafs23.Auxiliary.auxiliary;
import ch.uzh.ifi.hase.soprafs23.entity.Answer;
import ch.uzh.ifi.hase.soprafs23.entity.Comment;
import ch.uzh.ifi.hase.soprafs23.entity.Notification;
import ch.uzh.ifi.hase.soprafs23.entity.User;
import ch.uzh.ifi.hase.soprafs23.repository.AnswerRepository;
import ch.uzh.ifi.hase.soprafs23.repository.CommentRepository;
import ch.uzh.ifi.hase.soprafs23.repository.NotificationRepository;
import ch.uzh.ifi.hase.soprafs23.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import java.sql.Date;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.*;

@Service
public class CommentService {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private UserService userService;
    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private AnswerRepository answerRepository;

    @Autowired
    private NotificationRepository notificationRepository;

    @Transactional
    public String createComment(Integer answerID, String content, Integer parentId, HttpServletRequest request) {
        Map<String, Object> infobody = new HashMap<>();

        //评论内容长度是否合法
        if (content != null && content.length() > 0 && content.length() <= 200) {
            Integer who_comments = auxiliary.extractUserID(request);
            Optional<User> user = userRepository.findById(who_comments);
            //验证用户已登录
            if (user.isPresent()) {
                User comment_user = user.orElse(null);

                Optional<Answer> answer = answerRepository.findById(answerID);
                if (answer.isPresent()) {
                    Answer comment_answer = answer.orElse(null);
                    comment_answer.setComment_count(comment_answer.getComment_count()+1);

                    Comment newComment = new Comment();
                    newComment.setWho_comments(who_comments);
                    newComment.setContent(content);
                    newComment.setAnswer_ID(answerID);
                    newComment.setChange_time(Date.from(Instant.now()));
                    newComment.setParentCommentId(parentId);

                    commentRepository.save(newComment);

                    if (newComment.getId() != null) {
                        infobody.put("success", "true");


                        if (parentId != null){ //这里是子comment对于母comment的提醒
                            infobody.put("type", "comment");
                            Optional<Comment> p_Comment = commentRepository.findById(parentId);
                            if (p_Comment.isPresent()){
                                Comment parentComment = p_Comment.orElse(null);
                                User p_Comment_user = userRepository.findById(parentComment.getWho_comments()).orElse(null);
                                if (p_Comment_user != null){
                                    Notification notification = new Notification();
                                    notification.setToUserId(p_Comment_user.getId());
                                    notification.setUrl("/question/answer/" + comment_answer.getId());
                                    notification.setContent(comment_user.getUsername() + " replied to your comment.");
                                    notification.setCreateTime(new Timestamp(System.currentTimeMillis()));
                                    notificationRepository.save(notification);

                                    p_Comment_user.setHasNew(p_Comment_user.getHasNew()+1);
                                    userRepository.save(p_Comment_user);
                                }
                            }
                            return auxiliary.mapObjectToJson(infobody);

                        }// 这里是母comment对于回答的提醒
                        infobody.put("type", "answer");
                        Notification notification = new Notification();
                        notification.setToUserId(comment_answer.getWho_answers());
                        notification.setUrl("/question/answer/" + comment_answer.getId());
                        notification.setContent(comment_user.getUsername() + " commented your answer.");
                        notification.setCreateTime(new Timestamp(System.currentTimeMillis()));
                        notificationRepository.save(notification);

                        Optional<User> c_ans_er = userRepository.findById(comment_answer.getWho_answers());
                        if (c_ans_er.isPresent()) {
                            User a_ans_user = c_ans_er.orElse(null);
//                            a_ans_user.setHasNew(true);
                            a_ans_user.setHasNew(a_ans_user.getHasNew()+1);
                            userRepository.save(a_ans_user);
                        }
                    }
                }
            }
        }
        return auxiliary.mapObjectToJson(infobody);
    }

//    public Boolean parentJudgement(Comment comment, Integer parentId){
//        if (comment.getId() == parentId){
//            return false;
//        }
//        while(comment.getParentCommentId() != null){
//            comment = commentRepository.findById(comment.getParentCommentId()).orElse(null);
//        }
//        return Objects.equals(comment.getId(), parentId);
//    }

    @Transactional
    public String getAllComments(Integer answerID) {
        List<Map<String, Object>> infobody = new ArrayList<>();

        List<Comment> all_comments_to_ans = commentRepository.findByAnswerIdOrderByTimeAsc(answerID);
        List<Comment> parent_comments_list = new ArrayList<>();
        for (Comment c:all_comments_to_ans){
            if (c.getParentCommentId() == null) {
                parent_comments_list.add(c);
            }
        }
        for (Comment c:parent_comments_list){
            Map<String, Object> commentMap = new HashMap<>();
            Optional<User> c_user = userRepository.findById(c.getWho_comments());
            if(c_user.isPresent()){
                User comment_user = c_user.get();
                commentMap.put("author",comment_user.getUsername());
            }
            commentMap.put("id",c.getId());
            commentMap.put("time",c.getChange_time());
            commentMap.put("content",c.getContent());
            List<Map<String,Object>> replieslist = new ArrayList<>();
            addRepliesToList(replieslist,c); //递归子函数来加replies
            commentMap.put("replies",replieslist);
            infobody.add(commentMap);
        }

        return auxiliary.CommentListToJson(infobody);
    }

    private void addRepliesToList(List<Map<String, Object>> repliesList, Comment comment) {
        List<Comment> replies = commentRepository.findByParentID(comment.getId()); //最后一个子comment因为没有任何回复了，所以replies为空list
        for (Comment reply : replies) {
            Map<String, Object> replyMap = new HashMap<>();
            Optional<User> r_user = userRepository.findById(reply.getWho_comments());
            if (r_user.isPresent()){
                User reply_user = r_user.get();
                replyMap.put("author",reply_user.getUsername());
            }
            replyMap.put("id", reply.getId());
            replyMap.put("time", reply.getChange_time());
            replyMap.put("content", reply.getContent());
            List<Map<String, Object>> repliesToReplyList = new ArrayList<>();
            addRepliesToList(repliesToReplyList, reply);
            replyMap.put("replies", repliesToReplyList);
            repliesList.add(replyMap);
        }
    }

//    @Transactional
//    public String getAllComments(Integer answerID) {
//        List<List<Map<String, Object>>> infobody = new ArrayList<>();
//        List<Comment> all_comments_to_ans = commentRepository.findByAnswerIdOrderByTimeAsc(answerID); //把数据库里的这个answer下的所有comment都找到并按时间排好序
//
//        List<Comment> parent_comments_list = new ArrayList<>();
//        for (Comment c: all_comments_to_ans){ //从所有comment里找出所有的母comment并把它们放到另外一个list里
//            if (c.getParentCommentId() == null){
//                parent_comments_list.add(c);
//                //all_comments_to_ans.removeIf(comment -> comment.equals(c));
//            }
//        }
//        parent_comments_list.sort(Comparator.comparing(Comment::getChange_time).reversed());
//
//        for (Comment c: parent_comments_list){ //把所有的母comment变成最终的形式放到infobody里
//            List<Map<String, Object>> innerList = new ArrayList<>();
//            Map<String, Object> map_parent = new HashMap<>();
//            map_parent.put("id",c.getId());
//            Optional<User> c_user = userRepository.findById(c.getWho_comments());
//            if (c_user.isPresent()){
//                User comment_user = c_user.get();
//                map_parent.put("author",comment_user.getUsername());
//            }
//            map_parent.put("content",c.getContent());
//            map_parent.put("reply to","null");
//            map_parent.put("time",c.getChange_time());
//            innerList.add(map_parent);
//            infobody.add(innerList);
//        }
//
//        while (!parent_comments_list.isEmpty()){
//            Comment current = parent_comments_list.get(0);
//            for(Comment c:all_comments_to_ans){
//                if (parentJudgement(c,current.getId())){ //这个comment是目前审视的母comment的子comment
//                    Map<String, Object> map_child = new HashMap<>();
//                    map_child.put("id",c.getId());
//                    Optional<User> c_user = userRepository.findById(c.getWho_comments());
//                    if (c_user.isPresent()){
//                        User comment_user = c_user.get();
//                        map_child.put("author",comment_user.getUsername());
//                    }
//                    map_child.put("content",c.getContent());
//                    Optional<Comment> p_comment = commentRepository.findById(c.getParentCommentId());
//                    if(p_comment.isPresent()){
//                        Comment parent_comment = p_comment.get();
//                        Optional<User> p_user = userRepository.findById(parent_comment.getWho_comments());
//                        if(p_user.isPresent()){
//                            User parent_user = p_user.get();
//                            map_child.put("reply to",parent_user.getUsername());
//                        }
//                    }
//                    map_child.put("time",c.getChange_time());
//                    for (List<Map<String, Object>> innerList : infobody){
//                        if (!innerList.isEmpty()){
//                            Object idValue = innerList.get(0).get("id");
//                            if (idValue == current.getId()){
//                                innerList.add(map_child);
//                                break;
//                            }
//                        }
//                    }
//                }
//            }
//            parent_comments_list.remove(0);
//        }
//
//        return auxiliary.nestedListToJson(infobody);
//
//    }

    @Transactional
    public String getCommentsByWho(Integer who_comments) {
        List<Map<String, Object>> resultList = new ArrayList<>();
        List<Comment> commentList = commentRepository.findByWhoCommentsOrderByTimeDesc(who_comments);
        if(commentList != null && commentList.size() > 0) {
            //使用Set来避免重复的数据库查询
            Set<String> commentTo_Set = new HashSet<>();
            //把要查询的对象放在Set里
            for(Comment c : commentList) {
                //如0-19，代表类别为回答、回答ID为19
                commentTo_Set.add(0 + "-" + c.getAnswer_ID());
            }
            //执行查询，结果保存在两个不同的Map里，分别对应回答评论和文章评论
            Map<Integer, String> answerCommentToWords = new HashMap<>();
            //Map<Integer, String> articleCommentToWords = new HashMap<>();

            for(String s : commentTo_Set) {
                Integer to_comment_id = Integer.parseInt(s.substring(2));
                //执行回答作者昵称查询
                List<String> answererNickname = answerRepository.getAnswererUsernameByAnswerId(to_comment_id);
                if(answererNickname != null && answererNickname.size() == 1) {
                    StringBuilder sb = new StringBuilder(answererNickname.get(0));
                    if(sb.length() > 10) {
                        sb.delete(8, sb.length());
                        sb.append("...");
                    }
                    sb.append("的回答");
                    answerCommentToWords.put(to_comment_id, sb.toString());
                }
            }
            //填充结果集
            for(Comment c : commentList) {
                Map<String, Object> eachCommentMap = new HashMap<>();
                eachCommentMap.put("commentTime", c.getChange_time());
                eachCommentMap.put("commentContent", c.getContent());
                eachCommentMap.put("commentType", 0);
                eachCommentMap.put("commentId", c.getId());


                eachCommentMap.put("commentTo", answerCommentToWords.get(c.getAnswer_ID()));
                eachCommentMap.put("clickId", c.getAnswer_ID());


                resultList.add(eachCommentMap);
            }
        }

        return auxiliary.listMapToJson(resultList);

    }

//    @Transactional
//    public String createComment(Integer answerID, String content, HttpServletRequest request) {
//        Map<String, Object> infobody = new HashMap<>();
//
//        //评论内容长度是否合法
//        if (content != null && content.length() > 0 && content.length() <= 200) {
//            Integer who_comments = auxiliary.extractUserID(request);
//            Optional<User> user = userRepository.findById(who_comments);
//            //验证用户已登录
//            if (user.isPresent()) {
//                User comment_user = user.orElse(null);
//
//                Optional<Answer> answer = answerRepository.findById(answerID);
//                if (answer.isPresent()) {
//                    Answer comment_answer = answer.orElse(null);
//                    comment_answer.setComment_count(comment_answer.getComment_count()+1);
//
//                    Comment newComment = new Comment();
//                    newComment.setWho_comments(who_comments);
//                    newComment.setContent(content);
//                    newComment.setAnswer_ID(answerID);
//                    newComment.setChange_time(Date.from(Instant.now()));
//
//                    commentRepository.save(newComment);
//
//                    if (newComment.getId() != null) {
//                        infobody.put("success", "true");
//                        infobody.put("type", "answer");
//
//                        Notification notification = new Notification();
//                        notification.setToUserId(comment_answer.getWho_answers());
//                        notification.setUrl("/question/answer/" + comment_answer.getId());
//                        notification.setContent(comment_user.getUsername() + " commented your answer");
//                        notification.setCreateTime(new Timestamp(System.currentTimeMillis()));
//                        notificationRepository.save(notification);
//
//
//                        Optional<User> c_ans_er = userRepository.findById(comment_answer.getWho_answers());
//                        if (c_ans_er.isPresent()) {
//                            User a_ans_user = c_ans_er.orElse(null);
////                            a_ans_user.setHasNew(true);
//                            a_ans_user.setHasNew(a_ans_user.getHasNew()+1);
//                            userRepository.save(a_ans_user);
//                        }
//                    }
//                }
//            }
//        }
//        return auxiliary.mapObjectToJson(infobody);
//    }

//    @Transactional
//    public String getAllComments(Integer answerID) {
//        List<Map<String, Object>> resultList = new ArrayList<>();
//        Map<String, Object> resultMap = new HashMap<>();
//        List<Comment> comments;
//        resultMap.put("success","false");
//
//        comments = commentRepository.findByAnswerIdOrderByTimeDesc(answerID);
//
//        Set<Integer> who_comments = new HashSet<>();
//        for(Comment c : comments) {
//            who_comments.add(c.getWho_comments());
//        }
//        Map<Integer, Map<String, Object>> who_comments_info = new HashMap<>();
//        for(Integer c : who_comments) {
//            Map<String, Object> user_info_body= new HashMap<>();
//            Optional<User> o_user = userRepository.findById(c);
//            if(o_user.isPresent()){
//                User user = o_user.orElse(null);
//                user_info_body.put("userId",c);
//                user_info_body.put("username",user.getUsername());
//            }
//            who_comments_info.put(c,user_info_body);
//        }
//
//        for (Comment comment : comments){
//            Map<String, Object> eachComment = new HashMap<>();
//            eachComment.put("commentator", who_comments_info.get(comment.getWho_comments()));
//            eachComment.put("comment", comment);
//            resultList.add(eachComment);
//        }
//        resultMap.put("isThatAll","true");
//        resultMap.put("success","true");
//        resultMap.put("comments",resultList);
//        return auxiliary.mapObjectToJson(resultMap);
//
//    }









}
