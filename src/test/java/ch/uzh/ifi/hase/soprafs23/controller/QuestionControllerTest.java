package ch.uzh.ifi.hase.soprafs23.controller;

import ch.uzh.ifi.hase.soprafs23.repository.UserRepository;
import ch.uzh.ifi.hase.soprafs23.service.QuestionService;
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
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import javax.servlet.http.HttpServletRequest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

@ExtendWith(SpringExtension.class)
@WebMvcTest(QuestionController.class)
class QuestionControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private QuestionService mockQuestionService;
    @MockBean
    private UserRepository mockUserRepository;
    @MockBean
    private SimpMessagingTemplate mockMessagingTemplate;

    @Test
    void testCreateQuestion() throws Exception {
        // Mock questionService.createQuestion() and questionService.getHowManyQuestions() responses
        when(mockQuestionService.createQuestion(any(HttpServletRequest.class))).thenReturn("new1");
        when(mockQuestionService.getHowManyQuestions()).thenReturn("{\"howmanypages\": 2}");

        // Mock questionService.getAllQuestions() response
        when(mockQuestionService.getAllQuestions(eq(2), isNull(), isNull(), any(HttpServletRequest.class)))
                .thenReturn("result");

        // Perform the request
        mockMvc.perform(MockMvcRequestBuilders.post("/question/createQuestion")
                        .accept(MediaType.APPLICATION_JSON)
                        .header("token", "U3"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string("new1"));

        // Verify messagingTemplate.convertAndSend()
//        verify(messagingTemplate).convertAndSend("/topic/listQuestions/2/", "result");
    }




    @Test
    void testGetAllQuestions() throws Exception {
        // Setup
        when(mockQuestionService.getAllQuestions(eq(1), eq(null), eq(null),any(HttpServletRequest.class))).thenReturn("result");

        // Run the test
        final MockHttpServletResponse response = mockMvc.perform(get("/question/getAllQuestions")
                        .param("pageIndex", "1")
                        .param("tag", "null")
                        .accept(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();

        // Verify the results
        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
        assertThat(response.getContentAsString()).isEqualTo("");
    }

    @Test
    void testGetQuestionById() throws Exception {
        // Setup
        when(mockQuestionService.getQuestionById(eq(1), any(HttpServletRequest.class))).thenReturn("result");

        // Run the test
        final MockHttpServletResponse response = mockMvc.perform(get("/question/getQuestionById")
                        .param("ID", "1")
                        .accept(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();

        // Verify the results
        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
        assertThat(response.getContentAsString()).isEqualTo("result");
    }

    @Test
    void testGetHowManyQuestions() throws Exception {
        // Setup
        when(mockQuestionService.getHowManyQuestions()).thenReturn("result");

        // Run the test
        final MockHttpServletResponse response = mockMvc.perform(get("/question/getHowManyQuestions")
                        .accept(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();

        // Verify the results
        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
        assertThat(response.getContentAsString()).isEqualTo("result");
    }

    @Test
    void testGetQuestionsByWho() throws Exception {
        // Setup
        when(mockQuestionService.getQuestionsByWho(0)).thenReturn("result");

        // Run the test
        final MockHttpServletResponse response = mockMvc.perform(get("/question/getQuestionsByWho")
                        .param("who_asks", "0")
                        .accept(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();

        // Verify the results
        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
        assertThat(response.getContentAsString()).isEqualTo("result");
    }

    @Test
    void testUpdateQuestion() throws Exception {
        // Setup
        when(mockQuestionService.updateQuestion(eq(0), eq("newTitle"), eq("newDescription"),
                any(HttpServletRequest.class))).thenReturn("result");

        // Run the test
        final MockHttpServletResponse response = mockMvc.perform(post("/question/updateQuestion")
                        .param("ID", "0")
                        .param("newTitle", "newTitle")
                        .param("newDescription", "newDescription")
                        .accept(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();

        // Verify the results
        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
        assertThat(response.getContentAsString()).isEqualTo("result");
    }

    @Test
    void testDeleteQuestion() throws Exception {
        // Setup
        when(mockQuestionService.deleteQuestion(eq(0), any(HttpServletRequest.class))).thenReturn("result");

        // Run the test
        final MockHttpServletResponse response = mockMvc.perform(delete("/question/deleteQuestion")
                        .param("ID", "0")
                        .accept(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();

        // Verify the results
        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
        assertThat(response.getContentAsString()).isEqualTo("result");
    }
}
