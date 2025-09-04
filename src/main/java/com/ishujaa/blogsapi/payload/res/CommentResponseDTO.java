package com.ishujaa.blogsapi.payload.res;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record CommentResponseDTO(
        Long id,
        String name,
        String email,
        String body
) {}
