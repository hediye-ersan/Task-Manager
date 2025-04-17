package com.example.taskmanager.Dto;

import com.example.taskmanager.Entity.Priority;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;

public record TaskDTO(
        Long id,
        @NotEmpty(message = "Title cannot be empty")
        String title,
        @NotEmpty(message = "Description cannot be empty")
        String description,
        @NotNull(message = "Priority cannot be null")
        Priority priority,
        LocalDateTime createdAt,
        String boardColumnName
) {}
