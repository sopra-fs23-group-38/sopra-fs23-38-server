package ch.uzh.ifi.hase.soprafs23.controller;

import ch.uzh.ifi.hase.soprafs23.Auxiliary.auxiliary;
import ch.uzh.ifi.hase.soprafs23.entity.Answer;
import ch.uzh.ifi.hase.soprafs23.entity.Comment;
import ch.uzh.ifi.hase.soprafs23.repository.AnswerRepository;
import ch.uzh.ifi.hase.soprafs23.repository.CommentRepository;
import ch.uzh.ifi.hase.soprafs23.service.AnswerService;
import ch.uzh.ifi.hase.soprafs23.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/comment")
public class CommentController {

    @Autowired
    private CommentService commentService;
    @Autowired
    private CommentRepository commentRepository;
    @Autowired
    private AnswerRepository answerRepository;
    private final SimpMessagingTemplate messagingTemplate;
    @Autowired
    private AnswerService answerService;

    public CommentController(SimpMessagingTemplate messagingTemplate) {
        this.messagingTemplate = messagingTemplate;
    }

    @PostMapping("/createComment")
    public String createComment(@RequestParam("ID") String ID,
                                @RequestParam("content") String content,
                                @RequestParam(value = "parentId", required = false) Integer parentId,
                                HttpServletRequest request) {
        int id = Integer.parseInt(ID);
        return commentService.createComment(id, content, parentId,request);

    }

    @GetMapping("/getAllComments")
    public String getAllComments(@RequestParam("ID") String ID) {
        int id = Integer.parseInt(ID);
        return commentService.getAllComments(id);

    }

    @GetMapping("/getCommentsByWho")
    public String getCommentsByWho(@RequestParam("who_comments") String who_comments) {
        int id = Integer.parseInt(who_comments);
        return commentService.getCommentsByWho(id);
    }

    @PostMapping("/update")
    public String updateComment(@RequestParam("commentId") Integer commentId,
                             @RequestParam("content") String content) {
        Optional<Comment> byId = commentRepository.findById(commentId);
        Map<String, Object> resultMap = new HashMap<>();
        resultMap.put("success","false");
        if (byId.isPresent()) {
            resultMap.put("success","true");
            Comment comment = byId.get();
            comment.setContent(content);
            commentRepository.save(comment);
        }
        return auxiliary.mapObjectToJson(resultMap);
    }

    @DeleteMapping("/delete")
    public String deleteById(@RequestParam("commentId") Integer commentId) {
        int questionID = 0;
        Optional<Comment> byId = commentRepository.findById(commentId);
        if (byId.isPresent()) {
//            resultMap.put("success","true");
            Comment comment = byId.get();
            Integer ab = comment.getAnswer_ID();
            Optional<Answer> byasd = answerRepository.findById(ab);

            if (byasd.isPresent()){
                Answer sad = byasd.get();
                questionID = sad.getQuestion_id();
                sad.setComment_count(sad.getComment_count()-1);
                answerRepository.save(sad);
            }
        }
        commentRepository.deleteCommentByParent_ID(commentId);
        commentRepository.deleteById(commentId);
        Map<String, Object> resultMap = new HashMap<>();
        resultMap.put("success","true");
        int pageIndex = 1;
        HttpServletRequest request =null;
        String result = answerService.getAllAnsToOneQuestion(questionID, pageIndex,request);
        System.out.println("看有没有排xu"+result);
        messagingTemplate.convertAndSend( "/topic/getAllAnstoOneQ/"+questionID, result);
        return auxiliary.mapObjectToJson(resultMap);
    }
}
