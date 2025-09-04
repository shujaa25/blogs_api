package com.ishujaa.blogsapi.payload.req;

import com.ishujaa.blogsapi.model.Category;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;

public record PostRequestDTO(
        @NotBlank
        String title,
        @NotBlank
        String description,
        @NotBlank
        String content,
        @NotNull
        @PositiveOrZero
        Long categoryId
) {}
