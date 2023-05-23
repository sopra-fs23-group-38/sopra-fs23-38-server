package ch.uzh.ifi.hase.soprafs23.controller;

import ch.uzh.ifi.hase.soprafs23.entity.Answer;
import ch.uzh.ifi.hase.soprafs23.entity.Comment;
import ch.uzh.ifi.hase.soprafs23.entity.User;
import ch.uzh.ifi.hase.soprafs23.repository.AnswerRepository;
import ch.uzh.ifi.hase.soprafs23.repository.CommentRepository;
import ch.uzh.ifi.hase.soprafs23.repository.UserRepository;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.messaging.MessagingException;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.util.*;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
//@Import(CommentController.class)
@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
class CommentControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private CommentRepository commentRepository;
    @MockBean
    private UserRepository userRepository;
    @MockBean
    private SimpMessagingTemplate mockMessagingTemplate;
    @MockBean
    private AnswerRepository answerRepository;

    @Test
    void testCreateComment() throws Exception {
        // Setup
        // Run the test
        final MockHttpServletResponse response = mockMvc.perform(post("/comment/createComment")
                        .param("ID", "1")
                        .param("content", "content")
                        .param("parentId", "0")
                        .header("token", "U3")
                        .accept(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();

        // Verify the results
        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
        assertThat(response.getContentAsString()).isEqualTo("{}");
    }

    @Test
    void testGetAllComments() throws Exception {
        // Setup
        Comment comment = new Comment();
        comment.setId(1);
        comment.setContent("Test Comment Content");
        comment.setWho_comments(1);
        comment.setAnswer_ID(1);
        comment.setChange_time(new Date());
        comment.setParentCommentId(null);

        List<Comment> commentList = new ArrayList<>();
        commentList.add(comment);

        when(commentRepository.findByAnswerIdOrderByTimeAsc(anyInt())).thenReturn(commentList);

        // Assuming that there is a user with id 1
        User user = new User();
        user.setUsername("username");
        user.setAvatarUrl("avatarUrl");
        when(userRepository.findById(anyInt())).thenReturn(Optional.of(user));

        // Run the test
        final MockHttpServletResponse response = mockMvc.perform(get("/comment/getAllComments")
                        .param("ID", "1")
                        .accept(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();

        // Verify the results
        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());

        // Parse the response string into a list of maps for easy verification
        List<Map<String, Object>> responseList = new ObjectMapper().readValue(response.getContentAsString(), new TypeReference<List<Map<String, Object>>>() {});

        // Check that the list is not empty
        assertThat(responseList).isNotEmpty();

        // Check that the first item in the list contains the correct data
        Map<String, Object> firstItem = responseList.get(0);
        assertThat(firstItem.get("content")).isEqualTo("Test Comment Content");
        assertThat(firstItem.get("author")).isEqualTo("username");
        assertThat(firstItem.get("author_avatar")).isEqualTo("avatarUrl");
    }

    @Test
    void testGetCommentsByWho() throws Exception {
        // Setup
        // Run the test
        final MockHttpServletResponse response = mockMvc.perform(get("/comment/getCommentsByWho")
                        .param("who_comments", "2")
                        .accept(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();

        // Verify the results
        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
        assertThat(response.getContentAsString()).isEqualTo("[]");
    }

    @Test
    void testUpdateComment() throws Exception {
        // Setup
        final Comment comment1 = new Comment();
        comment1.setId(0);
        comment1.setContent("content");
        comment1.setWho_comments(0);
        comment1.setAnswer_ID(0);
        comment1.setChange_time(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
        comment1.setParentCommentId(0);
        final Optional<Comment> comment = Optional.of(comment1);
        when(commentRepository.findById(Mockito.any())).thenReturn(comment);

        // Run the test
        final MockHttpServletResponse response = mockMvc.perform(post("/comment/update")
                        .param("commentId", "0")
                        .param("content", "content")
                        .accept(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();

        // Verify the results
        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
        assertThat(response.getContentAsString()).isEqualTo("{\"success\":\"true\"}");
    }

    @Test
    void testDeleteById() throws Exception {
        // Setup
        // Run the test
        Integer commentId = 2; // 假设这是存在的Comment实体对象的id

        // Mock the comment repository to return a comment with the given id
        Comment comment = new Comment();
        comment.setId(commentId);
        comment.setAnswer_ID(commentId);
        when(commentRepository.findById(commentId)).thenReturn(Optional.of(comment));


        final Answer answer1 = new Answer();
        answer1.setId(0);
        answer1.setQuestion_id(0);
        answer1.setWho_answers(0);
        answer1.setContent("content");
        answer1.setComment_count(0);
        final Optional<Answer> answer = Optional.of(answer1);
        when(answerRepository.findById(Mockito.any())).thenReturn(answer);

        final MockHttpServletResponse response = mockMvc.perform(delete("/comment/delete")
                        .param("commentId", "2")
                        .accept(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();

        // Verify the results
        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
        assertThat(response.getContentAsString()).isEqualTo("{\"success\":\"true\"}");
        // Verify the results
        verify(commentRepository, times(1)).deleteById(commentId);

//        verify(mockMessagingTemplate).convertAndSend("destination", "payload");
    }

}
