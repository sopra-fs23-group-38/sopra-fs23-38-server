package ch.uzh.ifi.hase.soprafs23.service;

import ch.uzh.ifi.hase.soprafs23.entity.Message;
import ch.uzh.ifi.hase.soprafs23.entity.User;
import ch.uzh.ifi.hase.soprafs23.repository.MessageRepository;
import ch.uzh.ifi.hase.soprafs23.repository.UserRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.mockito.junit.jupiter.MockitoExtension;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;
import java.util.List;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
class MessageServiceTest {

    @Mock
    private MessageRepository mockMessageRepository;
    @Mock
    private UserRepository mockUserRepository;

    @InjectMocks
    private MessageService messageServiceUnderTest;

    @Test
    void testFetchMessageList() {
        // Setup
        Integer fromUserId = 1;
        Integer toUserId = 2;

        // Mocking the User objects
        User fromUser = new User();
        fromUser.setAvatarUrl("fromUserAvatarURL");
        User toUser = new User();
        toUser.setAvatarUrl("toUserAvatarURL");

        // Mocking the Message object
        Message message = new Message();
        message.setFromUserId(fromUserId);

        List<Message> messages = List.of(message);

        // Configuring the return values of the mock objects
        when(mockUserRepository.findById(fromUserId)).thenReturn(Optional.of(fromUser));
        when(mockUserRepository.findById(toUserId)).thenReturn(Optional.of(toUser));
        when(mockMessageRepository.listMessages(anyString())).thenReturn(messages);

        // Run the test
        String result = messageServiceUnderTest.fetchMessageList(fromUserId, toUserId);

        // Verify the results
        Assertions.assertNotNull(result);
    }
}