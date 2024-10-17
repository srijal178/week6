package com.example.school_management.service;

import com.example.school_management.model.User;
import com.example.school_management.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder; // Autowire PasswordEncoder

    public void saveUser(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword())); // Use the PasswordEncoder
        userRepository.save(user);
    }

    public User findByUsername(String username) {
        return userRepository.findByUsername(username);
    }
}
