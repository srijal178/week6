package com.example.school_management.service;

import com.example.school_management.model.User;
import com.example.school_management.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.mockito.Mockito.when;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.mockito.ArgumentMatchers.any;

@SpringBootTest
public class UserServiceTest {

    @Autowired
    private UserService userService;

    @MockBean
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;


    @Test
    public void testFindByUsername() {
        User user = new User();
        user.setUsername("testuser");
        user.setPassword("password123");

        when(userRepository.findByUsername("testuser")).thenReturn(user);

        User foundUser = userService.findByUsername("testuser");
        assertEquals(foundUser.getUsername(), "testuser");
    }

    @Test
    public void testPasswordEncoding() {
        // Create a new user and set a plain-text password
        User user = new User();
        user.setUsername("secureuser");
        user.setPassword("mysecretpassword");

        when(userRepository.save(any(User.class))).thenReturn(user);

        // Save the user, which should trigger password encoding
        userService.saveUser(user);

        // Verify that the password is not the same as the plain-text version
        assertNotEquals("mysecretpassword", user.getPassword(), 
            "The encoded password should not match the plain-text password.");

        // Verify that the password matches when using the encoder's match function
        assertEquals(passwordEncoder.matches("mysecretpassword", user.getPassword()), true,
            "The password should match when encoded with the password encoder.");
    }
}
