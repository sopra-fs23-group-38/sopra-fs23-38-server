package ch.uzh.ifi.hase.soprafs23.entity;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class NotificationTest {
    @Test
    public void testSetAndGetId() {
        Notification notification = new Notification();
        notification.setId(1);
        assertEquals(1, notification.getId());
    }

    @Test
    public void testSetAndGetToUserId() {
        Notification notification = new Notification();
        notification.setToUserId(2);
        assertEquals(2, notification.getToUserId());
    }

    @Test
    public void testSetAndGetUrl() {
        Notification notification = new Notification();
        notification.setUrl("url");
        assertEquals("url", notification.getUrl());
    }

    @Test
    public void testSetAndGetContent() {
        Notification notification = new Notification();
        notification.setContent("content");
        assertEquals("content", notification.getContent());
    }

    @Test
    public void testSetAndGetCreateTime() {
        Date date = new Date();
        Notification notification = new Notification();
        notification.setCreateTime(date);
        assertEquals(date, notification.getCreateTime());
    }
}
