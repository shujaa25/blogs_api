package com.ishujaa.blogsapi.payload.res;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;
import java.time.LocalDateTime;

public record CommentResponseDTO(
        Long id,
        String body,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
) {}
