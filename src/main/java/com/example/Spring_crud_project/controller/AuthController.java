package com.example.Spring_crud_project.controller;


import com.example.Spring_crud_project.dto.classes.AuthResponse;
import com.example.Spring_crud_project.dto.classes.LoginRequest;
import com.example.Spring_crud_project.dto.classes.RegisterRequest;
import com.example.Spring_crud_project.service.auth.AuthService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    // =========================
    // LOGIN
    // =========================
    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@RequestBody LoginRequest request) {
        return ResponseEntity.ok(authService.login(request));
    }

    // =========================
    // REGISTER (optional but recommended)
    // =========================
    @PostMapping("/register")
    public ResponseEntity<AuthResponse> register(@RequestBody RegisterRequest request) {
        return ResponseEntity.ok(authService.register(request));
    }

    // =========================
    // CREATE ADMIN (BACKDOOR - REMOVE IN PRODUCTION!)
    // =========================
    @PostMapping("/create-admin")
    public ResponseEntity<AuthResponse> createAdmin(@RequestBody RegisterRequest request) {
        return ResponseEntity.ok(authService.createAdmin(request));
    }
}
