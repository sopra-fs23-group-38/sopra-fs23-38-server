package ch.uzh.ifi.hase.soprafs23.entity;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.assertEquals;

class UserTest {

    private User userUnderTest;

    @BeforeEach
    void setUp() {
        userUnderTest = new User();
    }
    @Test
    public void testSetAndGetId() {
        User user = new User();
        user.setId(1);
        assertEquals(1, user.getId());
    }

    @Test
    public void testSetAndGetUsername() {
        User user = new User();
        user.setUsername("username");
        assertEquals("username", user.getUsername());
    }

    @Test
    public void testSetAndGetPassword() {
        User user = new User();
        user.setPassword("password");
        assertEquals("password", user.getPassword());
    }

    @Test
    public void testSetAndGetEmail() {
        User user = new User();
        user.setEmail("email");
        assertEquals("email", user.getEmail());
    }

    @Test
    public void testSetAndGetRegisterTime() {
        Date date = new Date();
        User user = new User();
        user.setRegister_time(date);
        assertEquals(date, user.getRegister_time());
    }

    @Test
    public void testSetAndGetHasNew() {
        User user = new User();
        user.setHasNew(1);
        assertEquals(1, user.getHasNew());
    }

    @Test
    public void testSetAndGetAvatarUrl() {
        User user = new User();
        user.setAvatarUrl("url");
        assertEquals("url", user.getAvatarUrl());
    }
}
