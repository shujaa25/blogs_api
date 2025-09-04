package com.ishujaa.blogsapi.payload.res;

import com.ishujaa.blogsapi.model.Category;
import com.ishujaa.blogsapi.model.Comment;

import java.util.Set;

public record PostResponseDTO (
        Long id,
        String title,
        String description,
        String content,
        Long categoryId,
        Set<Comment> comments
) {}