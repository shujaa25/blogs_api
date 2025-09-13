package com.ishujaa.blogsapi.payload.res;

import lombok.RequiredArgsConstructor;

public record LoginResponseDTO(
        String jwt,
        Long userId
) {}
