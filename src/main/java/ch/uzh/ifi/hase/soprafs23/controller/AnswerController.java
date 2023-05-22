package ch.uzh.ifi.hase.soprafs23.controller;

import ch.uzh.ifi.hase.soprafs23.service.AnswerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import ch.uzh.ifi.hase.soprafs23.service.QuestionService;

import javax.servlet.http.HttpServletRequest;
@RestController
@RequestMapping("/answer")
public class AnswerController {
    @Autowired
    private AnswerService answerService;

    @PostMapping("/createAnswer")
    public String createAnswer(@RequestParam("questionID") String questionID,
                                  @RequestParam("content") String content,
                                  HttpServletRequest request) {
        Integer questionId = Integer.parseInt(questionID);
        return answerService.createAnswer(questionId, content, request);
    }

    @GetMapping("/getAllAnstoOneQ")
    public String getAllAnsToOneQuestion(@RequestParam("questionID") String questionID,
                                           @RequestParam("pageIndex") String pageIndex,
                                           HttpServletRequest request) {

        int pageIndexInt = Integer.parseInt(pageIndex);
        int questionId = Integer.parseInt(questionID);
        return answerService.getAllAnsToOneQuestion(questionId, pageIndexInt, request);

    }

    @GetMapping("/getAnswerById")
    public String getAnswerById(@RequestParam("ID") String ID,
                            HttpServletRequest request) {
        int id = Integer.parseInt(ID);
        return answerService.getAnswerById(id, request);
    }

    @GetMapping("/getAnswersByWho")
    public String getAnswersByWho(@RequestParam("answererID") String answererID) {
        int answererId = Integer.parseInt(answererID);
        return answerService.getAnswersByWho(answererId);
    }

    @GetMapping("/getHowManyPages")
    public String getTotalPageCount(@RequestParam("which_question") Integer which_question) {
        return answerService.getHowManyPages(which_question);
    }

    @PostMapping("/vote")
    public String UporDownVote(@RequestParam("ID") String ID,
                                @RequestParam("UporDownVote") String UporDownVote) {

        int id = Integer.parseInt(ID);
        int vote = Integer.parseInt(UporDownVote);
        return answerService.UporDownVote(id,vote);
    }

    @PostMapping("/updateAnswer")
    public String updateAnswer(@RequestParam("ID") String ID,
                               @RequestParam("newContent") String newContent,
                               HttpServletRequest request) {
        int Id = Integer.parseInt(ID);
        return answerService.updateAnswer(Id, newContent, request);
    }

    @DeleteMapping ("/deleteAnswer")
    public String deleteAnswer(@RequestParam("ID") String ID,
                               HttpServletRequest request) {
        Integer Id = Integer.parseInt(ID);
        return answerService.deleteAnswer(Id, request);
    }

}
