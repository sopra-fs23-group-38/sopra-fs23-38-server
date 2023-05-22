package ch.uzh.ifi.hase.soprafs23.service;

import static org.mockito.Mockito.*;
import static org.assertj.core.api.Assertions.*;

import ch.uzh.ifi.hase.soprafs23.repository.AnswerRepository;
import ch.uzh.ifi.hase.soprafs23.repository.CommentRepository;
import ch.uzh.ifi.hase.soprafs23.repository.QuestionRepository;
import ch.uzh.ifi.hase.soprafs23.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.mock.web.MockHttpServletRequest;

import java.math.BigDecimal;
import java.util.*;

class SearchServiceTest {

    @Mock
    private QuestionRepository mockQuestionRepository;
    @Mock
    private AnswerRepository mockAnswerRepository;
    @Mock
    private UserRepository mockUserRepository;
    @Mock
    private CommentRepository mockCommentRepository;
    @InjectMocks
    private SearchService searchServiceUnderTest;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void testSearchKeyword() {
        // Setup
        final String keyword = "test";
        final String searchType = "All";  // "User", "Question", "Answer", "Comment", "All"

        // Mocking return value of UserFindByKeyword from UserRepository
        List<Object[]> userResults = new ArrayList<>();
        userResults.add(new Object[]{1, "username1", new BigDecimal(100)});
        when(mockUserRepository.UserFindByKeyword(keyword)).thenReturn(userResults);

        // Mocking return value of QuestionFindByKeyword from QuestionRepository
        List<Object[]> questionResults = new ArrayList<>();
        questionResults.add(new Object[]{1, "title1", "description1", new BigDecimal(100)});
        when(mockQuestionRepository.QuestionFindByKeyword(keyword)).thenReturn(questionResults);

        // Mocking return value of AnswerFindByKeyword from AnswerRepository
        List<Object[]> answerResults = new ArrayList<>();
        answerResults.add(new Object[]{1, "content1", 1, new BigDecimal(100)});
        when(mockAnswerRepository.AnswerFindByKeyword(keyword)).thenReturn(answerResults);

        // Mocking return value of CommentFindByKeyword from CommentRepository
        List<Object[]> commentResults = new ArrayList<>();
        commentResults.add(new Object[]{1, "comment1", 1, new BigDecimal(100)});
        when(mockCommentRepository.CommentFindByKeyword(keyword)).thenReturn(commentResults);

        // Run the test
        final List<Map<String, Object>> result = searchServiceUnderTest.searchKeyword(keyword, searchType);

        // Verify the results
        assertThat(result).hasSize(4);

        Map<String, Object> expectedUser = new HashMap<>();
        expectedUser.put("name", "username1");
        expectedUser.put("type", "User");
        expectedUser.put("html_url", "/center/U3");

        Map<String, Object> expectedQuestion = new HashMap<>();
        expectedQuestion.put("name", "title1");
        expectedQuestion.put("type", "Question");
        expectedQuestion.put("html_url", "/question/1");

        Map<String, Object> expectedAnswer = new HashMap<>();
        expectedAnswer.put("id", 1);
        expectedAnswer.put("name", "content1");
        expectedAnswer.put("type", "Answer");
        expectedAnswer.put("html_url", "/question/1");

        Map<String, Object> expectedComment = new HashMap<>();
        expectedComment.put("id", 1);
        expectedComment.put("name", "comment1");
        expectedComment.put("type", "Comment");
        expectedComment.put("html_url", "/question/answer/1");

        assertThat(result).contains(expectedUser, expectedQuestion, expectedAnswer, expectedComment);
    }
    @Test
    void testSearchKeyword_UserType() {
        // Setup
        final String keyword = "test";
        final String searchType = "User";

        // Mocking return value of UserFindByKeyword from UserRepository
        List<Object[]> userResults = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            userResults.add(new Object[]{i, "username" + i, new BigDecimal(100)});
        }
        when(mockUserRepository.UserFindByKeyword(keyword)).thenReturn(userResults);

