package com.example.Spring_crud_project.service.auth;

import com.example.Spring_crud_project.dto.classes.AuthResponse;
import com.example.Spring_crud_project.dto.classes.LoginRequest;
import com.example.Spring_crud_project.dto.classes.RegisterRequest;

public interface AuthService {

    AuthResponse login(LoginRequest request);

    AuthResponse register(RegisterRequest request);
}
