package ch.uzh.ifi.hase.soprafs23.entity;

import org.junit.jupiter.api.BeforeEach;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Date;

public class NotificationTest {
    @Test
    public void testNotification() {
        Notification notification = new Notification();

        // Set the properties
        Integer id = 1;
        Integer toUserId = 2;
        String url = "http://example.com";
        String content = "Test notification";
        Date createTime = new Date();

        notification.setId(id);
        notification.setToUserId(toUserId);
        notification.setUrl(url);
        notification.setContent(content);
        notification.setCreateTime(createTime);

        // Now use the getters to verify that the setters worked
        assertEquals(id, notification.getId());
        assertEquals(toUserId, notification.getToUserId());
        assertEquals(url, notification.getUrl());
        assertEquals(content, notification.getContent());
        assertEquals(createTime, notification.getCreateTime());
    }
}
