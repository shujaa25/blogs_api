package com.ishujaa.blogsapi.services.Impl;

import com.ishujaa.blogsapi.exception.APIException;
import com.ishujaa.blogsapi.exception.ResourceNotFoundException;
import com.ishujaa.blogsapi.mapper.CommentMapper;
import com.ishujaa.blogsapi.model.Comment;
import com.ishujaa.blogsapi.model.Post;
import com.ishujaa.blogsapi.payload.req.CommentRequestDTO;
import com.ishujaa.blogsapi.payload.res.CommentResponse;
import com.ishujaa.blogsapi.payload.res.CommentResponseDTO;
import com.ishujaa.blogsapi.repo.CommentRepository;
import com.ishujaa.blogsapi.repo.PostRepository;
import com.ishujaa.blogsapi.security.SecurityUtils;
import com.ishujaa.blogsapi.services.CommentService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class SimpleCommentService implements CommentService {

    private final CommentMapper commentMapper;

    private final CommentRepository commentRepository;
    private final PostRepository postRepository;
    private final SecurityUtils securityUtils;

    @Override
    public CommentResponseDTO createComment(Long id, CommentRequestDTO commentRequestDTO) {

        Post post = postRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Post", "id", id));

        Comment comment = commentMapper.toEntity(commentRequestDTO);
        comment.setUser(securityUtils.getLoggedUser());
        comment.setPost(post);
        post.getComments().add(comment);

        commentRepository.save(comment);

        return commentMapper.toResponseDTO(comment);

    }

    @Override
    public CommentResponse getCommentsByPostId(Long id) {

        Post post = postRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Post", "id", id));

        List<CommentResponseDTO> commentResponseDTOS = commentRepository.findAllByPost(post).stream()
                .map(commentMapper::toResponseDTO).toList();

        CommentResponse commentResponse = new CommentResponse();
        commentResponse.setContent(commentResponseDTOS);

        return commentResponse;
    }

    @Override
    public CommentResponseDTO getCommentById(Long postId, Long commentId) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new ResourceNotFoundException("Post", "id", postId));
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new ResourceNotFoundException("Comment", "id", commentId));

        if(!comment.getPost().getId().equals(postId))
            throw new APIException("Comment doesn't belong to the post.");

        return commentMapper.toResponseDTO(comment);
    }

    @Transactional
    @Override
    public CommentResponseDTO updateComment(Long postId, Long commentId, CommentRequestDTO commentRequestDTO) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new ResourceNotFoundException("Post", "id", postId));
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new ResourceNotFoundException("Comment", "id", commentId));

        if(!comment.getPost().getId().equals(postId))
            throw new APIException("Comment doesn't belong to the post.");

        comment.setBody(commentRequestDTO.body());

        return commentMapper.toResponseDTO(comment);

    }

    @Transactional
    @Override
    public void deleteComment(Long postId, Long commentId) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new ResourceNotFoundException("Post", "id", postId));
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new ResourceNotFoundException("Comment", "id", commentId));

        if(!comment.getPost().getId().equals(postId))
            throw new APIException("Comment doesn't belong to the post.");

        post.getComments().remove(comment);
    }

    /*
    @Transactional
    @Override
    public CommentResponseDTO updatePartialComment(Long postId, Long commentId, Map<String, Object> updates) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new ResourceNotFoundException("Post", "id", postId));
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new ResourceNotFoundException("Comment", "id", commentId));

        if(!comment.getPost().getId().equals(postId))
            throw new APIException("Comment doesn't belong to the post.");

        updates.forEach((key, val) -> {
            switch (key){
                case "body": comment.setBody((String) val); break;
                default: throw new APIException("Invalid filed specified.");
            }
        });
        commentRepository.save(comment);
        return commentMapper.toResponseDTO(comment);
    }*/
}
