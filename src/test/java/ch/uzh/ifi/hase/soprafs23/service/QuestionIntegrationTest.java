package ch.uzh.ifi.hase.soprafs23.service;

import ch.uzh.ifi.hase.soprafs23.entity.Question;
import ch.uzh.ifi.hase.soprafs23.entity.User;
import ch.uzh.ifi.hase.soprafs23.repository.AnswerRepository;
import ch.uzh.ifi.hase.soprafs23.repository.QuestionRepository;
import ch.uzh.ifi.hase.soprafs23.repository.UserRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.verify;

import ch.uzh.ifi.hase.soprafs23.Auxiliary.auxiliary;
@ExtendWith(MockitoExtension.class)
public class QuestionIntegrationTest {
    @Mock
    private QuestionRepository mockQuestionRepository;
    @Mock
    private UserRepository mockUserRepository;
    @Mock
    private AnswerRepository answerRepository;
    @InjectMocks
    private QuestionService questionServiceUnderTest;

    @Test
    void testQuestionIntegration(){
        // Setup
        HttpServletRequest request = mock(HttpServletRequest.class);
        when(request.getParameter("title")).thenReturn("11");
        when(request.getParameter("description")).thenReturn("123");
        when(request.getParameter("tag")).thenReturn("Study");
        when(request.getHeader("token")).thenReturn("U3");
        final String result = questionServiceUnderTest.createQuestion(request);
        // Verify the results
        Assertions.assertNotNull(result);
        // Verify that the questionRepository.save method was called
        verify(mockQuestionRepository, times(1)).save(any(Question.class));
        HttpServletRequest request1 = mock(HttpServletRequest.class);
        final String result1 = questionServiceUnderTest.getHowManyQuestions();

    }
    @Test
    void testCreateRetrieveUpdateDeleteQuestion() {
        HttpServletRequest createRequest = mock(HttpServletRequest.class);
        when(createRequest.getParameter("title")).thenReturn("11");
        when(createRequest.getParameter("description")).thenReturn("123");
        when(createRequest.getParameter("tag")).thenReturn("Study");
        when(createRequest.getHeader("token")).thenReturn("U3");
        final String createResult = questionServiceUnderTest.createQuestion(createRequest);
        Assertions.assertNotNull(createResult);
        verify(mockQuestionRepository).save(any(Question.class));

        HttpServletRequest retrieveRequest = mock(HttpServletRequest.class);
        final String retrieveResult = questionServiceUnderTest.getAllQuestions(1, "All", "tag", retrieveRequest);
        Assertions.assertNotNull(retrieveResult);

        HttpServletRequest updateRequest = mock(HttpServletRequest.class);
        final String updateResult = questionServiceUnderTest.updateQuestion(0, "newTitle", "newDescription", updateRequest);
        Assertions.assertNotNull(updateResult);
        verify(mockQuestionRepository).save(any(Question.class));

        HttpServletRequest deleteRequest = mock(HttpServletRequest.class);
        final String deleteResult = questionServiceUnderTest.deleteQuestion(0, deleteRequest);
        Assertions.assertNotNull(deleteResult);
        verify(mockQuestionRepository).deleteById(0);
    }



}
