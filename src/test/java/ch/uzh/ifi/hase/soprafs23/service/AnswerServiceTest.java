package ch.uzh.ifi.hase.soprafs23.service;

import ch.uzh.ifi.hase.soprafs23.Auxiliary.auxiliary;
import ch.uzh.ifi.hase.soprafs23.entity.*;
import ch.uzh.ifi.hase.soprafs23.repository.*;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mock.web.MockHttpServletRequest;
import org.mockito.Mockito;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.notNull;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AnswerServiceTest {

    @Mock
    private QuestionRepository mockQuestionRepository;
    @Mock
    private AnswerRepository mockAnswerRepository;
    @Mock
    private UserRepository mockUserRepository;
    @Mock
    private NotificationRepository mockNotificationRepository;
    @Mock
    private VoteRepository mockVoteRepository;
    @InjectMocks
    private AnswerService answerServiceUnderTest;
    @Mock
    private CommentRepository commentRepository;
    @Test
    void testCreateAnswer() {
        // Setup
        HttpServletRequest request = mock(HttpServletRequest.class);

        when(request.getHeader(eq("token"))).thenReturn("U3");
        // Configure QuestionRepository.findById(...).
        final Question question1 = new Question();
        question1.setTag("tag");
        question1.setId(0);
        question1.setTitle("title");
        question1.setDescription("description");
        question1.setWho_asks(0);
        question1.setAnswerCount(1);
        final Optional<Question> question = Optional.of(question1);
        when(mockQuestionRepository.findById(Mockito.any())).thenReturn(question);

        // Configure UserRepository.findById(...).
        final User user1 = new User();
        user1.setId(0);
        user1.setUsername("username");
        user1.setPassword("password");
        user1.setEmail("email");
        user1.setHasNew(0);
        final Optional<User> user = Optional.of(user1);
        when(mockUserRepository.findById(Mockito.any())).thenReturn(user);

        // Run the test
        final String result = answerServiceUnderTest.createAnswer(1, "content", request);

        // Verify the results
        Assertions.assertNotNull(result);
        verify(mockAnswerRepository).save(any(Answer.class));
        //verify(mockQuestionRepository).save(any(Question.class));
        // verify(mockNotificationRepository).save(any(Notification.class));
//        verify(mockUserRepository).save(any(User.class));

    }

    @Test
    void testCreateAnswer_QuestionRepositoryFindByIdReturnsAbsent() {
        // Setup
        HttpServletRequest request = mock(HttpServletRequest.class);

        when(request.getHeader(eq("token"))).thenReturn("U3");
        //when(mockQuestionRepository.findById(0)).thenReturn(Optional.empty());

        // Configure UserRepository.findById(...).
        final User user1 = new User();
        user1.setId(0);
        user1.setUsername("username");
        user1.setPassword("password");
        user1.setEmail("email");
        user1.setHasNew(0);
        final Optional<User> user = Optional.of(user1);
        // when(mockUserRepository.findById(0)).thenReturn(user);

        // Run the test
        final String result = answerServiceUnderTest.createAnswer(Mockito.any(), "content", request);

        // Verify the results
        Assertions.assertNotNull(result);
        //verify(mockAnswerRepository).save(any(Answer.class));
        //verify(mockQuestionRepository).save(any(Question.class));
        // verify(mockNotificationRepository).save(any(Notification.class));
        // verify(mockUserRepository).save(any(User.class));
    }

    @Test
    void testCreateAnswer_UserRepositoryFindByIdReturnsAbsent() {
        // Setup
        //final MockHttpServletRequest request = new MockHttpServletRequest();

        // Configure QuestionRepository.findById(...).
        final Question question1 = new Question();
        question1.setTag("tag");
        question1.setId(0);
        question1.setTitle("title");
        question1.setDescription("description");
        question1.setWho_asks(0);
        final Optional<Question> question = Optional.of(question1);
        when(mockQuestionRepository.findById(0)).thenReturn(question);

        when(mockUserRepository.findById(0)).thenReturn(Optional.empty());

        // Setup
        HttpServletRequest request = mock(HttpServletRequest.class);
        when(request.getHeader("token")).thenReturn("U0");

        // Run the test
        final String result = answerServiceUnderTest.createAnswer(0, "content", request);

        // Verify the results
        Assertions.assertNotNull(result);
        verify(mockAnswerRepository).save(any(Answer.class));
    }

    @Test
    void testGetAllAnsToOneQuestion() {
        // Setup
        final MockHttpServletRequest request = new MockHttpServletRequest();

        // Configure AnswerRepository.findByAllQuestionIdOrderByVoteCountDesc(...).
        final Answer answer = new Answer();
        answer.setId(0);
        answer.setQuestion_id(0);
        answer.setWho_answers(0);
        answer.setContent("content");
        answer.setChange_time(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
        answer.setComment_count(0);
        answer.setVote_count(0);
        final List<Answer> answers = List.of(answer);
        when(mockAnswerRepository.findByAllQuestionIdOrderByVoteCountDesc(0)).thenReturn(answers);

        when(mockAnswerRepository.findAnswererIdById(0)).thenReturn(0);

        // Configure AnswerRepository.findUserById(...).
        final User user = new User();
        user.setId(0);
        user.setUsername("username");
        user.setPassword("password");
        user.setEmail("email");
        user.setHasNew(0);
        when(mockAnswerRepository.findUserById(0)).thenReturn(user);

        // Run the test
        final String result = answerServiceUnderTest.getAllAnsToOneQuestion(0, 0, request);

        // Verify the results
        Assertions.assertNotNull(result);
    }

    @Test
    void testGetAllAnsToOneQuestion_AnswerRepositoryFindByAllQuestionIdOrderByVoteCountDescReturnsNull() {
        // Setup
        final MockHttpServletRequest request = new MockHttpServletRequest();
        when(mockAnswerRepository.findByAllQuestionIdOrderByVoteCountDesc(0)).thenReturn(null);

        // Run the test
        final String result = answerServiceUnderTest.getAllAnsToOneQuestion(0, 0, request);

        // Verify the results
        Assertions.assertNotNull(result);
    }

    @Test
    void testGetAllAnsToOneQuestion_AnswerRepositoryFindByAllQuestionIdOrderByVoteCountDescReturnsNoItems() {
        // Setup
        final MockHttpServletRequest request = new MockHttpServletRequest();
        when(mockAnswerRepository.findByAllQuestionIdOrderByVoteCountDesc(0)).thenReturn(Collections.emptyList());

        // Run the test
        final String result = answerServiceUnderTest.getAllAnsToOneQuestion(0, 0, request);

        // Verify the results
        Assertions.assertNotNull(result);
    }

    @Test
    void testGetAnswerById() {
        // Setup
        final MockHttpServletRequest request = new MockHttpServletRequest();

        // Configure AnswerRepository.findById(...).
        final Answer answer1 = new Answer();
        answer1.setId(0);
        answer1.setQuestion_id(0);
        answer1.setWho_answers(0);
        answer1.setContent("content");
        answer1.setChange_time(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
        answer1.setComment_count(0);
        answer1.setVote_count(0);
        final Optional<Answer> answer = Optional.of(answer1);
        when(mockAnswerRepository.findById(0)).thenReturn(answer);

        // Configure UserRepository.findById(...).
        final User user1 = new User();
        user1.setId(0);
        user1.setUsername("username");
        user1.setPassword("password");
        user1.setEmail("email");
        user1.setHasNew(0);
        final Optional<User> user = Optional.of(user1);
        when(mockUserRepository.findById(0)).thenReturn(user);

        // Configure QuestionRepository.findById(...).
        final Question question1 = new Question();
        question1.setTag("tag");
        question1.setId(0);
        question1.setTitle("title");
        question1.setDescription("description");
        question1.setWho_asks(0);
        final Optional<Question> question = Optional.of(question1);
        when(mockQuestionRepository.findById(0)).thenReturn(question);

        // Run the test
        final String result = answerServiceUnderTest.getAnswerById(0, request);

        // Verify the results
        Assertions.assertNotNull(result);
    }

    @Test
    void testGetAnswerById_AnswerRepositoryReturnsAbsent() {
        // Setup
        final MockHttpServletRequest request = new MockHttpServletRequest();
        when(mockAnswerRepository.findById(0)).thenReturn(Optional.empty());

        // Run the test
        final String result = answerServiceUnderTest.getAnswerById(0, request);

        // Verify the results
        Assertions.assertNotNull(result);
    }

    @Test
    void testGetAnswerById_UserRepositoryReturnsAbsent() {
        // Setup
        final MockHttpServletRequest request = new MockHttpServletRequest();

        // Configure AnswerRepository.findById(...).
        final Answer answer1 = new Answer();
        answer1.setId(0);
        answer1.setQuestion_id(0);
        answer1.setWho_answers(0);
        answer1.setContent("content");
        answer1.setChange_time(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
        answer1.setComment_count(0);
        answer1.setVote_count(0);
        final Optional<Answer> answer = Optional.of(answer1);
        when(mockAnswerRepository.findById(0)).thenReturn(answer);

        when(mockUserRepository.findById(0)).thenReturn(Optional.empty());

        // Configure QuestionRepository.findById(...).
        final Question question1 = new Question();
        question1.setTag("tag");
        question1.setId(0);
        question1.setTitle("title");
        question1.setDescription("description");
        question1.setWho_asks(0);
        final Optional<Question> question = Optional.of(question1);
        when(mockQuestionRepository.findById(0)).thenReturn(question);

        // Run the test
        final String result = answerServiceUnderTest.getAnswerById(0, request);

        // Verify the results
        Assertions.assertNotNull(result);
    }

    @Test
    void testGetAnswerById_QuestionRepositoryReturnsAbsent() {
        // Setup
        final MockHttpServletRequest request = new MockHttpServletRequest();

        // Configure AnswerRepository.findById(...).
        final Answer answer1 = new Answer();
        answer1.setId(0);
        answer1.setQuestion_id(0);
        answer1.setWho_answers(0);
        answer1.setContent("content");
        answer1.setChange_time(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
        answer1.setComment_count(0);
        answer1.setVote_count(0);
        final Optional<Answer> answer = Optional.of(answer1);
        when(mockAnswerRepository.findById(0)).thenReturn(answer);

        // Configure UserRepository.findById(...).
        final User user1 = new User();
        user1.setId(0);
        user1.setUsername("username");
        user1.setPassword("password");
        user1.setEmail("email");
        user1.setHasNew(0);
        Optional<User> user = Optional.of(user1);
        when(mockUserRepository.findById(0)).thenReturn(user);

        when(mockQuestionRepository.findById(0)).thenReturn(Optional.empty());

        // Run the test
        final String result = answerServiceUnderTest.getAnswerById(0, request);

        // Verify the results
        Assertions.assertNotNull(result);
    }

    @Test
    void testGetAnswersByWho() {
        // Setup
        // Configure AnswerRepository.findAllByWhoAnswersOrderByChangeTimeDesc(...).
        final Answer answer = new Answer();
        answer.setId(0);
        answer.setQuestion_id(0);
        answer.setWho_answers(0);
        answer.setContent("content");
        answer.setChange_time(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
        answer.setComment_count(0);
        answer.setVote_count(0);
        final List<Answer> answers = List.of(answer);
        when(mockAnswerRepository.findAllByWhoAnswersOrderByChangeTimeDesc(0)).thenReturn(answers);

        when(mockAnswerRepository.findAnswerToQuestionTitle(0)).thenReturn("result");

        // Run the test
        final String result = answerServiceUnderTest.getAnswersByWho(0);

        // Verify the results
        Assertions.assertNotNull(result);
    }

    @Test
    void testGetAnswersByWho_AnswerRepositoryFindAllByWhoAnswersOrderByChangeTimeDescReturnsNoItems() {
        // Setup
        when(mockAnswerRepository.findAllByWhoAnswersOrderByChangeTimeDesc(0)).thenReturn(Collections.emptyList());

        // Run the test
        final String result = answerServiceUnderTest.getAnswersByWho(0);

        // Verify the results
        Assertions.assertNotNull(result);
    }

    @Test
    void testUporDownVote() {
        // Setup
        // Configure AnswerRepository.findById(...).
        final Answer answer1 = new Answer();
        answer1.setId(0);
        answer1.setQuestion_id(0);
        answer1.setWho_answers(0);
        answer1.setContent("content");
        answer1.setChange_time(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
        answer1.setComment_count(0);
        answer1.setVote_count(0);
        final Optional<Answer> answer = Optional.of(answer1);
        when(mockAnswerRepository.findById(0)).thenReturn(answer);

        // Run the test
        final String result = answerServiceUnderTest.UporDownVote(0, 0);

        // Verify the results
        Assertions.assertNotNull(result);
        verify(mockVoteRepository).save(any(Vote.class));
    }

    @Test
    void testUporDownVote_AnswerRepositoryReturnsAbsent() {
        // Setup
        when(mockAnswerRepository.findById(0)).thenReturn(Optional.empty());

        // Run the test
        final String result = answerServiceUnderTest.UporDownVote(0, 0);

        // Verify the results
        Assertions.assertNotNull(result);
    }

    @Test
    void testUpdateAnswer() {
        // Setup
        final MockHttpServletRequest request = new MockHttpServletRequest();

        // Configure AnswerRepository.findById(...).
        final Answer answer1 = new Answer();
        answer1.setId(0);
        answer1.setQuestion_id(0);
        answer1.setWho_answers(0);
        answer1.setContent("content");
        answer1.setChange_time(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
        answer1.setComment_count(0);
        answer1.setVote_count(0);
        final Optional<Answer> answer = Optional.of(answer1);
        when(mockAnswerRepository.findById(0)).thenReturn(answer);

        // Run the test
        final String result = answerServiceUnderTest.updateAnswer(0, "content", request);

        // Verify the results
        Assertions.assertNotNull(result);
        verify(mockAnswerRepository).save(any(Answer.class));
    }

    @Test
    void testUpdateAnswer_AnswerRepositoryFindByIdReturnsAbsent() {
        // Setup
        final MockHttpServletRequest request = new MockHttpServletRequest();
        when(mockAnswerRepository.findById(0)).thenReturn(Optional.empty());

        // Run the test
        final String result = answerServiceUnderTest.updateAnswer(0, "content", request);

        // Verify the results
        Assertions.assertNotNull(result);
    }

    @Test
    void testDeleteAnswer() {
        // Setup
        final MockHttpServletRequest request = new MockHttpServletRequest();

        // Configure AnswerRepository.findById(...).
        final Answer answer1 = new Answer();
        answer1.setId(0);
        answer1.setQuestion_id(0);
        answer1.setWho_answers(0);
        answer1.setContent("content");
        answer1.setChange_time(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
        answer1.setComment_count(0);
        answer1.setVote_count(0);
        final Optional<Answer> answer = Optional.of(answer1);
        when(mockAnswerRepository.findById(0)).thenReturn(answer);

        // Configure QuestionRepository.findById(...).
        final Question question1 = new Question();
        question1.setTag("tag");
        question1.setId(0);
        question1.setAnswerCount(1);
        question1.setTitle("title");
        question1.setDescription("description");
        question1.setWho_asks(0);
        final Optional<Question> question = Optional.of(question1);
        when(mockQuestionRepository.findById(0)).thenReturn(question);

        // Run the test
        final String result = answerServiceUnderTest.deleteAnswer(0, request);

        // Verify the results
        Assertions.assertNotNull(result);
        verify(mockQuestionRepository).save(any(Question.class));
        verify(mockVoteRepository).deleteVotesByAnswerId(0);
        verify(mockAnswerRepository).deleteAnswerById(0);
    }

    @Test
    void testDeleteAnswer_AnswerRepositoryFindByIdReturnsAbsent() {
        // Setup
        final MockHttpServletRequest request = new MockHttpServletRequest();
        when(mockAnswerRepository.findById(0)).thenReturn(Optional.empty());

        // Run the test
        final String result = answerServiceUnderTest.deleteAnswer(0, request);

        // Verify the results
        Assertions.assertNotNull(result);
    }

    @Test
    void testDeleteAnswer_QuestionRepositoryFindByIdReturnsAbsent() {
        // Setup
        final MockHttpServletRequest request = new MockHttpServletRequest();

        // Configure AnswerRepository.findById(...).
        final Answer answer1 = new Answer();
        answer1.setId(0);
        answer1.setQuestion_id(0);
        answer1.setWho_answers(0);
        answer1.setContent("content");
        answer1.setChange_time(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
        answer1.setComment_count(0);
        answer1.setVote_count(0);
        final Optional<Answer> answer = Optional.of(answer1);
        when(mockAnswerRepository.findById(0)).thenReturn(answer);

        when(mockQuestionRepository.findById(0)).thenReturn(Optional.empty());

        // Run the test
        final String result = answerServiceUnderTest.deleteAnswer(0, request);

        // Verify the results
        Assertions.assertNotNull(result);
        verify(mockVoteRepository).deleteVotesByAnswerId(0);
        verify(mockAnswerRepository).deleteAnswerById(0);
    }

    @Test
    void testGetHowManyPages() {
        // Setup
        when(mockAnswerRepository.countAnswersByQuestionId(0)).thenReturn(0);

        // Run the test
        final String result = answerServiceUnderTest.getHowManyPages(0);

        // Verify the results
        Assertions.assertNotNull(result);
    }
}
