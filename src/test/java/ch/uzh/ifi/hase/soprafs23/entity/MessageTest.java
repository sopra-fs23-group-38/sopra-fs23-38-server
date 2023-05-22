package ch.uzh.ifi.hase.soprafs23.entity;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Timestamp;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class MessageTest {
    @Test
    public void testMessage() {
        Message message = new Message();

        // Set the properties
        Long id = 1L;
        String userIds = "1,2";
        Integer fromUserId = 1;
        Integer toUserId = 2;
        String content = "Test message";
        Timestamp createTime = new Timestamp(new Date().getTime());
        Timestamp updateTime = new Timestamp(new Date().getTime());

        message.setId(id);
        message.setUserIds(userIds);
        message.setFromUserId(fromUserId);
        message.setToUserId(toUserId);
        message.setContent(content);
        message.setCreateTime(createTime);
        message.setUpdateTime(updateTime);

        // Now use the getters to verify that the setters worked
        assertEquals(id, message.getId());
        assertEquals(userIds, message.getUserIds());
        assertEquals(fromUserId, message.getFromUserId());
        assertEquals(toUserId, message.getToUserId());
        assertEquals(content, message.getContent());
        assertEquals(createTime, message.getCreateTime());
        assertEquals(updateTime, message.getUpdateTime());
    }
}
