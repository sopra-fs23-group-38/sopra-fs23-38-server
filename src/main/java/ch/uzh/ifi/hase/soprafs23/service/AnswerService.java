package ch.uzh.ifi.hase.soprafs23.service;

import ch.uzh.ifi.hase.soprafs23.Auxiliary.auxiliary;
import ch.uzh.ifi.hase.soprafs23.entity.*;
import ch.uzh.ifi.hase.soprafs23.repository.*;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import java.sql.Date;
import java.time.Instant;
import java.util.*;

@Service
public class AnswerService {

    @Autowired
    private QuestionRepository questionRepository;

    @Autowired
    private AnswerRepository answerRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private NotificationRepository notificationRepository;

    @Autowired
    private VoteRepository voteRepository;


    @Transactional
    public String createAnswer(Integer questionId, String content, HttpServletRequest request) {
        Map<String, Object> infobody = new HashMap<>();

        Integer userId = auxiliary.extractUserID(request);
        Answer answer = new Answer();

        if (content != null && content.length() > 0) {
            answer.setQuestion_id(questionId);
            answer.setWho_answers(userId);
            answer.setContent(content);
            answer.setChange_time(Date.from(Instant.now()));
            answer.setVote_count(0);
            answer.setComment_count(0);
            answerRepository.save(answer);
            infobody.put("success", "true");
        }

        Optional<Question> ans_to_question = questionRepository.findById(questionId);
        assert userId != null;
        Optional<User> ans_user = userRepository.findById(userId);

        if (ans_user.isPresent()) {
            User ans_to_user = ans_user.orElse(null);

            if (ans_to_question.isPresent()) {
                Question question = ans_to_question.orElse(null);
                question.setAnswerCount(question.getAnswerCount() + 1);
                questionRepository.save(question);

                Notification notification = new Notification();// update the Notification
                notification.setToUserId(question.getWho_asks());
                notification.setUrl("/question/answer/" + answer.getId());
                notification.setContent(ans_to_user.getUsername() + "answered to your question.");
                notification.setCreateTime(Date.from(Instant.now()));
                notificationRepository.save(notification);

                Optional<User> ans_to_question_user = userRepository.findById(question.getWho_asks());
                if (ans_to_question_user.isPresent()) {
                    User ans_to_question_User = ans_to_question_user.get();
                    ans_to_question_User.setHasNew(ans_to_question_User.getHasNew()+1);
                    userRepository.save(ans_to_question_User);
                }
            }
        }
        return auxiliary.mapObjectToJson(infobody);

    }

    @Transactional
    public String getAllAnsToOneQuestion(Integer questionId, int pageIndex, HttpServletRequest request) {
        List<Map<String, Object>> infobody = new ArrayList<>();

        int limit = 7;
        int offset = (pageIndex - 1) * limit;
//        int allindex = answerRepository.countAnswersByQuestionId(questionId);

        List<Answer> existingAnsToOneQuestion = answerRepository.findByAllQuestionIdOrderByVoteCountDesc(questionId);

        if (existingAnsToOneQuestion != null) {
            for (Answer answer : existingAnsToOneQuestion) {
                Map<String, Object> answerInformation = new HashMap<>();
                answerInformation.put("answer", answer);

                Integer answerId = answer.getId();
                Integer who_answersId = answerRepository.findAnswererIdById(answerId);

                User who_answers = answerRepository.findUserById(who_answersId);

                answerInformation.put("who_answersId", who_answersId);
                answerInformation.put("who_answers_name", who_answers.getUsername());
                answerInformation.put("who_answers_avatar", who_answers.getAvatarUrl());

                infobody.add(answerInformation);

            }
        }
        return auxiliary.listMapToJson(infobody);
    }

