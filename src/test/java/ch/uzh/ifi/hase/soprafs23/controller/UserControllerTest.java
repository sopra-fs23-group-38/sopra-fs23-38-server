package ch.uzh.ifi.hase.soprafs23.controller;

import ch.uzh.ifi.hase.soprafs23.Auxiliary.auxiliary;
import ch.uzh.ifi.hase.soprafs23.entity.User;
import ch.uzh.ifi.hase.soprafs23.repository.UserRepository;
import ch.uzh.ifi.hase.soprafs23.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
@WebMvcTest(UserController.class)
class UserControllerTest {
    @BeforeEach
    void setUp() {
        mockRequest = new MockHttpServletRequest();
    }
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private MockHttpServletRequest mockRequest;

    @MockBean
    private UserService mockUserService;

    @MockBean
    private UserRepository userRepository;
    @MockBean
    private User mockUser;
    @MockBean
    private auxiliary auxiliary;
    @Autowired
    private UserController userController;

    @Test
    void testRegisterNewUser() throws Exception {
        // Mock userService.registerNewUser() response
        when(mockUserService.registerNewUser(any(HttpServletRequest.class), any(HttpServletResponse.class))).thenReturn("user1");

        // Perform the request
        mockMvc.perform(MockMvcRequestBuilders.post("/register")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string("user1"));
    }

    @Test
    void testLoginOneUser() throws Exception {
        // Mock userService.loginOneUser() response
        when(mockUserService.loginOneUser(eq("username"), eq("password"), any(HttpServletRequest.class), any(HttpServletResponse.class))).thenReturn("user1");

        // Perform the request
        mockMvc.perform(MockMvcRequestBuilders.post("/login")
                        .param("username", "username")
                        .param("password", "password")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string("user1"));
    }

//    @Test
//    void testCleanHasNew() {
//        // Setup
//        when(auxiliary.extractUserID(mockRequest)).thenReturn(1);
//        when(userRepository.findById(1)).thenReturn(Optional.of(mockUser));
//        doNothing().when(userRepository).save(mockUser);
//        when(auxiliary.mapObjectToJson(any())).thenReturn("{\"success\": true}");
//
//        // Exercise
//        String result = userController.cleanHasNew(mockRequest);
//
//        // Verify
//        assertEquals("{\"success\": true}", result);
//        verify(userRepository).findById(1);
//        verify(userRepository).save(mockUser);
//        verify(auxiliary).mapObjectToJson(any());
//    }
}