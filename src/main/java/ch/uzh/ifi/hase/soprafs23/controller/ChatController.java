package ch.uzh.ifi.hase.soprafs23.controller;

import ch.uzh.ifi.hase.soprafs23.Auxiliary.auxiliary;
import ch.uzh.ifi.hase.soprafs23.entity.Message;
import ch.uzh.ifi.hase.soprafs23.entity.Notification;
import ch.uzh.ifi.hase.soprafs23.entity.User;
import ch.uzh.ifi.hase.soprafs23.repository.MessageRepository;
import ch.uzh.ifi.hase.soprafs23.repository.NotificationRepository;
import ch.uzh.ifi.hase.soprafs23.repository.UserRepository;
import ch.uzh.ifi.hase.soprafs23.service.AnswerService;
import ch.uzh.ifi.hase.soprafs23.service.CommentService;
import ch.uzh.ifi.hase.soprafs23.service.MessageService;
import ch.uzh.ifi.hase.soprafs23.service.QuestionService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.*;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.json.JSONObject;
import javax.servlet.http.HttpServletRequest;
import java.sql.Timestamp;
import java.util.*;

@Controller
public class ChatController {

    private final MessageRepository messageRepository;
    private final NotificationRepository notificationRepository;
    public ChatController(SimpMessagingTemplate messagingTemplate, MessageRepository messageRepository, NotificationRepository notificationRepository) {
        this.messagingTemplate = messagingTemplate;
        this.messageRepository = messageRepository;
        this.notificationRepository = notificationRepository;
    }
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private SimpMessagingTemplate messagingTemplate;
    @Autowired
    private QuestionService questionService;
    @Autowired
    private CommentService commentService;
    @Autowired
    private AnswerService answerService;

    @Autowired
    private MessageService messageService;

    @Scheduled(fixedDelay = 1000)
    public void getHasNew() {
        List<User> users = userRepository.findAll();
        for (User user : users) {
            Map<String, Object> eacNotification = new HashMap<>();
            eacNotification.put("has_new", user.getHasNew());
            if (user.getHasNew()>0){
            String token = String.format("U%s", user.getId() * 3);
            messagingTemplate.convertAndSend("/topic/has_new/" + token, auxiliary.mapObjectToJson(eacNotification));
            //user.setHasNew(0);
            userRepository.save(user);
            List<Notification> messages = notificationRepository.listNotifications(user.getId());

            try {
                String response = auxiliary.notificationsToJson(messages);
                messagingTemplate.convertAndSend( "/topic/notifications/"+user.getId(), response);
            } catch (Exception e) {
                Map<String, String> resultMap = new HashMap<>();
                resultMap.put("success", "false");
                String response = auxiliary.mapToJson(resultMap);
                messagingTemplate.convertAndSend( "/topic/notifications/"+user.getId(), response);
            }
    }}}

