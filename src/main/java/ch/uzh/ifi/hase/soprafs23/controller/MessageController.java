package ch.uzh.ifi.hase.soprafs23.controller;

import ch.uzh.ifi.hase.soprafs23.Auxiliary.auxiliary;
import ch.uzh.ifi.hase.soprafs23.entity.Message;
import ch.uzh.ifi.hase.soprafs23.entity.Notification;
import ch.uzh.ifi.hase.soprafs23.repository.MessageRepository;
import ch.uzh.ifi.hase.soprafs23.repository.NotificationRepository;
import ch.uzh.ifi.hase.soprafs23.repository.UserRepository;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ch.uzh.ifi.hase.soprafs23.entity.User;

import java.sql.Timestamp;
import java.util.*;
import ch.uzh.ifi.hase.soprafs23.Auxiliary.auxiliary;

@RestController
@RequestMapping("/message")
public class MessageController {
    private final MessageRepository messageRepository;
    private final UserRepository userRepository;
    private final NotificationRepository notificationRepository;

    public MessageController(MessageRepository messageRepository, UserRepository userRepository, NotificationRepository notificationRepository) {
        this.messageRepository = messageRepository;
        this.userRepository = userRepository;
        this.notificationRepository = notificationRepository;
    }

    @PostMapping("/list")
    public String listMessages(@RequestParam("fromUserId") Integer fromUserId,
                               @RequestParam("toUserId") Integer toUserId) {
        List<String> userIds = new ArrayList<>();
        userIds.add(fromUserId.toString());
        userIds.add(toUserId.toString());
        Collections.sort(userIds);
        String userIdsText = String.join(",", userIds);

        List<Message> messages = messageRepository.listMessages(userIdsText);

        try {
            return auxiliary.messagesToJson(messages);
        } catch (Exception e) {
            Map<String, Object> infobody = new HashMap<>();
            infobody.put("success","false");
            return auxiliary.mapObjectToJson(infobody);
        }
    }

    @PostMapping("/insert")
    public String insertComment(@RequestParam("content") String content,
                                @RequestParam("fromUserId") Integer fromUserId,
                                @RequestParam("toUserId") Integer toUserId) {
        Message message = new Message();
        List<String> userIds = new ArrayList<>();
        userIds.add(fromUserId.toString());
        userIds.add(toUserId.toString());
        Collections.sort(userIds);

        message.setUserIds(String.join(",", userIds));
        message.setContent(content);
        message.setFromUserId(fromUserId);
        message.setToUserId(toUserId);
        message.setCreateTime(new Timestamp(System.currentTimeMillis()));
        message.setUpdateTime(new Timestamp(System.currentTimeMillis()));
        messageRepository.save(message);

        Map<String, Object> infobody= new HashMap<>();
        infobody.put("success","false");
        if (message.getId() != null) {
            Optional<User> byId = userRepository.findById(fromUserId);
            if (byId.isPresent()) {
                User fromUser = byId.get();
                Notification notification = new Notification();
                notification.setToUserId(toUserId);
                notification.setUrl("/chat?fromUserId=" + toUserId + "&toUserId=" + fromUserId);
                notification.setContent(fromUser.getUsername() + " SEND YOU A MESSAGE");
                notification.setCreateTime(new Timestamp(System.currentTimeMillis()));
                notificationRepository.save(notification);

                Optional<User> byId2 = userRepository.findById(toUserId);
                boolean present = byId2.isPresent();
                if (present) {
                    User toUser = byId2.get();
                    toUser.setHasNew(true);
                    userRepository.save(toUser);
                }
            }
            infobody.put("success","true");
        }

        return auxiliary.mapObjectToJson(infobody);
    }
}
