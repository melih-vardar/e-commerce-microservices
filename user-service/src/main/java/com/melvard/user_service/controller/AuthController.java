package com.melvard.user_service.controller;

import com.melvard.user_service.service.UserService;
import io.github.melihvardar.dtos.user.JwtResponseDTO;
import io.github.melihvardar.dtos.user.UserLoginDTO;
import io.github.melihvardar.dtos.user.UserRegisterDTO;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
@Slf4j
public class AuthController {

    private final UserService userService;

    @PostMapping("/register")
    public ResponseEntity<JwtResponseDTO> register(@Valid @RequestBody UserRegisterDTO registerRequest) {
        log.info("Register request received");
        JwtResponseDTO response = userService.register(registerRequest);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/login")
    public ResponseEntity<JwtResponseDTO> login(@Valid @RequestBody UserLoginDTO loginRequest) {
        log.info("Login request received");
        JwtResponseDTO response = userService.login(loginRequest);
        return ResponseEntity.ok(response);
    }

}
