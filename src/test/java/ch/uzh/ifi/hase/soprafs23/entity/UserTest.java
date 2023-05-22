package ch.uzh.ifi.hase.soprafs23.entity;

import org.junit.jupiter.api.BeforeEach;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Date;

public class UserTest {
    @Test
    public void testUser() {
        User user = new User();

        // Set the properties
        Integer id = 1;
        String username = "testuser";
        String password = "password";
        String email = "testuser@example.com";
        Date register_time = new Date();
        Integer hasNew = 1;

        user.setId(id);
        user.setUsername(username);
        user.setPassword(password);
        user.setEmail(email);
        user.setRegister_time(register_time);
        user.setHasNew(hasNew);

        // Now use the getters to verify that the setters worked
        assertEquals(id, user.getId());
        assertEquals(username, user.getUsername());
        assertEquals(password, user.getPassword());
        assertEquals(email, user.getEmail());
        assertEquals(register_time, user.getRegister_time());
        assertEquals(hasNew, user.getHasNew());
    }
}
