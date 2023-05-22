package ch.uzh.ifi.hase.soprafs23.service;

import ch.uzh.ifi.hase.soprafs23.entity.Question;
import ch.uzh.ifi.hase.soprafs23.entity.User;
import ch.uzh.ifi.hase.soprafs23.repository.QuestionRepository;
import ch.uzh.ifi.hase.soprafs23.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mock.web.MockHttpServletRequest;

import java.util.*;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class QuestionServiceTest {

    @Mock
    private QuestionRepository mockQuestionRepository;
    @Mock
    private UserRepository mockUserRepository;

    @InjectMocks
    private QuestionService questionServiceUnderTest;

//    @Test
//    void testCreateQuestion() {
//        // Setup
//        final MockHttpServletRequest request = new MockHttpServletRequest();
//
//        // Run the test
//        final String result = questionServiceUnderTest.createQuestion(request);
//
//        // Verify the results
//        assertThat(result).isEqualTo("result");
//        verify(mockQuestionRepository).save(any(Question.class));
//    }

//    @Test
//    void testGetAllQuestions() {
//        // Setup
//        final MockHttpServletRequest request = new MockHttpServletRequest();
//
//        // Configure QuestionRepository.findTopNByOrderByTimeDesc(...).
//        final Question question = new Question();
//        question.setTag("tag");
//        question.setId(0);
//        question.setTitle("newTitle");
//        question.setDescription("newDescription");
//        question.setWho_asks(0);
//        question.setChange_time(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
//        final List<Question> questions = List.of(question);
//        when(mockQuestionRepository.findTopNByOrderByTimeDesc(0, 0)).thenReturn(questions);
//
//        // Configure QuestionRepository.findTopNByTagOrderByTimeDesc(...).
//        final Question question1 = new Question();
//        question1.setTag("tag");
//        question1.setId(0);
//        question1.setTitle("newTitle");
//        question1.setDescription("newDescription");
//        question1.setWho_asks(0);
//        question1.setChange_time(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
//        final List<Question> questions1 = List.of(question1);
//        when(mockQuestionRepository.findTopNByTagOrderByTimeDesc("tag", 0, 0)).thenReturn(questions1);
//
//        when(mockQuestionRepository.findQuestionerIdById(0)).thenReturn(0);
//
//        // Configure QuestionRepository.findUserById(...).
//        final User user = new User();
//        user.setId(0);
//        user.setUsername("username");
//        user.setPassword("password");
//        user.setEmail("email");
//        user.setRegister_time(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
//        when(mockQuestionRepository.findUserById(0)).thenReturn(user);
//
//        // Run the test
//        final String result = questionServiceUnderTest.getAllQuestions(0, "tag", "tag",request);
//
//        // Verify the results
//        assertThat(result).isEqualTo("result");
//    }

//    @Test
//    void testGetAllQuestions_QuestionRepositoryFindTopNByOrderByTimeDescReturnsNoItems() {
//        // Setup
//        final MockHttpServletRequest request = new MockHttpServletRequest();
//        when(mockQuestionRepository.findTopNByOrderByTimeDesc(0, 0)).thenReturn(Collections.emptyList());
//
//        // Run the test
//        final String result = questionServiceUnderTest.getAllQuestions(0, "tag","tag", request);
//
//        // Verify the results
//        assertThat(result).isEqualTo("result");
//    }

//    @Test
//    void testGetAllQuestions_QuestionRepositoryFindTopNByTagOrderByTimeDescReturnsNoItems() {
//        // Setup
//        final MockHttpServletRequest request = new MockHttpServletRequest();
//        when(mockQuestionRepository.findTopNByTagOrderByTimeDesc("tag", 0, 0)).thenReturn(Collections.emptyList());
//
//        // Run the test
//        final String result = questionServiceUnderTest.getAllQuestions(0, "tag","tag", request);
//
//        // Verify the results
//        assertThat(result).isEqualTo("result");
//    }

//    @Test
//    void testGetHowManyQuestions() {
//        // Setup
//        when(mockQuestionRepository.count()).thenReturn(0L);
//
//        // Run the test
//        final String result = questionServiceUnderTest.getHowManyQuestions();
//
//        // Verify the results
//        assertThat(result).isEqualTo("result");
//    }

//    @Test
//    void testGetQuestionsByWho() {
//        // Setup
//        // Configure QuestionRepository.findAllByWhoAsksOrderByChangeTimeDesc(...).
//        final Question question = new Question();
//        question.setTag("tag");
//        question.setId(0);
//        question.setTitle("newTitle");
//        question.setDescription("newDescription");
//        question.setWho_asks(0);
//        question.setChange_time(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
//        final List<Question> questions = List.of(question);
//        when(mockQuestionRepository.findAllByWhoAsksOrderByChangeTimeDesc(0)).thenReturn(questions);
//
//        // Run the test
//        final String result = questionServiceUnderTest.getQuestionsByWho(0);
//
//        // Verify the results
//        assertThat(result).isEqualTo("result");
//    }

//    @Test
//    void testGetQuestionsByWho_QuestionRepositoryReturnsNoItems() {
//        // Setup
//        when(mockQuestionRepository.findAllByWhoAsksOrderByChangeTimeDesc(0)).thenReturn(Collections.emptyList());
//
//        // Run the test
//        final String result = questionServiceUnderTest.getQuestionsByWho(0);
//
//        // Verify the results
//        assertThat(result).isEqualTo("result");
//    }

//    @Test
//    void testGetQuestionById() {
//        // Setup
//        final MockHttpServletRequest request = new MockHttpServletRequest();
//
//        // Configure QuestionRepository.findById(...).
//        final Question question1 = new Question();
//        question1.setTag("tag");
//        question1.setId(0);
//        question1.setTitle("newTitle");
//        question1.setDescription("newDescription");
//        question1.setWho_asks(0);
//        question1.setChange_time(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
//        final Optional<Question> question = Optional.of(question1);
//        when(mockQuestionRepository.findById(0)).thenReturn(question);
//
//        // Configure UserRepository.findById(...).
//        final User user1 = new User();
//        user1.setId(0);
//        user1.setUsername("username");
//        user1.setPassword("password");
//        user1.setEmail("email");
//        user1.setRegister_time(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
//        final Optional<User> user = Optional.of(user1);
//        when(mockUserRepository.findById(0)).thenReturn(user);
//
//        // Run the test
//        final String result = questionServiceUnderTest.getQuestionById(0, request);
//
//        // Verify the results
//        assertThat(result).isEqualTo("result");
//    }

//    @Test
//    void testGetQuestionById_QuestionRepositoryReturnsAbsent() {
//        // Setup
//        final MockHttpServletRequest request = new MockHttpServletRequest();
//        when(mockQuestionRepository.findById(0)).thenReturn(Optional.empty());
//
//        // Run the test
//        final String result = questionServiceUnderTest.getQuestionById(0, request);
//
//        // Verify the results
//        assertThat(result).isEqualTo("result");
//    }

//    @Test
//    void testGetQuestionById_UserRepositoryReturnsAbsent() {
//        // Setup
//        final MockHttpServletRequest request = new MockHttpServletRequest();
//
//        // Configure QuestionRepository.findById(...).
//        final Question question1 = new Question();
//        question1.setTag("tag");
//        question1.setId(0);
//        question1.setTitle("newTitle");
//        question1.setDescription("newDescription");
//        question1.setWho_asks(0);
//        question1.setChange_time(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
//        final Optional<Question> question = Optional.of(question1);
//        when(mockQuestionRepository.findById(0)).thenReturn(question);
//
//        when(mockUserRepository.findById(0)).thenReturn(Optional.empty());
//
//        // Run the test
//        final String result = questionServiceUnderTest.getQuestionById(0, request);
//
//        // Verify the results
//        assertThat(result).isEqualTo("result");
//    }

//    @Test
//    void testUpdateQuestion() {
//        // Setup
//        final MockHttpServletRequest request = new MockHttpServletRequest();
//
//        // Configure QuestionRepository.findById(...).
//        final Question question1 = new Question();
//        question1.setTag("tag");
//        question1.setId(0);
//        question1.setTitle("newTitle");
//        question1.setDescription("newDescription");
//        question1.setWho_asks(0);
//        question1.setChange_time(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
//        final Optional<Question> question = Optional.of(question1);
//        when(mockQuestionRepository.findById(0)).thenReturn(question);
//
//        // Run the test
//        final String result = questionServiceUnderTest.updateQuestion(0, "newTitle", "newDescription", request);
//
//        // Verify the results
//        assertThat(result).isEqualTo("result");
//        verify(mockQuestionRepository).save(any(Question.class));
//    }
//
//    @Test
//    void testUpdateQuestion_QuestionRepositoryFindByIdReturnsAbsent() {
//        // Setup
//        final MockHttpServletRequest request = new MockHttpServletRequest();
//        when(mockQuestionRepository.findById(0)).thenReturn(Optional.empty());
//
//        // Run the test
//        final String result = questionServiceUnderTest.updateQuestion(0, "newTitle", "newDescription", request);
//
//        // Verify the results
//        assertThat(result).isEqualTo("result");
//    }

//    @Test
//    void testDeleteQuestion() {
//        // Setup
//        final MockHttpServletRequest request = new MockHttpServletRequest();
//
//        // Run the test
//        final String result = questionServiceUnderTest.deleteQuestion(0, request);
//
//        // Verify the results
//        assertThat(result).isEqualTo("result");
//        verify(mockQuestionRepository).deleteById(0);
//    }
}
