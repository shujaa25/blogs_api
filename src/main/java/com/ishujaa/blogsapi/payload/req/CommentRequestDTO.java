package com.ishujaa.blogsapi.payload.req;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;

public record CommentRequestDTO(
        @NotBlank
        String name,
        @NotBlank
        String email,
        @NotBlank
        String body
) {}
