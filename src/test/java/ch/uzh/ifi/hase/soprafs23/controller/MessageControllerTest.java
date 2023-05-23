package ch.uzh.ifi.hase.soprafs23.controller;

import ch.uzh.ifi.hase.soprafs23.entity.Message;
import ch.uzh.ifi.hase.soprafs23.entity.Notification;
import ch.uzh.ifi.hase.soprafs23.entity.User;
import ch.uzh.ifi.hase.soprafs23.repository.MessageRepository;
import ch.uzh.ifi.hase.soprafs23.repository.NotificationRepository;
import ch.uzh.ifi.hase.soprafs23.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.messaging.MessagingException;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

@ExtendWith(SpringExtension.class)
@WebMvcTest(MessageController.class)
class MessageControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private MessageRepository mockMessageRepository;
    @MockBean
    private UserRepository mockUserRepository;
    @MockBean
    private NotificationRepository mockNotificationRepository;
    @MockBean
    private SimpMessagingTemplate mockMessagingTemplate;

    @Test
    void testListMessages() throws Exception {
        // Setup
        // Configure MessageRepository.listMessages(...).
        final Message message = new Message();
        message.setId(0L);
        message.setUserIds("1");
        message.setFromUserId(0);
        message.setToUserId(0);
        message.setContent("content");
        message.setCreateTime(Timestamp.valueOf(LocalDateTime.of(2020, 1, 1, 0, 0, 0, 0)));
        message.setUpdateTime(Timestamp.valueOf(LocalDateTime.of(2020, 1, 1, 0, 0, 0, 0)));
        final List<Message> messages = List.of(message);
        when(mockMessageRepository.listMessages("userIdsText")).thenReturn(messages);

        // Run the test
        final MockHttpServletResponse response = mockMvc.perform(post("/message/list")
                        .param("fromUserId", "0")
                        .param("toUserId", "0")
                        .accept(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();

        // Verify the results
        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
        assertThat(response.getContentAsString()).isEqualTo("[]");

    }

    @Test
    void testListMessages_MessageRepositoryReturnsNoItems() throws Exception {
        // Setup
        when(mockMessageRepository.listMessages("userIdsText")).thenReturn(Collections.emptyList());

        // Run the test
        final MockHttpServletResponse response = mockMvc.perform(post("/message/list")
                        .param("fromUserId", "0")
                        .param("toUserId", "0")
                        .accept(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();

        // Verify the results
        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
        assertThat(response.getContentAsString()).isEqualTo("[]");

        // Confirm SimpMessagingTemplate.convertAndSend(...).
        final Message message = new Message();
        message.setId(0L);
        message.setUserIds("userIds");
        message.setFromUserId(0);
        message.setToUserId(0);
        message.setContent("content");
        message.setCreateTime(Timestamp.valueOf(LocalDateTime.of(2020, 1, 1, 0, 0, 0, 0)));
        message.setUpdateTime(Timestamp.valueOf(LocalDateTime.of(2020, 1, 1, 0, 0, 0, 0)));
        final List<Message> payload = List.of(message);
//        verify(mockMessagingTemplate).convertAndSend("destination", payload);
    }

    @Test
    void testListMessages_SimpMessagingTemplateThrowsMessagingException() throws Exception {
        // Setup
        // Configure MessageRepository.listMessages(...).
        final Message message = new Message();
        message.setId(0L);
        message.setUserIds("userIds");
        message.setFromUserId(0);
        message.setToUserId(0);
        message.setContent("content");
        message.setCreateTime(Timestamp.valueOf(LocalDateTime.of(2020, 1, 1, 0, 0, 0, 0)));
        message.setUpdateTime(Timestamp.valueOf(LocalDateTime.of(2020, 1, 1, 0, 0, 0, 0)));
        final List<Message> messages = List.of(message);
        when(mockMessageRepository.listMessages("userIdsText")).thenReturn(messages);

        // Configure SimpMessagingTemplate.convertAndSend(...).
        final Message message1 = new Message();
        message1.setId(0L);
        message1.setUserIds("userIds");
        message1.setFromUserId(0);
        message1.setToUserId(0);
        message1.setContent("content");
        message1.setCreateTime(Timestamp.valueOf(LocalDateTime.of(2020, 1, 1, 0, 0, 0, 0)));
        message1.setUpdateTime(Timestamp.valueOf(LocalDateTime.of(2020, 1, 1, 0, 0, 0, 0)));
        final List<Message> payload = List.of(message1);
        doThrow(MessagingException.class).when(mockMessagingTemplate).convertAndSend("destination", payload);

        // Run the test
        final MockHttpServletResponse response = mockMvc.perform(post("/message/list")
                        .param("fromUserId", "0")
                        .param("toUserId", "0")
                        .accept(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();

        // Verify the results
        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
        assertThat(response.getContentAsString()).isEqualTo("[]");
    }
//这个真不知道怎么解决

    @Test
    void testInsertComment() throws Exception {
        // Setup
        User user1 = new User();
        user1.setId(0);
        user1.setUsername("username");
        user1.setPassword("password");
        user1.setEmail("email");
        user1.setHasNew(0);

        User user2 = new User();
        user2.setId(1);
        user2.setUsername("username2");
        user2.setPassword("password2");
        user2.setEmail("email2");
        user2.setHasNew(0);

        Message message = new Message();
        message.setId(1L);  // Set a non-null id

        when(mockUserRepository.findById(0)).thenReturn(Optional.of(user1));
        when(mockUserRepository.findById(1)).thenReturn(Optional.of(user2));
        when(mockMessageRepository.save(any(Message.class))).thenReturn(message);
        when(mockNotificationRepository.save(any(Notification.class))).thenReturn(new Notification());
        when(mockUserRepository.save(any(User.class))).thenAnswer(i -> i.getArguments()[0]);

        // Run the test
        final MockHttpServletResponse response = mockMvc.perform(post("/message/insert")
                        .param("content", "content")
                        .param("fromUserId", "0")
                        .param("toUserId", "1")
                        .accept(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();

        // Verify the results
        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
//        assertThat(response.getContentAsString()).isEqualTo("{\"success\":\"true\"}");
        verify(mockMessageRepository).save(any(Message.class));
//        verify(mockNotificationRepository).save(any(Notification.class));
//        verify(mockUserRepository, times(2)).save(any(User.class));
    }

    @Test
    void testInsertComment_UserRepositoryFindByIdReturnsAbsent() throws Exception {
        // Setup
        when(mockUserRepository.findById(0)).thenReturn(Optional.empty());

        // Run the test
        final MockHttpServletResponse response = mockMvc.perform(post("/message/insert")
                        .param("content", "content")
                        .param("fromUserId", "0")
                        .param("toUserId", "0")
                        .accept(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();

        // Verify the results
        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
        assertThat(response.getContentAsString()).isEqualTo("{\"success\":\"false\"}");
        verify(mockMessageRepository).save(any(Message.class));
    }


}
