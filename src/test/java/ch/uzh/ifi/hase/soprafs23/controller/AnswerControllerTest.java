package ch.uzh.ifi.hase.soprafs23.controller;

import ch.uzh.ifi.hase.soprafs23.Auxiliary.auxiliary;
import ch.uzh.ifi.hase.soprafs23.entity.Answer;
import ch.uzh.ifi.hase.soprafs23.entity.Question;
import ch.uzh.ifi.hase.soprafs23.repository.AnswerRepository;
import ch.uzh.ifi.hase.soprafs23.service.AnswerService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.AutoConfigureTestEntityManager;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import ch.uzh.ifi.hase.soprafs23.service.AnswerService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Propagation;
//import org.springframework.transaction.annotation.Transactional;

// 导入其他必需的类和注解

//@DataJpaTest
//@WebMvcTest/
//@Transactional
//@SpringBootTest
//@Transactional
@ExtendWith(SpringExtension.class)
@WebMvcTest(AnswerController.class)
class AnswerControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AnswerService mockAnswerService;

    @Test
    void testCreateAnswer() throws Exception {
        // Setup
        when(mockAnswerService.createAnswer(eq(1), eq("content"), any(HttpServletRequest.class))).thenReturn("result");

        // Create a MockHttpServletRequest with parameters
        MockHttpServletRequestBuilder requestBuilder = post("/answer/createAnswer")
                .param("questionID", "1")
                .param("content", "content")
                .accept(MediaType.APPLICATION_JSON)
                .requestAttr(RequestDispatcher.FORWARD_REQUEST_URI, "/answer/createAnswer");

        requestBuilder.header("token", "U3");
        // Run the test
        final MockHttpServletResponse response = mockMvc.perform(requestBuilder)
                .andReturn().getResponse();

        // Verify the results
        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
        assertThat(response.getContentAsString()).isEqualTo("result");
    }


    @Test
    void testGetAllAnsToOneQuestion() throws Exception {
        // Setup
        when(mockAnswerService.getAllAnsToOneQuestion(eq(1), eq(1), any(HttpServletRequest.class)))
                .thenReturn("result");

        // Run the test
        final MockHttpServletResponse response = mockMvc.perform(get("/answer/getAllAnstoOneQ")
                        .param("questionID", "1")
                        .param("pageIndex", "1")
                        .accept(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();

        // Verify the results
        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
        assertThat(response.getContentAsString()).isEqualTo("result");
    }

    @Test
    void testGetAnswerById() throws Exception {
        // Setup
        when(mockAnswerService.getAnswerById(eq(1), any(HttpServletRequest.class))).thenReturn("result");

        // Run the test
        final MockHttpServletResponse response = mockMvc.perform(get("/answer/getAnswerById")
                        .param("ID", "1")
                        .accept(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();

        // Verify the results
        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
        assertThat(response.getContentAsString()).isEqualTo("result");
    }

    @Test
    void testGetAnswersByWho() throws Exception {
        // Setup
        when(mockAnswerService.getAnswersByWho(1)).thenReturn("result");

        // Run the test
        final MockHttpServletResponse response = mockMvc.perform(get("/answer/getAnswersByWho")
                        .param("answererID", "1")
                        .accept(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();

        // Verify the results
        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
        assertThat(response.getContentAsString()).isEqualTo("result");
    }

    @Test
    void testGetTotalPageCount() throws Exception {
        // Setup
        when(mockAnswerService.getHowManyPages(0)).thenReturn("result");

        // Run the test
        final MockHttpServletResponse response = mockMvc.perform(get("/answer/getHowManyPages")
                        .param("which_question", "0")
                        .accept(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();

        // Verify the results
        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
        assertThat(response.getContentAsString()).isEqualTo("result");
    }

    @Test
    void testUporDownVote() throws Exception {
        // Setup
        when(mockAnswerService.UporDownVote(1, 1)).thenReturn("result");

        // Run the test
        final MockHttpServletResponse response = mockMvc.perform(post("/answer/vote")
                        .param("ID", "1")
                        .param("UporDownVote", "1")
                        .accept(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();

        // Verify the results
        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
        assertThat(response.getContentAsString()).isEqualTo("result");
    }

    @Test
    void testUpdateAnswer() throws Exception {
        // Setup
        when(mockAnswerService.updateAnswer(eq(0), eq("newContent"), any(HttpServletRequest.class)))
                .thenReturn("result");

        // Run the test
        final MockHttpServletResponse response = mockMvc.perform(post("/answer/updateAnswer")
                        .param("ID", "0")
                        .param("newContent", "newContent")
                        .accept(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();

        // Verify the results
        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
        assertThat(response.getContentAsString()).isEqualTo("result");
    }

    @Test
    void testDeleteAnswer() throws Exception {
        // Setup
        when(mockAnswerService.deleteAnswer(eq(0), any(HttpServletRequest.class))).thenReturn("result");

        // Run the test
        final MockHttpServletResponse response = mockMvc.perform(delete("/answer/deleteAnswer")
                        .param("ID", "0")
                        .accept(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();

        // Verify the results
        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
        assertThat(response.getContentAsString()).isEqualTo("result");
    }
}
