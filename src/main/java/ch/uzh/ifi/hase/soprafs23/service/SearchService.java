package ch.uzh.ifi.hase.soprafs23.service;


import ch.uzh.ifi.hase.soprafs23.repository.AnswerRepository;
import ch.uzh.ifi.hase.soprafs23.repository.QuestionRepository;
import ch.uzh.ifi.hase.soprafs23.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.*;


@Service
public class SearchService {
    @Autowired
    private QuestionRepository questionRepository;
    @Autowired
    private AnswerRepository answerRepository;

    @Autowired
    private UserRepository userRepository;


    @Transactional
    public List<Map<String, Object>> searchKeyword(String keyword,String searchType) {
        List<Map<String, Object>> total = new ArrayList<>();
        switch (searchType){
            case "User":
                List<Map<String, Object>> userSearchList = searchUser(keyword);
                int maxElementsU = Math.min(userSearchList.size(), 5);
                for(int i = 0; i < maxElementsU; i++) {
                    Map<String, Object> userMap = userSearchList.get(i);
                    Map<String, Object> trans_info = new HashMap<>();
                    trans_info.put("name", userMap.get("username"));
                    trans_info.put("type", "User");
                    int idValue = (int) userMap.get("id");
                    int result = idValue * 3;
                    trans_info.put("html_url", "/center/U" + result);
                    total.add(trans_info);
                }
                break;
            case "Question":
                List<Map<String, Object>> questionSearchList = searchQuestion(keyword);
                int maxElementsQ = Math.min(questionSearchList.size(), 5);
                for(int i = 0; i < maxElementsQ; i++) {
                    Map<String, Object> questionMap = questionSearchList.get(i);
                    Map<String, Object> trans_info = new HashMap<>();
                    trans_info.put("name", questionMap.get("title"));
                    trans_info.put("type", "Question");
                    trans_info.put("html_url", "/question/" + questionMap.get("id"));
                    total.add(trans_info);
                }
                break;
            case "Answer":
                List<Map<String, Object>> answerSearchList = searchAnswer(keyword);
                int maxElementsA = Math.min(answerSearchList.size(), 5);
                for(int i = 0; i < maxElementsA; i++) {
                    Map<String, Object> answerMap = answerSearchList.get(i);
                    Map<String, Object> trans_info = new HashMap<>();

                    trans_info.put("id",answerMap.get("id"));
                    trans_info.put("name", answerMap.get("content"));
                    trans_info.put("type", "Answer");
                    trans_info.put("html_url", "/question/" + answerMap.get("question_id"));
                    total.add(trans_info);
                }
                break;
            case "Comment":
                System.out.println(2);


            case "All":
            default:
                List<Map<String, Object>> userList = searchUser(keyword);
                List<Map<String, Object>> questionList = searchQuestion(keyword);
                List<Map<String, Object>> answerList = searchAnswer(keyword);
                int maxU = Math.min(userList.size(), 5);
                int maxQ = Math.min(questionList.size(), 5);
                int maxA = Math.min(answerList.size(), 5);
                for(int i = 0; i < maxU; i++) {
                    Map<String, Object> userMap = userList.get(i);
                    Map<String, Object> trans_info = new HashMap<>();
                    trans_info.put("name", userMap.get("username"));
                    trans_info.put("type", "User");
                    int idValue = (int) userMap.get("id");
                    int result = idValue * 3;
                    trans_info.put("html_url", "/center/U" + result);
                    total.add(trans_info);
                }
                for(int i = 0; i < maxQ; i++) {
                    Map<String, Object> questionMap = questionList.get(i);
                    Map<String, Object> trans_info = new HashMap<>();
                    trans_info.put("name", questionMap.get("title"));
                    trans_info.put("type", "Question");
                    trans_info.put("html_url", "/question/" + questionMap.get("id"));
                    total.add(trans_info);
                }
                for(int i = 0; i < maxA; i++) {
                    Map<String, Object> answerMap = answerList.get(i);
                    Map<String, Object> trans_info = new HashMap<>();
                    trans_info.put("id", answerMap.get("id"));
                    trans_info.put("name", answerMap.get("content"));
                    trans_info.put("type", "Answer");
                    trans_info.put("html_url", "/question/" + answerMap.get("question_id"));
                    total.add(trans_info);
                }
                break;
        }
        return total;}

    private List<Map<String, Object>> searchUser(String keyword){
        List<Map<String, Object>> userSearchList = new ArrayList<>();
        List<Object[]> userResult = userRepository.UserFindByKeyword(keyword);
        for (Object[] result : userResult) {
            Map<String, Object> mapU = new HashMap<>();
            int id = (int) result[0];
            String username = (String) result[1];
            BigDecimal score = (BigDecimal) result[2];
            mapU.put("id", id);
            mapU.put("username", username);
            mapU.put("score", score.doubleValue());
            userSearchList.add(mapU);
        }
        return userSearchList;
    }

    private List<Map<String, Object>> searchQuestion(String keyword){
        List<Map<String, Object>> questionSearchList = new ArrayList<>();
        List<Object[]> questionResult = questionRepository.QuestionFindByKeyword(keyword);
        for (Object[] result : questionResult) {
            Map<String, Object> mapQ = new HashMap<>();
            int id = (int) result[0];
            String title = (String) result[1];
            String description = (String) result[2];
            BigDecimal score = (BigDecimal) result[3];
            mapQ.put("id", id);
            mapQ.put("title", title);
            mapQ.put("description",description);
            mapQ.put("score", score.doubleValue());
            questionSearchList.add(mapQ);
        }
        return questionSearchList;
    }

    private List<Map<String, Object>> searchAnswer(String keyword) {
        List<Map<String, Object>> answerSearchList = new ArrayList<>();
        List<Object[]> answerResult = answerRepository.AnswerFindByKeyword(keyword);
        for (Object[] result : answerResult) {
            Map<String, Object> mapA = new HashMap<>();
            int id = (int) result[0];
            String content = (String) result[1];
            int question_id = (int) result[2];
            BigDecimal score = (BigDecimal) result[3];
            mapA.put("id", id);
            mapA.put("content", content);
            mapA.put("question_id", question_id);
            mapA.put("score", score.doubleValue());
            answerSearchList.add(mapA);
        }
        return answerSearchList;
    }}
