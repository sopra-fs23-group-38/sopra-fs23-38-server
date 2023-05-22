package ch.uzh.ifi.hase.soprafs23;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

//@ExtendWith(SpringExtension.class)
//@WebMvcTest(Application.class)
//class ApplicationTest {
//
//    @Autowired
//    private MockMvc mockMvc;
//
//    @Test
//    void testMain() {
//        // Setup
//        // Run the test
//        Application.main(new String[]{"args"});
//
//        // Verify the results
//    }
//
//    @Test
//    void testHelloWorld() throws Exception {
//        // Setup
//        // Run the test
//        final MockHttpServletResponse response = mockMvc.perform(get("/")
//                        .accept(MediaType.APPLICATION_JSON))
//                .andReturn().getResponse();
//
//        // Verify the results
//        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
//        assertThat(response.getContentAsString()).isEqualTo("expectedResponse");
//    }
//}
