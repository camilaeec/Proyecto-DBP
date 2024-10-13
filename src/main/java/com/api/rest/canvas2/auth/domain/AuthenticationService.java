package com.api.rest.canvas2.auth.domain;

import com.api.rest.canvas2.Events.SignIn.SignInEvent;
import com.api.rest.canvas2.Users.domain.Role;
import com.api.rest.canvas2.Users.domain.User;
import com.api.rest.canvas2.Users.infrastructure.UserRepository;
import com.api.rest.canvas2.auth.dto.JwtAuthResponseDto;
import com.api.rest.canvas2.auth.dto.LoginDto;
import com.api.rest.canvas2.auth.dto.SigninDto;
import com.api.rest.canvas2.configuration.JwtService;
import com.api.rest.canvas2.exceptions.UserAlreadyExistsException;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthenticationService {
    private final UserRepository userRepository;

    private final JwtService jwtService;

    private final PasswordEncoder passwordEncoder;

    private final ApplicationEventPublisher applicationEventPublisher;

    public JwtAuthResponseDto login(LoginDto logInDto) {
        User user = userRepository.findByEmail(logInDto.getEmail())
                .orElseThrow(() -> new UsernameNotFoundException("Email not found"));

        if (!passwordEncoder.matches(logInDto.getPassword(), user.getPassword())) {
            throw new IllegalArgumentException("Invalid password");
        }

        JwtAuthResponseDto response = new JwtAuthResponseDto();
        response.setToken(jwtService.generateToken(user));
        return response;
    }

    public JwtAuthResponseDto signIn(SigninDto signinDto) {
        if (userRepository.findByEmail(signinDto.getEmail()).isPresent()) {
            throw new UserAlreadyExistsException("Email already exists");
        }

        applicationEventPublisher.publishEvent(
                new SignInEvent(this, signinDto.getEmail(), signinDto.getName(), signinDto.getLastname())
        );

        User user = new User();
        user.setEmail(signinDto.getEmail());
        user.setPassword(passwordEncoder.encode(signinDto.getPassword()));
        user.setName(signinDto.getName());
        user.setLastname(signinDto.getLastname());

        try {
            user.setRole(Role.valueOf(signinDto.getRole().toUpperCase()));
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Invalid role provided. Valid roles are ADMIN, TEACHER, ASSISTANT, STUDENT.");
        }

        userRepository.save(user);

        JwtAuthResponseDto response = new JwtAuthResponseDto();
        response.setToken(jwtService.generateToken(user));
        return response;
    }

    public boolean verifyPassword(Long userId, String password) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
        return passwordEncoder.matches(password, user.getPassword());
    }
}