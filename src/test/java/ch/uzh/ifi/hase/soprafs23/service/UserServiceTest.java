package ch.uzh.ifi.hase.soprafs23.service;

import ch.uzh.ifi.hase.soprafs23.entity.User;
import ch.uzh.ifi.hase.soprafs23.repository.UserRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;

import java.util.Calendar;
import java.util.Collections;
import java.util.GregorianCalendar;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock
    private UserRepository mockUserRepository;

    @InjectMocks
    private UserService userServiceUnderTest;

    @Test
    void testRegisterNewUser() {
        // Setup
        final MockHttpServletRequest request = new MockHttpServletRequest();
        final MockHttpServletResponse response = new MockHttpServletResponse();
        request.addParameter("username","username");
        // Configure UserRepository.findByUsername(...).
        final User user = new User();
        user.setId(0);
        user.setUsername("username");
        user.setPassword("password");
        user.setEmail("email");
        user.setRegister_time(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
        user.setHasNew(0);
        // when(mockUserRepository.findByUsername("username")).thenReturn(user);

        // Run the test
        final String result = userServiceUnderTest.registerNewUser(request, response);

        // Verify the results
        Assertions.assertNotNull(result);
    }

    @Test
    void testRegisterNewUser_UserRepositoryFindByUsernameReturnsNull() {
        // Setup
        final MockHttpServletRequest request = new MockHttpServletRequest();
        final MockHttpServletResponse response = new MockHttpServletResponse();
        //when(mockUserRepository.findByUsername("username")).thenReturn(null);
        //when(mockUserRepository.findIdByUsername("username")).thenReturn(0);

        // Run the test
        final String result = userServiceUnderTest.registerNewUser(request, response);

        // Verify the results
        Assertions.assertNotNull(result);
        //verify(mockUserRepository).save(any(User.class));
    }

    @Test
    void testLoginOneUser() {
        // Setup
        final MockHttpServletRequest request = new MockHttpServletRequest();
        final MockHttpServletResponse response = new MockHttpServletResponse();

        // Configure UserRepository.loginWithUsername(...).
        final User user = new User();
        user.setId(0);
        user.setUsername("username");
        user.setPassword("password");
        user.setEmail("email");
        user.setRegister_time(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
        user.setHasNew(0);
        final List<User> users = List.of(user);
        when(mockUserRepository.loginWithUsername("username", "password")).thenReturn(users);

        // Run the test
        final String result = userServiceUnderTest.loginOneUser("username", "password", request, response);

        // Verify the results
        Assertions.assertNotNull(result);
    }

    @Test
    void testLoginOneUser_UserRepositoryReturnsNoItems() {
        // Setup
        final MockHttpServletRequest request = new MockHttpServletRequest();
        final MockHttpServletResponse response = new MockHttpServletResponse();
        when(mockUserRepository.loginWithUsername("username", "password")).thenReturn(Collections.emptyList());

        // Run the test
        final String result = userServiceUnderTest.loginOneUser("username", "password", request, response);

        // Verify the results
        Assertions.assertNotNull(result);
    }
}
