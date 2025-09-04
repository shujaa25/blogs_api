package com.ishujaa.blogsapi.payload.req;

import jakarta.validation.constraints.NotBlank;

public record CategoryRequestDTO(
        @NotBlank
        String name,
        @NotBlank
        String description
)
{}