        // Run the test
        final List<Map<String, Object>> result = searchServiceUnderTest.searchKeyword(keyword, searchType);

        // Verify the results
        assertThat(result).hasSize(5);

        for (int i = 0; i < 5; i++) {
            Map<String, Object> expectedUser = new HashMap<>();
            expectedUser.put("name", "username" + i);
            expectedUser.put("type", "User");
            expectedUser.put("html_url", "/center/U" + (i * 3));
            assertThat(result).contains(expectedUser);
        }
    }
//    @Test
//    void testSearchKeyword_QuestionType() {
//        // Setup
//        final String keyword = "test";
//        final String searchType = "Question";
//
//        // Mocking return value of QuestionFindByKeyword from QuestionRepository
//        List<Object[]> questionResults = new ArrayList<>();
//        for (int i = 0; i < 10; i++) {
//            questionResults.add(new Object[]{i, "title" + i});
//        }
//        when(mockQuestionRepository.QuestionFindByKeyword(keyword)).thenReturn(questionResults);
//
//        // Run the test
//        final List<Map<String, Object>> result = searchServiceUnderTest.searchKeyword(keyword, searchType);
//
//        // Verify the results
//        assertThat(result).hasSize(5);
//
//        for (int i = 0; i < 5; i++) {
//            Map<String, Object> expectedQuestion = new HashMap<>();
//            expectedQuestion.put("name", "title" + i);
//            expectedQuestion.put("type", "Question");
//            expectedQuestion.put("html_url", "/question/" + i);
//            assertThat(result).contains(expectedQuestion);
//        }
//    }
//
//    @Test
//    void testSearchKeyword_AnswerType() {
//        // Setup
//        final String keyword = "test";
//        final String searchType = "Answer";
//
//        // Mocking return value of AnswerFindByKeyword from AnswerRepository
//        List<Object[]> answerResults = new ArrayList<>();
//        for (int i = 0; i < 10; i++) {
//            answerResults.add(new Object[]{i, "content" + i, i});
//        }
//        when(mockAnswerRepository.AnswerFindByKeyword(keyword)).thenReturn(answerResults);
//
//        // Run the test
//        final List<Map<String, Object>> result = searchServiceUnderTest.searchKeyword(keyword, searchType);
//
//        // Verify the results
//        assertThat(result).hasSize(5);
//
//        for (int i = 0; i < 5; i++) {
//            Map<String, Object> expectedAnswer = new HashMap<>();
//            expectedAnswer.put("id", i);
//            expectedAnswer.put("name", "content" + i);
//            expectedAnswer.put("type", "Answer");
//            expectedAnswer.put("html_url", "/question/" + i);
//            assertThat(result).contains(expectedAnswer);
//        }
//    }
//
//    @Test
//    void testSearchKeyword_CommentType() {
//        // Setup
//        final String keyword = "test";
//        final String searchType = "Comment";
//
//        // Mocking return value of CommentFindByKeyword from CommentRepository
//        List<Object[]> commentResults = new ArrayList<>();
//        for (int i = 0; i < 10; i++) {
//            commentResults.add(new Object[]{i, "content" + i, i});
//        }
//        when(mockCommentRepository.CommentFindByKeyword(keyword)).thenReturn(commentResults);
//
//        // Run the test
//        final List<Map<String, Object>> result = searchServiceUnderTest.searchKeyword(keyword, searchType);
//
//        // Verify the results
//        assertThat(result).hasSize(5);
//
//        for (int i = 0; i < 5; i++) {
//            Map<String, Object> expectedComment = new HashMap<>();
//            expectedComment.put("id", i);
//            expectedComment.put("name", "content" + i);
//            expectedComment.put("type", "Comment");
//            expectedComment.put("html_url", "/question/answer/" + i);
//            assertThat(result).contains(expectedComment);
//        }
    }
