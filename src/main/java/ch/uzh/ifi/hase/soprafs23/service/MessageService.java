package ch.uzh.ifi.hase.soprafs23.service;

import ch.uzh.ifi.hase.soprafs23.Auxiliary.auxiliary;
import ch.uzh.ifi.hase.soprafs23.entity.Message;
import ch.uzh.ifi.hase.soprafs23.entity.User;
import ch.uzh.ifi.hase.soprafs23.repository.MessageRepository;
import ch.uzh.ifi.hase.soprafs23.repository.UserRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class MessageService {

    @Autowired
    private MessageRepository messageRepository;

    @Autowired
    private UserRepository userRepository;


    public String fetchMessageList(Integer fromUserId, Integer toUserId) {
        List<String> userIds1 = new ArrayList<>();
        userIds1.add(fromUserId.toString());
        userIds1.add(toUserId.toString());
        Collections.sort(userIds1);
        String userIdsText = String.join(",", userIds1);

        List<Message> messages = messageRepository.listMessages(userIdsText);
        User fromUser = userRepository.findById(fromUserId).get();
        User toUser = userRepository.findById(toUserId).get();
        String fromUser_avatar = fromUser.getAvatarUrl();
        String toUser_avatar = toUser.getAvatarUrl();

        List<Map<String, Object>> messageMaps = messages.stream().map(message -> {
            Map<String, Object> messageMap = new ObjectMapper().convertValue(message, Map.class);
            if (message.getFromUserId().equals(fromUserId)) {
                messageMap.put("fromUserAvatar", fromUser_avatar);
                messageMap.put("toUserAvatar", toUser_avatar);
            } else {
                messageMap.put("fromUserAvatar", toUser_avatar);
                messageMap.put("toUserAvatar", fromUser_avatar);
            }
            return messageMap;
        }).collect(Collectors.toList());

        try {
            return new ObjectMapper().writeValueAsString(messageMaps);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            return null;
        }
    }
}
