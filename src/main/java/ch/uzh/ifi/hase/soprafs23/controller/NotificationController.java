package ch.uzh.ifi.hase.soprafs23.controller;

import ch.uzh.ifi.hase.soprafs23.Auxiliary.auxiliary;
import ch.uzh.ifi.hase.soprafs23.entity.Notification;
import ch.uzh.ifi.hase.soprafs23.repository.NotificationRepository;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/notification")
public class NotificationController {


    private final NotificationRepository notificationRepository;

    public NotificationController(NotificationRepository notificationRepository) {
        this.notificationRepository = notificationRepository;
    }

    @PostMapping("/list")
    public String listNotifications(@RequestParam("toUserId") Integer toUserId) {
        List<Notification> messages = notificationRepository.listNotifications(toUserId);

        try {
            return auxiliary.notificationsToJson(messages);
        } catch (Exception e) {
            Map<String, String> resultMap = new HashMap<>();
            resultMap.put("success","false");
            return auxiliary.mapToJson(resultMap);
        }
    }
}