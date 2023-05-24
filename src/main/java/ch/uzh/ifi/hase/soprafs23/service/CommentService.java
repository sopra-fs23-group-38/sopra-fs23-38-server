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
import org.springframework.data.domain.PageRequest;
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

    int comment_count =0;

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
                    comment_answer.setComment_count(comment_answer.getComment_count() + 1);

                    Comment newComment = new Comment();
                    newComment.setWho_comments(who_comments);
                    newComment.setContent(content);
                    newComment.setAnswer_ID(answerID);
                    newComment.setChange_time(Date.from(Instant.now()));
                    newComment.setParentCommentId(parentId);

                    commentRepository.save(newComment);

                    infobody.put("success", "true");


                    if (parentId != null) { //这里是子comment对于母comment的提醒
                        infobody.put("type", "comment");
                        Optional<Comment> p_Comment = commentRepository.findById(parentId);
                        if (p_Comment.isPresent()) {
                            Comment parentComment = p_Comment.orElse(null);
                            User p_Comment_user = userRepository.findById(parentComment.getWho_comments()).orElse(null);
                            if (p_Comment_user != null) {
                                Notification notification = new Notification();
                                notification.setToUserId(p_Comment_user.getId());
                                notification.setUrl("/question/answer/" + comment_answer.getId());
                                notification.setContent(comment_user.getUsername() + " replied to your comment.");
                                notification.setCreateTime(new Timestamp(System.currentTimeMillis()));
                                notificationRepository.save(notification);

                                p_Comment_user.setHasNew(p_Comment_user.getHasNew() + 1);
                                userRepository.save(p_Comment_user);
                            }
                            Notification notification = new Notification();
                            notification.setToUserId(comment_answer.getWho_answers());
                            notification.setUrl("/question/answer/" + comment_answer.getId());
                            notification.setContent(comment_user.getUsername() + " commented your answer.");
                            notification.setCreateTime(new Timestamp(System.currentTimeMillis()));
                            notificationRepository.save(notification);

                            Optional<User> c_ans_er = userRepository.findById(comment_answer.getWho_answers());
                            if (c_ans_er.isPresent()) {
                                User a_ans_user = c_ans_er.orElse(null);
                                a_ans_user.setHasNew(a_ans_user.getHasNew() + 1);
                                userRepository.save(a_ans_user);
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
                        a_ans_user.setHasNew(a_ans_user.getHasNew() + 1);
                        userRepository.save(a_ans_user);
                    }
                }
            }
        }
        return auxiliary.mapObjectToJson(infobody);
    }

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
                commentMap.put("author_avatar",comment_user.getAvatarUrl());
            }
            commentMap.put("id",c.getId());
            commentMap.put("time",c.getChange_time());
            commentMap.put("content",c.getContent());
            List<Map<String,Object>> replieslist = new ArrayList<>();
            addRepliesToList(replieslist,c); //递归子函数来加replies
            commentMap.put("replies",replieslist);
            commentMap.put("comment_count",comment_count+1);
            infobody.add(commentMap);
            comment_count = 0;
        }
        int totalComments = countComments(infobody);

        if (!infobody.isEmpty()) {
            infobody.get(0).put("totalcount",totalComments);
        }

        return auxiliary.CommentListToJson(infobody);
    }

    public static int countComments(List<Map<String, Object>> comments) {
        int count = 0;
        for (Map<String, Object> comment : comments) {
            if (comment.containsKey("comment_count")) {
                count += (Integer) comment.get("comment_count");
            }
        }
        return count;
    }

    void addRepliesToList(List<Map<String, Object>> repliesList, Comment comment) {
        List<Comment> replies = commentRepository.findByParentID(comment.getId()); //最后一个子comment因为没有任何回复了，所以replies为空list
        for (Comment reply : replies) {
            Map<String, Object> replyMap = new HashMap<>();
            Optional<User> r_user = userRepository.findById(reply.getWho_comments());
            if (r_user.isPresent()){
                User reply_user = r_user.get();
                replyMap.put("author",reply_user.getUsername());
                replyMap.put("author_avatar",reply_user.getAvatarUrl());
            }
            replyMap.put("id", reply.getId());
            replyMap.put("time", reply.getChange_time());
            replyMap.put("content", reply.getContent());
            comment_count +=1;
            List<Map<String, Object>> repliesToReplyList = new ArrayList<>();
            addRepliesToList(repliesToReplyList, reply);
            replyMap.put("replies", repliesToReplyList);
            repliesList.add(replyMap);

        }
    }

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
                //---------增加
                Integer parentid = c.getParentCommentId();
                Optional<Answer> c_ans= answerRepository.findById(c.getAnswer_ID());
                if (c_ans.isPresent()){
                    Answer c_answer = c_ans.get();
                    eachCommentMap.put("comment_ans",c_answer.getContent());
                }
                if (parentid == null){
                    eachCommentMap.put("commentparent","Reply to Answer");
                }
                else{
                    eachCommentMap.put("commentparent","Reply to Comment");
                }
                //---------增加
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









}
