package ch.uzh.ifi.hase.soprafs23.service;

import ch.uzh.ifi.hase.soprafs23.Auxiliary.AuxiliaryWrapper;
import ch.uzh.ifi.hase.soprafs23.Auxiliary.auxiliary;
import ch.uzh.ifi.hase.soprafs23.entity.Answer;
import ch.uzh.ifi.hase.soprafs23.entity.Comment;
import ch.uzh.ifi.hase.soprafs23.entity.Notification;
import ch.uzh.ifi.hase.soprafs23.entity.User;
import ch.uzh.ifi.hase.soprafs23.repository.AnswerRepository;
import ch.uzh.ifi.hase.soprafs23.repository.CommentRepository;
import ch.uzh.ifi.hase.soprafs23.repository.NotificationRepository;
import ch.uzh.ifi.hase.soprafs23.repository.UserRepository;
import org.junit.Before;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import javax.servlet.http.HttpServletRequest;
import java.time.Instant;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

//@RunWith(MockitoJUnitRunner.class)
//public class CommentServiceTest {
//
//    @InjectMocks
//    CommentService commentService;
//
//    @Mock
//    CommentRepository commentRepository;
//    @Mock
//    UserRepository userRepository;
//    @Mock
//    AnswerRepository answerRepository;
//    @Mock
//    NotificationRepository notificationRepository;
//    @MockBean
//    private AuxiliaryWrapper auxiliaryWrapper;
//    @Mock
//    HttpServletRequest mockHttpServletRequest;
//    @Test
//    public void createComment() {
//        Integer userId = 1;
//        Integer answerId = 1;
//        String content = "Test Comment";
//        Integer parentId = null;
//
//        when(auxiliaryWrapper.extractUserID(mockHttpServletRequest)).thenReturn(userId);
//
//        User mockUser = mock(User.class);
//        when(userRepository.findById(userId)).thenReturn(Optional.of(mockUser));
//
//        Answer mockAnswer = mock(Answer.class);
//        when(answerRepository.findById(answerId)).thenReturn(Optional.of(mockAnswer));
//
//        Comment mockComment = mock(Comment.class);
//        when(commentRepository.save(any(Comment.class))).thenReturn(mockComment);
//
//        when(mockComment.getId()).thenReturn(1);
//
//        User mockAnswerUser = mock(User.class);
//        when(userRepository.findById(mockAnswer.getWho_answers())).thenReturn(Optional.of(mockAnswerUser));
//
//        Map<String, Object> infobody = new HashMap<>();
//        infobody.put("success", "true");
//        infobody.put("type", "answer");
//        when(auxiliaryWrapper.mapObjectToJson(infobody)).thenReturn("Comment Created Successfully");
//
//        String result = commentService.createComment(answerId, content, parentId, mockHttpServletRequest);
//        assertEquals("Comment Created Successfully", result);
//    }
//}