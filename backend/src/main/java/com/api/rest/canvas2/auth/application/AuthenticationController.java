package com.api.rest.canvas2.auth.application;

import com.api.rest.canvas2.auth.domain.AuthenticationService;
import com.api.rest.canvas2.auth.dto.JwtAuthResponseDto;
import com.api.rest.canvas2.auth.dto.LoginDto;
import com.api.rest.canvas2.auth.dto.PasswordVerificationDto;
import com.api.rest.canvas2.auth.dto.SigninDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
public class AuthenticationController {
    private final AuthenticationService authenticationService;

    @PostMapping("/login")
    public ResponseEntity<JwtAuthResponseDto> login(@RequestBody LoginDto logInDTO) {
        return ResponseEntity.ok(authenticationService.login(logInDTO));
    }

    @PostMapping("/signin")
    public ResponseEntity<JwtAuthResponseDto> signin(@RequestBody SigninDto signInDTO) {
        return ResponseEntity.ok(authenticationService.signIn(signInDTO));
    }

    @PostMapping("/verify-password")
    public ResponseEntity<Boolean> verifyPassword(@RequestBody PasswordVerificationDto request) {
        boolean isValid = authenticationService.verifyPassword(request.getUserId(), request.getPassword());
        return ResponseEntity.ok(isValid);
    }
}