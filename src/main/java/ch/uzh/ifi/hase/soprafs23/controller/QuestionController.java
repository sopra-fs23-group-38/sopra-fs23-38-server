package ch.uzh.ifi.hase.soprafs23.controller;

import ch.uzh.ifi.hase.soprafs23.entity.User;
import ch.uzh.ifi.hase.soprafs23.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ch.uzh.ifi.hase.soprafs23.Auxiliary.auxiliary;
import ch.uzh.ifi.hase.soprafs23.service.QuestionService;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/question")
public class QuestionController {

    @Autowired
    private QuestionService questionService;

    @Autowired
    private UserRepository userRepository;

    @PostMapping("/createQuestion")
    public String createQuestion(HttpServletRequest request) {
        return questionService.createQuestion(request);
    }

    @GetMapping("/getAllQuestions")
    public String getAllQuestions(@RequestParam("pageIndex") String pageIndex,
                                     HttpServletRequest request) {

        Integer pageIndexInt = Integer.parseInt(pageIndex);
        return questionService.getAllQuestions(pageIndexInt, request);

    }

    @GetMapping("/getQuestionById")
    public String getQuestionById(@RequestParam("ID") String ID,
                              HttpServletRequest request) {
        int id = Integer.parseInt(ID);
        return questionService.getQuestionById(id, request);
    }

    @GetMapping("/getHowManyQuestions")
    public String getHowManyQuestions() {
        return questionService.getHowManyQuestions();
    }

    @GetMapping("/getQuestionsByWho")
    public String getQuestionsByWho(@RequestParam("who_asks") String who_asks) {
        Integer who_asks_id = Integer.parseInt(who_asks);
        return questionService.getQuestionsByWho(who_asks_id);
    }

    @PostMapping("/updateQuestion")
    public String updateQuestion(@RequestParam("ID") String ID,
                                 @RequestParam("newTitle") String newTitle,
                                 @RequestParam("newDescription") String newDescription,
                                 HttpServletRequest request) {
        Integer Id = Integer.parseInt(ID);
    return questionService.updateQuestion(Id, newTitle, newDescription, request);
    }

    @DeleteMapping ("/deleteQuestion")
    public String deleteQuestion(@RequestParam("ID") String ID,
                                 HttpServletRequest request) {
        Integer Id = Integer.parseInt(ID);
        return questionService.deleteQuestion(Id, request);
    }




}
