package ch.uzh.ifi.hase.soprafs23.Auxiliary;

import ch.uzh.ifi.hase.soprafs23.entity.Message;
import ch.uzh.ifi.hase.soprafs23.entity.Notification;
import ch.uzh.ifi.hase.soprafs23.entity.Message;
import ch.uzh.ifi.hase.soprafs23.entity.Notification;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

public class AuxiliaryWrapper {

    public String listMapToJson(List<Map<String, Object>> list) {
        return auxiliary.listMapToJson(list);
    }

    public String CommentListToJson(List<Map<String, Object>> commentlist) {
        return auxiliary.CommentListToJson(commentlist);
    }

    public String mapToJson(Map<String, String> map) {
        return auxiliary.mapToJson(map);
    }

    public String mapObjectToJson(Map<String, Object> map) {
        return auxiliary.mapObjectToJson(map);
    }

    public String notificationsToJson(List<Notification> notifications) {
        return auxiliary.notificationsToJson(notifications);
    }

    public String messagesToJson(List<Message> messages) {
        return auxiliary.messagesToJson(messages);
    }

    public Cookie Identification(Integer userId) {
        return auxiliary.Identification(userId);
    }

    public Integer extractUserID(HttpServletRequest request) {
        return auxiliary.extractUserID(request);
    }
}