    @MessageMapping("/insert/{fromUserId}/{toUserId}")
    public void insertComment(Message message) throws JsonProcessingException, JsonProcessingException {
        List<String> userIds = new ArrayList<>();

        userIds.add(message.getFromUserId().toString());
        userIds.add(message.getToUserId().toString());
        Collections.sort(userIds);
        message.setUserIds(String.join(",", userIds));
        message.setCreateTime(new Timestamp(System.currentTimeMillis()));
        message.setUpdateTime(new Timestamp(System.currentTimeMillis()));

        // Save the message
        Message savedMessage = messageRepository.save(message);

        Map<String, Object> infobody= new HashMap<>();
        infobody.put("success","false");
        if (savedMessage.getId() != null) {
            Optional<User> byId = userRepository.findById(savedMessage.getFromUserId());
            if (byId.isPresent()) {
                User fromUser = byId.get();
                Notification notification = new Notification();
                notification.setToUserId(savedMessage.getToUserId());
                notification.setUrl("/chat?fromUserId=" + savedMessage.getToUserId() + "&toUserId=" + savedMessage.getFromUserId());
                notification.setContent(fromUser.getUsername() + " SEND YOU A MESSAGE");
                notification.setCreateTime(new Timestamp(System.currentTimeMillis()));
                notificationRepository.save(notification);
                Optional<User> byId2 = userRepository.findById(savedMessage.getToUserId());
                boolean present = byId2.isPresent();
                if (present) {
                    User toUser = byId2.get();
                    toUser.setHasNew(toUser.getHasNew()+1);
                    userRepository.save(toUser);
                }
            }
            infobody.put("success","true");

            List<String> userIds1 = new ArrayList<>();
            userIds1.add(savedMessage.getFromUserId().toString());
            userIds1.add(savedMessage.getToUserId().toString());
            Collections.sort(userIds1);
            String userIdsText = String.join(",", userIds1);

            // Get the avatar URLs of the sender and receiver
            String fromUserAvatar = userRepository.findById(savedMessage.getFromUserId()).get().getAvatarUrl();
            String toUserAvatar = userRepository.findById(savedMessage.getToUserId()).get().getAvatarUrl();

            // Create a map from the message
            Map<String, Object> messageMap = new ObjectMapper().convertValue(savedMessage, Map.class);
            messageMap.put("fromUserAvatar", fromUserAvatar);
            messageMap.put("toUserAvatar", toUserAvatar);

            // Convert the messageMap to JSON
            String messageJson = new ObjectMapper().writeValueAsString(messageMap);

            // Broadcast the message
            messagingTemplate.convertAndSend("/topic/messages/" + savedMessage.getFromUserId()+","+savedMessage.getToUserId(), messageJson);

            listMessages(savedMessage.getToUserId(),savedMessage.getFromUserId());
        }
    }
    
    
    @MessageMapping("/list/{fromUserId}/{toUserId}")
    public void listMessages(@DestinationVariable Integer fromUserId,
                             @DestinationVariable Integer toUserId) {
        String messagesJson = messageService.fetchMessageList(fromUserId, toUserId);
        messagingTemplate.convertAndSend("/topic/messages/" + fromUserId+","+toUserId, messagesJson);
    }


    @MessageMapping("/getHowManyQuestions/")
    public void handleGetHowManyQuestions() {
        String result = questionService.getHowManyQuestions();
        messagingTemplate.convertAndSend("/topic/howManyQuestions/", result);
    }

    @MessageMapping("/getAllQuestions/{page}/")
    public void getAllQuestions(@DestinationVariable Integer page){
        String result1 = questionService.getHowManyQuestions();

        JSONObject jsonObject = new JSONObject(result1);
        int pages = jsonObject.getInt("howmanypages");
        for (int i = pages; i > 0; i--) {
            HttpServletRequest request1 =null;
            String tag = null;
            String orderBy = null;
            String result = questionService.getAllQuestions(i, tag,orderBy,request1);
            System.out.println("jinlai"+result);
            messagingTemplate.convertAndSend("/topic/listQuestions/"+i+"/", result);
        }
    }
    @MessageMapping("/getAllQuestions/{page}/{orderBy1}")
    public void getAllQuestions(@DestinationVariable Integer page,@DestinationVariable String orderBy1){
        String result1 = questionService.getHowManyQuestions();
        if (Objects.equals(orderBy1, "1")){
        JSONObject jsonObject = new JSONObject(result1);
        int pages = jsonObject.getInt("howmanypages");
        for (int i = pages; i > 0; i--) {
            HttpServletRequest request1 =null;
            String tag = null;
            String orderBy = "answer_count";
            String result = questionService.getAllQuestions(i, tag,orderBy,request1);
            System.out.println("orderBy2"+result);
            messagingTemplate.convertAndSend("/topic/listQuestions/"+i+"/"+"1"+"/", result);
        }
    }else{
            JSONObject jsonObject = new JSONObject(result1);
            int pages = jsonObject.getInt("howmanypages");
            for (int i = pages; i > 0; i--) {
                HttpServletRequest request1 =null;
//                String tag = null;
                String orderBy = null;
                String result = questionService.getAllQuestions(i, orderBy1,orderBy,request1);
//                System.out.println(result);
                if (Objects.equals(result, "[]")){
//                    System.out.println(i);
                    messagingTemplate.convertAndSend("/topic/howManyQuestions/"+orderBy1+"/", i-1);
                }
//                System.out.println(Objects.equals(result, "[]"));
                messagingTemplate.convertAndSend("/topic/listQuestions/"+i+"/"+orderBy1+"/", result);
            }
        }

    }
    @MessageMapping("/getAllQuestions/{page}/{tag}/{orderBy1}")
    public void getAllQuestions(@DestinationVariable Integer page, @DestinationVariable String tag, @DestinationVariable String orderBy1){
        String result1 = questionService.getHowManyQuestions();
        JSONObject jsonObject = new JSONObject(result1);
        int pages = jsonObject.getInt("howmanypages");
        for (int i = pages; i > 0; i--) {
            HttpServletRequest request1 = null;
            String orderBy = "answer_count";
            String result = questionService.getAllQuestions(i, tag, orderBy, request1);
            System.out.println("orderBy4"+result);
            if (Objects.equals(result, "[]")){
//                System.out.println(i);
                messagingTemplate.convertAndSend("/topic/howManyQuestions/"+tag+"/", i-1);
            }
            messagingTemplate.convertAndSend("/topic/listQuestions/"+i+"/"+tag+"/"+1+"/", result);
        }
    }

