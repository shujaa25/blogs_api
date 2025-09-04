package com.ishujaa.blogsapi.payload.res;

import jakarta.validation.constraints.NotBlank;

public record CategoryResponseDTO(
        Long id,
        String name,
        String description
)
{}