    @Transactional
    public String getAnswerById(int id, HttpServletRequest request) {
        Map<String, Object> infobody = new HashMap<>();
        Optional<Answer> ans = answerRepository.findById(id);

        if (ans.isPresent()) {
            Answer answer = ans.orElse(null);
            Integer who_answers = answer.getWho_answers();
            Optional<User> u = userRepository.findById(who_answers);
            if (u.isPresent()) {
                User user = u.orElse(null);
                infobody.put("userId", who_answers);
                infobody.put("username", user.getUsername());
                infobody.put("useravatar",user.getAvatarUrl());
            }
            infobody.put("answer", answer);
            infobody.put("commentCount", answer.getComment_count());
            infobody.put("votecount", answer.getVote_count());
            infobody.put("questionId", answer.getQuestion_id());
            Optional<Question> q = questionRepository.findById(answer.getQuestion_id());
            if (q.isPresent()) {
                infobody.put("title", q.get().getTitle());
                infobody.put("questionAnscount", q.get().getAnswerCount());

            }
        }

        return auxiliary.mapObjectToJson(infobody);
    }

    @Transactional
    public String getAnswersByWho(Integer who_answers_id) {
        List<Map<String, Object>> infobody = new ArrayList<>();
        List<Answer> existingAnswersofWho = answerRepository.findAllByWhoAnswersOrderByChangeTimeDesc(who_answers_id);

        for (Answer answer : existingAnswersofWho) {
            Map<String, Object> answerInformation = new HashMap<>();
            answerInformation.put("answerId", answer.getId());
            answerInformation.put("content", answer.getContent());
            answerInformation.put("questionId", answer.getQuestion_id());
            answerInformation.put("questionTitle", answerRepository.findAnswerToQuestionTitle(answer.getId()));
            answerInformation.put("change_time", answer.getChange_time());
            answerInformation.put("vote_count",answer.getVote_count());
            if (answer.getComment_count() > 0) {
                answerInformation.put("hasComment", "true");
            }
            else {
                answerInformation.put("hasComment", "false");
            }
            infobody.add(answerInformation);
        }
        return auxiliary.listMapToJson(infobody);

    }


    public String UporDownVote(int id, int vote) {
        Map<String, Object> infobody = new HashMap<>();
        Optional<Answer> ans = answerRepository.findById(id);
        if (ans.isPresent()) {
            Answer answer = ans.orElse(null);
            Vote newvote = new Vote();
            newvote.setAnswer_id(id);
            if (vote == 1) {
                newvote.setIs_upvote(true);
                answer.setVote_count(answer.getVote_count() + 1);
            }
            else {
                newvote.setIs_upvote(false);
                answer.setVote_count(answer.getVote_count() - 1);
            }
            voteRepository.save(newvote);
        }
        infobody.put("success", "true");

        return auxiliary.mapObjectToJson(infobody);
    }

    @Transactional
    public String updateAnswer(Integer Id, String newContent, HttpServletRequest request) {
        Map<String, Object> infobody = new HashMap<>();

        if (newContent != null && newContent.length() < 50) {
            Optional<Answer> answer_tobe_updated = answerRepository.findById(Id);
            if (answer_tobe_updated.isPresent()) {
                Answer answer = answer_tobe_updated.orElse(null);
                answer.setContent(newContent);
                answer.setChange_time(Date.from(Instant.now()));
                answerRepository.save(answer);
                infobody.put("success", "true");
            }
            else {
                infobody.put("success", "false");
            }
        }
        return auxiliary.mapObjectToJson(infobody);
    }

    @Transactional
    public String deleteAnswer(Integer Id, HttpServletRequest request) {
        Map<String, Object> infobody = new HashMap<>();

        Optional<Answer> ans = answerRepository.findById(Id);
        if (ans.isPresent()) {
            Answer answer = ans.orElse(null);
            Integer q_id = answer.getQuestion_id();
            Optional<Question> q = questionRepository.findById(q_id);
            if (q.isPresent()) {
                Question question = q.orElse(null);
                question.setAnswerCount(question.getAnswerCount() - 1);
                questionRepository.save(question);
            }
            voteRepository.deleteVotesByAnswerId(Id);
            // leave this as to delete comment further
            commentRepository.deleteCommentByAnswer_ID(Id);
            answerRepository.deleteAnswerById(Id);


        }
        infobody.put("success", "true");
        return auxiliary.mapObjectToJson(infobody);

    }

    public String getHowManyPages(Integer question_id) {
        double howmanypages = Math.ceil(((double) answerRepository.countAnswersByQuestionId(question_id)) / 7);
        Map<String, Object> infobody= new HashMap<>();
        infobody.put("howmanypages",(int)howmanypages);
        return auxiliary.mapObjectToJson(infobody);
    }
}