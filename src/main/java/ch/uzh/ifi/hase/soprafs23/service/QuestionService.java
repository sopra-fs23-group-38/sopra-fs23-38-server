package ch.uzh.ifi.hase.soprafs23.service;

import ch.uzh.ifi.hase.soprafs23.Auxiliary.auxiliary;
import ch.uzh.ifi.hase.soprafs23.entity.Question;
import ch.uzh.ifi.hase.soprafs23.entity.User;
import ch.uzh.ifi.hase.soprafs23.repository.AnswerRepository;
import ch.uzh.ifi.hase.soprafs23.repository.CommentRepository;
import ch.uzh.ifi.hase.soprafs23.repository.QuestionRepository;
import ch.uzh.ifi.hase.soprafs23.repository.UserRepository;
import org.json.JSONObject;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Date;
import java.time.Instant;
import java.util.*;

import javax.servlet.http.HttpServletRequest;

@Service
public class QuestionService {
    @Autowired
    private QuestionRepository questionRepository;

    @Autowired
    private AnswerRepository answerRepository;

    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private UserRepository userRepository;

    @Transactional
    public String createQuestion(HttpServletRequest request) {
        Map<String,Object> infobody = new HashMap<>();
        String title = request.getParameter("title");
        String description = request.getParameter("description");
        String tag = request.getParameter("tag"); // Add this line
        Integer who_asks = auxiliary.extractUserID(request);
        if (title != null && who_asks != null) {
            Question newquestion = new Question();
            newquestion.setTitle(title);
            newquestion.setDescription(description);
            newquestion.setTag(tag);
            newquestion.setWho_asks(who_asks);
            newquestion.setChange_time(Date.from(Instant.now()));
            newquestion.setAnswerCount(0);

            questionRepository.save(newquestion);

            infobody.put("success", "true");
            infobody.put("userID", "/question/"+newquestion.getId());
        }
        else {
            infobody.put("success", "false");
        }
        return auxiliary.mapObjectToJson(infobody);
    }

    @Transactional
    public String getAllQuestions(Integer pageIndex, String tag, String orderBy, HttpServletRequest request) {

        List<Map<String, Object>> infobody = new ArrayList<>();

        int limit = 7;
        int offset = (pageIndex-1) * limit;
        List<Question> existingQuestions;

        if (Objects.equals(orderBy, "answer_count")) {
            if (tag == null || tag.isEmpty()) {
                existingQuestions = questionRepository.findTopNByOrderByAnswerCountDesc(offset, limit);
            } else {
                existingQuestions = questionRepository.findTopNByTagOrderByAnswerCountDesc(tag, offset, limit);
            }
        } else {
            if (tag == null || tag.isEmpty()) {
                existingQuestions = questionRepository.findTopNByOrderByTimeDesc(offset, limit);
            } else {
                existingQuestions = questionRepository.findTopNByTagOrderByTimeDesc(tag, offset, limit);
            }
        }

        if (existingQuestions!= null) {
            for (Question question : existingQuestions) {

                Map<String, Object> questionInformation = new HashMap<>();

                questionInformation.put("question", question);

                Integer questionId = question.getId();
                Integer who_asksId = questionRepository.findQuestionerIdById(questionId);

                User who_asks = questionRepository.findUserById(who_asksId);

                questionInformation.put("who_asksId", who_asksId);
                questionInformation.put("who_asks_name", who_asks.getUsername());
                questionInformation.put("who_asks_avatar", who_asks.getAvatarUrl());

                infobody.add(questionInformation);
            }
        }
        return auxiliary.listMapToJson(infobody);
    }


    public String getHowManyQuestions() {
//        System.out.println(questionRepository.count());
        int howmanypages = (int) Math.ceil(questionRepository.count() / 7.0);
        howmanypages = Math.max(howmanypages, 1); // Ensure at least 1 page
        Map<String, Object> infobody = new HashMap<>();
        infobody.put("howmanypages", howmanypages);
        return auxiliary.mapObjectToJson(infobody);
    }

    @Transactional
    public String getQuestionsByWho(Integer who_asks_id) {
        List<Map<String, Object>> infobody = new ArrayList<>();
        List<Question> existingQuestionsofWho = questionRepository.findAllByWhoAsksOrderByChangeTimeDesc(who_asks_id);

        for (Question question : existingQuestionsofWho) {
            Map<String, Object> questionInformation = new HashMap<>();
            questionInformation.put("questionId", question.getId());
            questionInformation.put("lastChangeTime", question.getChange_time());
            questionInformation.put("title", question.getTitle());
            questionInformation.put("description", question.getDescription());
            questionInformation.put("answer_count",question.getAnswerCount());
            if (question.getAnswerCount()>0) {
                questionInformation.put("hasAnswer", "true");
            } else {
                questionInformation.put("hasAnswer", "false");
            }
            infobody.add(questionInformation);
        }
        return auxiliary.listMapToJson(infobody);
    }


    @Transactional
    public String getQuestionById(int id, HttpServletRequest request) {
        Map<String, Object> infobody = new HashMap<>();
        Optional<Question> q = questionRepository.findById(id);

        if (q.isPresent()) {
            Question question = q.orElse(null);

            Integer who_asks = question.getWho_asks();
            Optional<User> u = userRepository.findById(who_asks);
            if (u.isPresent()) {
                User user = u.orElse(null);
                infobody.put("userId", who_asks);
                infobody.put("username", user.getUsername());
                infobody.put("user_avatar",user.getAvatarUrl());
            }
            infobody.put("question", question);
            infobody.put("answerCount", question.getAnswerCount());
        }
        return auxiliary.mapObjectToJson(infobody);

    }

    @Transactional
    public String updateQuestion(Integer Id, String newTitle, String newDescription, HttpServletRequest request) {
        Map<String, Object> infobody = new HashMap<>();

        if (newTitle != null && newTitle.length() < 50) {
            Optional<Question> question_tobe_updated = questionRepository.findById(Id);
            if (question_tobe_updated.isPresent()) {
                Question question = question_tobe_updated.orElse(null);
                question.setTitle(newTitle);
                question.setDescription(newDescription);
                question.setChange_time(Date.from(Instant.now()));
                questionRepository.save(question);
                infobody.put("success", "true");
            } else {
                infobody.put("success", "false");
            }
        }
        return auxiliary.mapObjectToJson(infobody);
    }

    @Transactional
    public String deleteQuestion(Integer Id, HttpServletRequest request) {
        Map<String, Object> infobody= new HashMap<>();

        List<Integer> answer_id_list = answerRepository.getAllIdByQuestion_id(Id);
        for (Integer id :answer_id_list){
            commentRepository.deleteCommentByAnswer_ID(id);
        }

        answerRepository.deleteAnswerByQuestion_id(Id);
        questionRepository.deleteById(Id);

        infobody.put("success", "true");

        return auxiliary.mapObjectToJson(infobody);
    }

}