    @MessageMapping("/getQuestionById")
    public void getQuestionById(@Payload String payload, @Header("simpSessionId") String sessionId) throws Exception {
        JSONObject jsonObject = new JSONObject(payload);
        int id = jsonObject.getInt("ID");
        HttpServletRequest request =null;
        String result = questionService.getQuestionById(id,request);
        messagingTemplate.convertAndSend( "/topic/getQuestionById/"+id, result);
    }
    @MessageMapping("/getAnswerById")
    public void getAnswerById(@Payload String payload, @Header("simpSessionId") String sessionId) throws Exception {
        JSONObject jsonObject = new JSONObject(payload);
        int id = jsonObject.getInt("ID");
        HttpServletRequest request =null;
        String result = answerService.getAnswerById(id, request);
        messagingTemplate.convertAndSend( "/topic/getAnswerById/"+id, result);
    }

    @MessageMapping("/getAllComments")
    public void getAllComments(@Payload String payload, @Header("simpSessionId") String sessionId) throws Exception {
        JSONObject jsonObject = new JSONObject(payload);
        int id = jsonObject.getInt("ID");
        HttpServletRequest request =null;
        String result = commentService.getAllComments(id);
        messagingTemplate.convertAndSend( "/topic/getAllComments/"+id, result);
    }



    @MessageMapping("/getAllAnstoOneQ")
    public void getAllAnsToOneQuestion(@Payload String payload, @Header("simpSessionId") String sessionId) throws Exception {
        JSONObject jsonObject = new JSONObject(payload);
        int pageIndex = jsonObject.getInt("pageIndex");
        int questionID = jsonObject.getInt("questionID");
        HttpServletRequest request =null;
        String result = answerService.getAllAnsToOneQuestion(questionID, pageIndex,request);
        System.out.println("看有没有排xu"+result);
        messagingTemplate.convertAndSend( "/topic/getAllAnstoOneQ/"+questionID, result);
    }

    @MessageMapping("/listNotifications")
    public void listNotifications(@Payload Map<String, Integer> payload, @Header("simpSessionId") String sessionId) {
        Integer toUserId = payload.get("toUserId");
        System.out.println(toUserId);
        List<Notification> messages = notificationRepository.listNotifications(toUserId);

        try {
            String response = auxiliary.notificationsToJson(messages);
            messagingTemplate.convertAndSend( "/topic/notifications/"+toUserId, response);
        } catch (Exception e) {
            Map<String, String> resultMap = new HashMap<>();
            resultMap.put("success", "false");
            String response = auxiliary.mapToJson(resultMap);
            messagingTemplate.convertAndSend( "/topic/notifications/"+toUserId, response);
        }
    }




}



