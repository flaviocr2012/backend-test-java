package org.example.controllers;

import org.example.constants.Constants;
import org.example.exceptions.company.UsernameAlreadyTakenException;
import org.example.models.User;
import org.example.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@RequestBody User user) {
        if (userRepository.findByUsername(user.getUsername()).isPresent()) {
            throw new UsernameAlreadyTakenException(Constants.USERNAME_TAKEN);
        }

        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setRole(Constants.DEFAULT_USER_ROLE);
        userRepository.save(user);

        return ResponseEntity.ok(Constants.USER_REGISTERED_SUCCESSFULLY);
    }

}


