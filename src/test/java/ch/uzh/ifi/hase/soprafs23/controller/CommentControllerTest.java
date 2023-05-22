package ch.uzh.ifi.hase.soprafs23.controller;

import ch.uzh.ifi.hase.soprafs23.entity.Comment;
import ch.uzh.ifi.hase.soprafs23.repository.CommentRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
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

import java.util.Optional;

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
    private SimpMessagingTemplate mockMessagingTemplate;

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

//    @Test
//    void testGetAllComments() throws Exception {
//        // Setup
//        // Run the test
//        final MockHttpServletResponse response = mockMvc.perform(get("/comment/getAllComments")
//                        .param("ID", "1")
//                        .accept(MediaType.APPLICATION_JSON))
//                .andReturn().getResponse();
//
//        // Verify the results
//        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
//        assertThat(response.getContentAsString()).isEqualTo("[]");
//    }

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
        // Run the test
        final MockHttpServletResponse response = mockMvc.perform(post("/comment/update")
                        .param("commentId", "0")
                        .param("content", "content")
                        .accept(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();

        // Verify the results
        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
        assertThat(response.getContentAsString()).isEqualTo("{\"success\":\"false\"}");
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
