package ch.uzh.ifi.hase.soprafs23.service;

import ch.uzh.ifi.hase.soprafs23.entity.Answer;
import ch.uzh.ifi.hase.soprafs23.entity.Comment;
import ch.uzh.ifi.hase.soprafs23.entity.Notification;
import ch.uzh.ifi.hase.soprafs23.entity.User;
import ch.uzh.ifi.hase.soprafs23.repository.*;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mock.web.MockHttpServletRequest;

import java.math.BigDecimal;
import java.util.*;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CommentServiceTest {

    @Mock
    private UserRepository mockUserRepository;
    @Mock
    private UserService mockUserService;
    @Mock
    private CommentRepository mockCommentRepository;
    @Mock
    private AnswerRepository mockAnswerRepository;
    @Mock
    private NotificationRepository mockNotificationRepository;

    @BeforeEach
    public void init() {
        MockitoAnnotations.initMocks(this);
    }

    @InjectMocks
    private CommentService commentServiceUnderTest;

    @Test
    void testCreateComment() {
        // Setup
        final MockHttpServletRequest request = new MockHttpServletRequest();

        // Configure UserRepository.findById(...).
        final User user1 = new User();
        user1.setId(0);
        user1.setUsername("username");
        user1.setPassword("password");
        user1.setEmail("email");
        user1.setHasNew(0);
        final Optional<User> user = Optional.of(user1);
        when(mockUserRepository.findById(Mockito.any())).thenReturn(user);

        // Configure AnswerRepository.findById(...).
        final Answer answer1 = new Answer();
        answer1.setId(0);
        answer1.setQuestion_id(0);
        answer1.setWho_answers(0);
        answer1.setContent("content");
        answer1.setComment_count(0);
        final Optional<Answer> answer = Optional.of(answer1);
        when(mockAnswerRepository.findById(Mockito.any())).thenReturn(answer);

        // Configure CommentRepository.findById(...).
        final Comment comment1 = new Comment();
        comment1.setId(0);
        comment1.setContent("content");
        comment1.setWho_comments(0);
        comment1.setAnswer_ID(0);
        comment1.setChange_time(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
        comment1.setParentCommentId(0);
        final Optional<Comment> comment = Optional.of(comment1);
        when(mockCommentRepository.findById(Mockito.any())).thenReturn(comment);

        // Run the test
        final String result = commentServiceUnderTest.createComment(0, "content", 0, request);
        final String result2 = commentServiceUnderTest.createComment(0, "content", null, request);

        // Verify the results
        Assertions.assertNotNull(result);
        Assertions.assertNotNull(result2);
        //verify(mockCommentRepository).save(any(Comment.class));
        //verify(mockNotificationRepository).save(any(Notification.class));
        //verify(mockUserRepository).save(any(User.class));
    }

    @Test
    void testCreateComment_UserRepositoryFindByIdReturnsAbsent() {
        // Setup
        final MockHttpServletRequest request = new MockHttpServletRequest();
        when(mockUserRepository.findById(Mockito.any())).thenReturn(Optional.empty());

        // Run the test
        final String result = commentServiceUnderTest.createComment(0, "content", 0, request);

        // Verify the results
        Assertions.assertNotNull(result);
    }

    @Test
    void testCreateComment_AnswerRepositoryReturnsAbsent() {
        // Setup
        final MockHttpServletRequest request = new MockHttpServletRequest();

        // Configure UserRepository.findById(...).
        final User user1 = new User();
        user1.setId(0);
        user1.setUsername("username");
        user1.setPassword("password");
        user1.setEmail("email");
        user1.setHasNew(0);
        final Optional<User> user = Optional.of(user1);
        when(mockUserRepository.findById(Mockito.any())).thenReturn(user);

        when(mockAnswerRepository.findById(Mockito.any())).thenReturn(Optional.empty());

        // Run the test
        final String result = commentServiceUnderTest.createComment(0, "content", 0, request);

        // Verify the results
        Assertions.assertNotNull(result);
    }

    @Test
    void testCreateComment_CommentRepositoryFindByIdReturnsAbsent() {
        // Setup
        final MockHttpServletRequest request = new MockHttpServletRequest();

        // Configure UserRepository.findById(...).
        final User user1 = new User();
        user1.setId(0);
        user1.setUsername("username");
        user1.setPassword("password");
        user1.setEmail("email");
        user1.setHasNew(0);
        final Optional<User> user = Optional.of(user1);
        when(mockUserRepository.findById(Mockito.any())).thenReturn(user);

        // Configure AnswerRepository.findById(...).
        final Answer answer1 = new Answer();
        answer1.setId(0);
        answer1.setQuestion_id(0);
        answer1.setWho_answers(0);
        answer1.setContent("content");
        answer1.setComment_count(0);
        final Optional<Answer> answer = Optional.of(answer1);
        when(mockAnswerRepository.findById(Mockito.any())).thenReturn(answer);

        //when(mockCommentRepository.findById(Mockito.any())).thenReturn(Optional.empty());

        // Run the test
        final String result = commentServiceUnderTest.createComment(0, "content", 0, request);

        // Verify the results
        Assertions.assertNotNull(result);
        verify(mockCommentRepository).save(any(Comment.class));
    }

    @Test
    void testGetAllComments() {
        // Setup
        // Configure CommentRepository.findByAnswerIdOrderByTimeAsc(...).
        final Comment comment = new Comment();
        comment.setId(0);
        comment.setContent("content");
        comment.setWho_comments(0);
        comment.setAnswer_ID(0);
        comment.setChange_time(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
        comment.setParentCommentId(null);
        final List<Comment> comments = List.of(comment);
        when(mockCommentRepository.findByAnswerIdOrderByTimeAsc(0)).thenReturn(comments);

        // Configure UserRepository.findById(...).
        final User user1 = new User();
        user1.setId(0);
        user1.setUsername("username");
        user1.setPassword("password");
        user1.setEmail("email");
        user1.setHasNew(0);
        final Optional<User> user = Optional.of(user1);
        //when(mockUserRepository.findById(Mockito.any())).thenReturn(user);

        // Configure CommentRepository.findByParentID(...).
        final Comment comment1 = new Comment();
        comment1.setId(1);
        comment1.setContent("content");
        comment1.setWho_comments(0);
        comment1.setAnswer_ID(0);
        comment1.setChange_time(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
        comment1.setParentCommentId(0);
        final List<Comment> comments1 = List.of(comment1);
        // when(mockCommentRepository.findByParentID(0)).thenReturn(comments);

        // Run the test
        final String result = commentServiceUnderTest.getAllComments(0);

        // Verify the results
        Assertions.assertNotNull(result);
    }

    @Test
    void testGetAllComments_CommentRepositoryFindByAnswerIdOrderByTimeAscReturnsNoItems() {
        // Setup
        when(mockCommentRepository.findByAnswerIdOrderByTimeAsc(0)).thenReturn(Collections.emptyList());

        // Run the test
        final String result = commentServiceUnderTest.getAllComments(0);

        // Verify the results
        assertTrue(result == null || result.isEmpty() || result.equals("[]"));
    }

    @Test
    void testGetAllComments_UserRepositoryReturnsAbsent() {
        // Setup
        // Configure CommentRepository.findByAnswerIdOrderByTimeAsc(...).
        final Comment comment = new Comment();
        comment.setId(0);
        comment.setContent("content");
        comment.setWho_comments(0);
        comment.setAnswer_ID(0);
        comment.setChange_time(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
        comment.setParentCommentId(0);
        final List<Comment> comments = List.of(comment);
        when(mockCommentRepository.findByAnswerIdOrderByTimeAsc(Mockito.any())).thenReturn(comments);

        lenient().when(mockUserRepository.findById(Mockito.any())).thenReturn(Optional.empty());

        // Configure CommentRepository.findByParentID(...).
        final Comment comment1 = new Comment();
        comment1.setId(0);
        comment1.setContent("content");
        comment1.setWho_comments(0);
        comment1.setAnswer_ID(0);
        comment1.setChange_time(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
        comment1.setParentCommentId(0);
        List<Comment> comments1 = List.of(comment1);
        //when(mockCommentRepository.findByParentID(Mockito.any())).thenReturn(comments1);

        // Run the test
        final String result = commentServiceUnderTest.getAllComments(0);

        // Verify the results
        assertTrue(result == null || result.isEmpty() || result.equals("[]"));
    }

    @Test
    void testGetAllComments_CommentRepositoryFindByParentIDReturnsNoItems() {
        // Setup
        // Configure CommentRepository.findByAnswerIdOrderByTimeAsc(...).
        final Comment comment = new Comment();
        comment.setId(0);
        comment.setContent("content");
        comment.setWho_comments(0);
        comment.setAnswer_ID(0);
        comment.setChange_time(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
        comment.setParentCommentId(0);
        final List<Comment> comments = List.of(comment);
        when(mockCommentRepository.findByAnswerIdOrderByTimeAsc(0)).thenReturn(comments);

        // Configure UserRepository.findById(...).
        final User user1 = new User();
        user1.setId(0);
        user1.setUsername("username");
        user1.setPassword("password");
        user1.setEmail("email");
        user1.setHasNew(0);
        final Optional<User> user = Optional.of(user1);
        when(mockUserRepository.findById(Mockito.any())).thenReturn(user);

        lenient().when(mockUserRepository.findById(Mockito.any())).thenReturn(Optional.empty());

        // Run the test
        final String result = commentServiceUnderTest.getAllComments(0);

        // Verify the results
        assertTrue(result == null || result.isEmpty() || result.equals("[]"));
    }
    @Test
    void testGetCommentsByWho() {
        // Setup
        // Configure CommentRepository.findByWhoCommentsOrderByTimeDesc(...).
        final Comment comment = new Comment();
        comment.setId(0);
        comment.setContent("content");
        comment.setWho_comments(0);
        comment.setAnswer_ID(0);
        comment.setChange_time(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
        comment.setParentCommentId(0);
        final List<Comment> comments = List.of(comment);
        when(mockCommentRepository.findByWhoCommentsOrderByTimeDesc(0)).thenReturn(comments);

        when(mockAnswerRepository.getAnswererUsernameByAnswerId(0)).thenReturn(List.of("value"));

        // Run the test
        final String result = commentServiceUnderTest.getCommentsByWho(0);

        // Verify the results
        Assertions.assertNotNull(result);
    }

    @Test
    void testGetCommentsByWho_CommentRepositoryReturnsNull() {
        // Setup
        when(mockCommentRepository.findByWhoCommentsOrderByTimeDesc(0)).thenReturn(null);

        // Run the test
        final String result = commentServiceUnderTest.getCommentsByWho(0);

        // Verify the results
        Assertions.assertNotNull(result);
    }

    @Test
    void testGetCommentsByWho_CommentRepositoryReturnsNoItems() {
        // Setup
        when(mockCommentRepository.findByWhoCommentsOrderByTimeDesc(0)).thenReturn(Collections.emptyList());

        // Run the test
        final String result = commentServiceUnderTest.getCommentsByWho(0);

        // Verify the results
        Assertions.assertNotNull(result);
    }

    @Test
    void testGetCommentsByWho_AnswerRepositoryReturnsNull() {
        // Setup
        // Configure CommentRepository.findByWhoCommentsOrderByTimeDesc(...).
        final Comment comment = new Comment();
        comment.setId(0);
        comment.setContent("content");
        comment.setWho_comments(0);
        comment.setAnswer_ID(0);
        comment.setChange_time(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
        comment.setParentCommentId(0);
        final List<Comment> comments = List.of(comment);
        when(mockCommentRepository.findByWhoCommentsOrderByTimeDesc(0)).thenReturn(comments);

        when(mockAnswerRepository.getAnswererUsernameByAnswerId(0)).thenReturn(null);

        // Run the test
        final String result = commentServiceUnderTest.getCommentsByWho(0);

        // Verify the results
        Assertions.assertNotNull(result);
    }

    @Test
    void testGetCommentsByWho_AnswerRepositoryReturnsNoItems() {
        // Setup
        // Configure CommentRepository.findByWhoCommentsOrderByTimeDesc(...).
        final Comment comment = new Comment();
        comment.setId(0);
        comment.setContent("content");
        comment.setWho_comments(0);
        comment.setAnswer_ID(0);
        comment.setChange_time(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
        comment.setParentCommentId(0);
        final List<Comment> comments = List.of(comment);
        when(mockCommentRepository.findByWhoCommentsOrderByTimeDesc(0)).thenReturn(comments);

        when(mockAnswerRepository.getAnswererUsernameByAnswerId(0)).thenReturn(Collections.emptyList());

        // Run the test
        final String result = commentServiceUnderTest.getCommentsByWho(0);

        // Verify the results
        Assertions.assertNotNull(result);
    }

    @Test
    void testAddRepliesToList() {
        // Given
        Comment comment = new Comment();
        comment.setId(1);
        comment.setWho_comments(2);
        comment.setChange_time(new Date());
        comment.setContent("Test Comment");

        Comment reply = new Comment();
        reply.setId(3);
        reply.setWho_comments(4);
        reply.setChange_time(new Date());
        reply.setContent("Reply Comment");

        User user = new User();
        user.setUsername("Test User");
        user.setAvatarUrl("testAvatarUrl");

        when(mockCommentRepository.findByParentID(1)).thenReturn(Arrays.asList(reply));
        when(mockUserRepository.findById(4)).thenReturn(Optional.of(user));

        List<Map<String, Object>> repliesList = new ArrayList<>();

        // When
        commentServiceUnderTest.addRepliesToList(repliesList, comment);

        // Then
        assertEquals(1, repliesList.size());
        Map<String, Object> replyMap = repliesList.get(0);
        assertEquals(user.getUsername(), replyMap.get("author"));
        assertEquals(user.getAvatarUrl(), replyMap.get("author_avatar"));
        assertEquals(reply.getId(), replyMap.get("id"));
        assertEquals(reply.getChange_time(), replyMap.get("time"));
        assertEquals(reply.getContent(), replyMap.get("content"));
        assertEquals(0, ((List)replyMap.get("replies")).size()); // Checking for no replies of reply
    }

//    @Test
//    void addRepliesToList() {
//        // Setup
//        final Comment comment1 = new Comment();
//        comment1.setId(1);
//        comment1.setContent("content");
//        comment1.setWho_comments(0);
//        comment1.setAnswer_ID(0);
//        comment1.setChange_time(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
//        comment1.setParentCommentId(0);
//        final List<Comment> comments1 = List.of(comment1);
//        when(mockCommentRepository.findByParentID(Mockito.any())).thenReturn(comments1);
//        final User user1 = new User();
//        user1.setId(0);
//        user1.setUsername("username");
//        user1.setPassword("password");
//        user1.setEmail("email");
//        user1.setHasNew(0);
//        final Optional<User> user = Optional.of(user1);
//        when(mockUserRepository.findById(Mockito.any())).thenReturn(user);
//        List<Map<String, Object>> repliesList  = new ArrayList<>();
//        // Run the test
//        commentServiceUnderTest.addRepliesToList(repliesList,comment1);
//
//        // Verify the results
//        //Assertions.assertNotNull(result);
//    }




    @InjectMocks
    SearchService searchService;
    @Mock
    private QuestionRepository questionRepositoryTest;

    @Test
    void searchKeyword() {
        List<Object[]> questionResult = new ArrayList<>();
        Object[] result = new Object[4];
        result[0] = 1;
        result[1] = "title";
        result[2] = "description";
        result[3] = new BigDecimal(100);
        questionResult.add(result);
        when(questionRepositoryTest.QuestionFindByKeyword(Mockito.any())).thenReturn(questionResult);

        List<Object[]> questionResult1 = new ArrayList<>();
        Object[] result1 = new Object[4];
        result1[0] = 1;
        result1[1] = "1";
        result1[2] = 1;
        result1[3] = new BigDecimal(100);
        questionResult1.add(result1);
        when(mockAnswerRepository.AnswerFindByKeyword(Mockito.any())).thenReturn(questionResult1);

        List<Object[]> questionResult2 = new ArrayList<>();
        Object[] result2 = new Object[4];
        result2[0] = 1;
        result2[1] = "1";
        result2[2] = new BigDecimal(100);
        questionResult2.add(result2);
        when(mockUserRepository.UserFindByKeyword(Mockito.any())).thenReturn(questionResult2);
        final String searchType = "All";
        List<Map<String, Object>> result33 = searchService.searchKeyword("keyword",searchType);
    }

}
