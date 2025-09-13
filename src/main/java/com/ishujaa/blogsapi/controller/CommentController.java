package com.ishujaa.blogsapi.controller;

import com.ishujaa.blogsapi.payload.req.CommentRequestDTO;
import com.ishujaa.blogsapi.payload.res.CommentResponse;
import com.ishujaa.blogsapi.payload.res.CommentResponseDTO;
import com.ishujaa.blogsapi.services.CommentService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RequiredArgsConstructor
@RestController
public class CommentController {

    private final CommentService commentService;

    @PostMapping("/user/posts/{postId}/comments")
    public ResponseEntity<CommentResponseDTO> createComment(@PathVariable Long postId,
                                                            @Valid @RequestBody CommentRequestDTO commentRequestDTO){
        return ResponseEntity.status(HttpStatus.CREATED).body(commentService.createComment(postId, commentRequestDTO));
    }

    @GetMapping("/public/posts/{postId}/comments")
    public ResponseEntity<CommentResponse> getCommentsByPostId(@PathVariable Long postId){
        return ResponseEntity.ok(commentService.getCommentsByPostId(postId));
    }

    @GetMapping("/public/posts/{postId}/comments/{commentId}")
    public ResponseEntity<CommentResponseDTO> getCommentById(@PathVariable Long postId, @PathVariable Long commentId){
        return ResponseEntity.ok(commentService.getCommentById(postId, commentId));
    }

    @PreAuthorize("@commentSecurity.isOwner(#commentId) or hasRole('ADMIN')")
    @PostMapping("/user/posts/{postId}/comments/{commentId}")
    public ResponseEntity<CommentResponseDTO> updateComment(@PathVariable Long postId,
                                                            @PathVariable Long commentId,
                                                            @Valid @RequestBody CommentRequestDTO commentRequestDTO){
        return ResponseEntity.ok(commentService.updateComment(postId, commentId, commentRequestDTO));
    }

    @PreAuthorize("@postSecurity.isOwner(#postId) or @commentSecurity.isOwner(#commentId)  or hasRole('ADMIN')")
    @DeleteMapping("/user/posts/{postId}/comments/{commentId}")
    public ResponseEntity<Void> deleteComment(@PathVariable Long postId, @PathVariable Long commentId){
        commentService.deleteComment(postId, commentId);
        return ResponseEntity.ok(null);
    }

    /*@PreAuthorize("@commentSecurity.isOwner(#id)")
    @PatchMapping("/user/posts/{postId}/comments/{commentId}")
    public ResponseEntity<CommentResponseDTO> updatePartialComment(@PathVariable Long postId,
                                                            @PathVariable Long commentId,
                                              @RequestBody Map<String, Object> updates){
        return ResponseEntity.ok(commentService.updatePartialComment(postId, commentId, updates));
    }*/

}
