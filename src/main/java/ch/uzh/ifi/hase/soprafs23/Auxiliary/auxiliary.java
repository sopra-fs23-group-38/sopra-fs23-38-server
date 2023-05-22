package ch.uzh.ifi.hase.soprafs23.Auxiliary;

import ch.uzh.ifi.hase.soprafs23.entity.Message;
import ch.uzh.ifi.hase.soprafs23.entity.Notification;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;



public class auxiliary {
    private static final Gson gson = new Gson();

    public static String listMapToJson(List<Map<String, Object>> list) {
        return gson.toJson(list);
    }
    public static String CommentListToJson(List<Map<String, Object>> commentlist){
        Gson gson = new Gson();
        return gson.toJson(commentlist);
    }
    public static String mapToJson(Map<String, String> map) {
        Gson gson = new GsonBuilder().create();
        return gson.toJson(map);
    }

    public static String mapObjectToJson(Map<String, Object> map) {
        Gson gson = new GsonBuilder().create();
        return gson.toJson(map);
    }

    public static String notificationsToJson(List<Notification> notifications) {
        Gson gson = new Gson();
        return gson.toJson(notifications);
    }

    public static String messagesToJson(List<Message> messages) {
        Gson gson = new Gson();
        return gson.toJson(messages);
    }

    public static Cookie Identification(Integer userId) {

        String token = String.format("U%s", userId * 3); //TokenUtils.sign(userId);

        Cookie tokenCookie = new Cookie("token", token);

        tokenCookie.setMaxAge(365 * 24 * 60 * 60);
        tokenCookie.setPath("/");
        return tokenCookie;
    }

    public static Integer extractUserID(HttpServletRequest request) {
        String token = request.getHeader("token");
        if (token != null && !token.isEmpty()) {
            System.out.println("Using token header: " + token);
            Integer userId = Integer.parseInt(token.substring(1)) / 3;
            return userId;
        }

        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals("token")) {
                    token = cookie.getValue();
                    System.out.println("Using token cookie: " + token);
                    break;
                }
            }
        }

        if (token != null && !token.isEmpty()) {
            Integer userId = Integer.parseInt(token.substring(1)) / 3;
            return userId;
        }
        return null;
    }

}
