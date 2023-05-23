package ch.uzh.ifi.hase.soprafs23.controller;

import ch.uzh.ifi.hase.soprafs23.Auxiliary.auxiliary;
import ch.uzh.ifi.hase.soprafs23.entity.User;
import ch.uzh.ifi.hase.soprafs23.repository.UserRepository;
import ch.uzh.ifi.hase.soprafs23.service.UserService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.*;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.net.URI;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

//@SpringBootTest
@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;


    @MockBean
    private UserService mockUserService;

    @Autowired
    private UserRepository userRepository;

    @Test
    void testRegisterNewUser() throws Exception {
        // Setup
        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpServletResponse response = mock(HttpServletResponse.class);

        // Set the parameter values
        when(request.getParameter("username")).thenReturn("testuser");
        when(request.getParameter("password")).thenReturn("testpassword");
        when(request.getParameter("email")).thenReturn("test@example.com");

        // Configure UserService.registerNewUser(...)
        when(mockUserService.registerNewUser(request, response)).thenReturn("expectedResponse");

        // Run the test
        final MockHttpServletResponse servletResponse = mockMvc.perform(post("/register")
                        .accept(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();

        // Verify the results
        assertThat(servletResponse.getStatus()).isEqualTo(HttpStatus.OK.value());
        assertThat(servletResponse.getContentAsString()).isEqualTo("");
//        verify(mockUserService).registerNewUser(request, response);
    }
    @Test
    void contextLoadsHasNew() {


        Map<String, Object> map = new HashMap<>();
        map.put("username", "tom");
        map.put("password", "123456");
        RequestEntity<Map> requestEntity = RequestEntity.post(URI.create("http://localhost:8080/has_new")).
                header(HttpHeaders.COOKIE, "name=token").
                header("token", "U3").
                accept(MediaType.APPLICATION_JSON).
                contentType(MediaType.APPLICATION_JSON).
                body(new HashMap());
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<String> res = restTemplate.exchange(requestEntity, String.class);
        System.out.println("返回结果：" + res.getBody());
    }


    @Test
    void testLoginOneUser() throws Exception {
        // Setup
        when(mockUserService.loginOneUser(eq("username"), eq("password"), any(HttpServletRequest.class),
                any(HttpServletResponse.class))).thenReturn("result");

        // Run the test
        final MockHttpServletResponse response = mockMvc.perform(post("/login")
                        .param("username", "username")
                        .param("password", "password")
                        .accept(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();

        // Verify the results
        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
        assertThat(response.getContentAsString()).isEqualTo("result");
    }

    @Test
    void testGetHasNew() throws Exception {
        // Mock request and user
        HttpServletRequest request = mock(HttpServletRequest.class);

        when(request.getHeader(eq("token"))).thenReturn("U3");
        doReturn("U3").when(request).getHeader(eq("token"));
//        Mockito.doReturn("U3").when(request).getHeader(eq("token"));
        Integer userId = 1;
        //when(auxiliary.extractUserID(request)).thenReturn(userId);

        User user = new User();
        user.setId(userId);
        user.setHasNew(1);
        Optional<User> optionalUser = Optional.of(user);
        //when(userRepository.findById(1)).thenReturn(Optional.of(user));

        // Mock cookies
        Cookie[] cookies = new Cookie[0];
        when(request.getCookies()).thenReturn(cookies);

        // Perform the GET request
        mockMvc.perform(get("/has_new").requestAttr("userId", userId).header("token", "U3"))
                .andExpect(status().isOk());
        //.andExpect(content().json("{\"has_new\": 2}"));
    }

    @Test
    void testCleanHasNew() throws Exception {
        // Setup
        Integer userId = 1;
        User user = new User();
        user.setId(userId);
        user.setHasNew(1);
        Optional<User> userOptional = Optional.of(user);
        //when(userRepository.findById(Mockito.any())).thenReturn(userOptional);

        // Run the test
        final MockHttpServletResponse response = mockMvc.perform(post("/has_new")
                        .accept(MediaType.APPLICATION_JSON)
                        .header("token", "U3"))
                .andReturn().getResponse();

        // Verify the results
        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
        assertThat(response.getContentAsString()).isEqualTo("{\"success\":true}");

        // Verify that user's hasNew is set to 0 and saved
        user.setHasNew(0);
        //verify(userRepository).save(user);
    }
}
