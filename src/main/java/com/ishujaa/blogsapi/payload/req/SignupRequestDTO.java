package com.ishujaa.blogsapi.payload.req;

import jakarta.validation.constraints.NotBlank;

public record SignupRequestDTO(
        @NotBlank
        String username,
        @NotBlank
        String password
) {}
