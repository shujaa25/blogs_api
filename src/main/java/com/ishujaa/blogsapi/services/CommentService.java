package com.ishujaa.blogsapi.services;

import com.ishujaa.blogsapi.payload.req.CommentRequestDTO;
import com.ishujaa.blogsapi.payload.res.CommentResponse;
import com.ishujaa.blogsapi.payload.res.CommentResponseDTO;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;

import java.util.Map;

public interface CommentService {
    CommentResponseDTO createComment(Long id, @Valid CommentRequestDTO commentRequestDTO);

    CommentResponse getCommentsByPostId(Long id);

    CommentResponseDTO getCommentById(Long id, Long commentId);

    CommentResponseDTO updateComment(Long id, Long commentId, @Valid CommentRequestDTO commentRequestDTO);

    @Transactional
    void deleteComment(Long id, Long commentId);

    CommentResponseDTO updatePartialComment(Long id, Long commentId, Map<String, Object> updates);
}
