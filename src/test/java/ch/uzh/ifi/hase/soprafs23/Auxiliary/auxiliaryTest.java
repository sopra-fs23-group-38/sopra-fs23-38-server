package ch.uzh.ifi.hase.soprafs23.Auxiliary;

import ch.uzh.ifi.hase.soprafs23.entity.Message;
import ch.uzh.ifi.hase.soprafs23.entity.Notification;
import org.junit.jupiter.api.Test;
import org.springframework.mock.web.MockHttpServletRequest;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.util.*;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class auxiliaryTest {

    @Test
    void testListMapToJson() {
        // Setup
        List<Map<String, Object>> list = Collections.singletonList(Collections.singletonMap("key", "value"));

        // Run the test
        String result = auxiliary.listMapToJson(list);

        // Verify the results
        assertThat(result).isEqualTo("[{\"key\":\"value\"}]");
    }

    @Test
    void testCommentListToJson() {
        // Setup
        List<Map<String, Object>> commentList = Collections.singletonList(Collections.singletonMap("key", "value"));

        // Run the test
        String result = auxiliary.CommentListToJson(commentList);

        // Verify the results
        assertThat(result).isEqualTo("[{\"key\":\"value\"}]");
    }


    @Test
    void testMapToJson() {
        // Setup
        Map<String, String> map = Collections.singletonMap("key", "value");

        // Run the test
        String result = auxiliary.mapToJson(map);

        // Verify the results
        assertThat(result).isEqualTo("{\"key\":\"value\"}");
    }

    @Test
    void testMapObjectToJson() {
        // Setup
        Map<String, Object> map = Collections.singletonMap("key", "value");

        // Run the test
        String result = auxiliary.mapObjectToJson(map);

        // Verify the results
        assertThat(result).isEqualTo("{\"key\":\"value\"}");
    }

    @Test
    void testNotificationsToJson() {
        // Setup
        Notification notification = new Notification();
        notification.setId(0);
        notification.setToUserId(0);
        notification.setUrl("url");
        notification.setContent("content");
        List<Notification> notifications = Collections.singletonList(notification);

        // Run the test
        String result = auxiliary.notificationsToJson(notifications);

        // Verify the results
        assertThat(result).isEqualTo("[{\"id\":0,\"toUserId\":0,\"url\":\"url\",\"content\":\"content\"}]");
    }

    @Test
    void testMessagesToJson() {
        // Setup
        Message message = new Message();
        message.setId(0L);
        message.setUserIds("userIds");
        message.setFromUserId(0);
        message.setToUserId(0);
        message.setContent("content");
        List<Message> messages = Collections.singletonList(message);

        // Run the test
        String result = auxiliary.messagesToJson(messages);

        // Verify the results
        assertThat(result).isEqualTo("[{\"id\":0,\"userIds\":\"userIds\",\"fromUserId\":0,\"toUserId\":0,\"content\":\"content\"}]");
    }

    @Test
    void testIdentification() {
        // Setup
        Integer userId = 0;

        // Run the test
        Cookie result = auxiliary.Identification(userId);

        // Verify the results
        assertThat(result.getName()).isEqualTo("token");
        assertThat(result.getValue()).isEqualTo("U0");
        assertThat(result.getMaxAge()).isEqualTo(365 * 24 * 60 * 60);
        assertThat(result.getPath()).isEqualTo("/");
    }

    @Test
    void testExtractUserID_HeaderToken() {
        // Setup
        HttpServletRequest request = mock(HttpServletRequest.class);
        when(request.getHeader("token")).thenReturn("U0");

        // Run the test
        Integer result = auxiliary.extractUserID(request);

        // Verify the results
        assertThat(result).isEqualTo(0);
    }
    @Test
    void testExtractUserID_CookieToken() {
        // Setup
        HttpServletRequest request = mock(HttpServletRequest.class);
        Cookie cookie = new Cookie("token", "U0");
        when(request.getCookies()).thenReturn(new Cookie[]{cookie});
        // Run the test
        Integer result = auxiliary.extractUserID(request);

// Verify the results
        assertThat(result).isEqualTo(0);}

    //    }
    @Test
    void testExtractUserID_null() {
        // Setup
        HttpServletRequest request = mock(HttpServletRequest.class);
//        Cookie cookie = new Cookie("token", "U0");
//        when(request.getCookies()).thenReturn(new Cookie[]{});
        // Run the test
        Integer result = auxiliary.extractUserID(request);

        // Verify the results
        assertThat(result).isEqualTo(null);}


}
