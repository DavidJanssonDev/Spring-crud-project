package com.example.Spring_crud_project.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/test")
public class TestController {

    @GetMapping("/public")
    public String publicEndpoint() {
        return "PUBLIC OK";
    }

    @GetMapping("/user")
    public String userEndpoint() {
        return "USER OK";
    }

    @GetMapping("/admin")
    public String adminEndpoint() {
        return "ADMIN OK";
    }
}