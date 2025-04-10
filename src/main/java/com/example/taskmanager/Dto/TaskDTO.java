package com.example.taskmanager.Dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;

public record TaskDTO(
        @NotEmpty(message = "Title cannot be empty")
        String title,
        String description,
        @NotNull(message = "Priority cannot be null")
        Integer priority,
        LocalDateTime createdAt
) {}
