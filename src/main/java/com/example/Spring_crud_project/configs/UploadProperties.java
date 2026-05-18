package com.example.Spring_crud_project.configs;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@ConfigurationProperties(prefix = "app.upload")
public record UploadProperties(
        int maxFiles,
        String dir
) {}