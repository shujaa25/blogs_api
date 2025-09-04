package com.ishujaa.blogsapi.services.Impl;

import com.ishujaa.blogsapi.mapper.CommentMapper;
import com.ishujaa.blogsapi.model.Comment;
import com.ishujaa.blogsapi.model.Post;
import com.ishujaa.blogsapi.payload.req.CommentRequestDTO;
import com.ishujaa.blogsapi.payload.res.CommentResponse;
import com.ishujaa.blogsapi.payload.res.CommentResponseDTO;
import com.ishujaa.blogsapi.repo.CommentRepository;
import com.ishujaa.blogsapi.repo.PostRepository;
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


    @Override
    public CommentResponseDTO createComment(Long id, CommentRequestDTO commentRequestDTO) {

        Post post = postRepository.findById(id).orElseThrow(IllegalArgumentException::new);

        Comment comment = commentMapper.toEntity(commentRequestDTO);
        comment.setPost(post);
        post.getComments().add(comment);

        commentRepository.save(comment);

        return commentMapper.toResponseDTO(comment);

    }

    @Override
    public CommentResponse getCommentsByPostId(Long id) {

        Post post = postRepository.findById(id).orElseThrow(IllegalArgumentException::new);

        List<CommentResponseDTO> commentResponseDTOS = commentRepository.findAllByPost(post).stream()
                .map(commentMapper::toResponseDTO).toList();

        CommentResponse commentResponse = new CommentResponse();
        commentResponse.setContent(commentResponseDTOS);

        return commentResponse;
    }

    @Override
    public CommentResponseDTO getCommentById(Long postId, Long commentId) {
        Post post = postRepository.findById(postId).orElseThrow(IllegalArgumentException::new);
        Comment comment = commentRepository.findById(commentId).orElseThrow(IllegalArgumentException::new);

        if(!comment.getPost().getId().equals(postId))
            throw new IllegalArgumentException("Post id doesn't match with comment's post id");

        return commentMapper.toResponseDTO(comment);
    }

    @Transactional
    @Override
    public CommentResponseDTO updateComment(Long postId, Long commentId, CommentRequestDTO commentRequestDTO) {
        Post post = postRepository.findById(postId).orElseThrow(IllegalArgumentException::new);
        Comment comment = commentRepository.findById(commentId).orElseThrow(IllegalArgumentException::new);

        if(!comment.getPost().getId().equals(postId))
            throw new IllegalArgumentException("Post id doesn't match with comment's post id");

        comment.setBody(commentRequestDTO.body());
        comment.setName(commentRequestDTO.name());
        comment.setEmail(commentRequestDTO.email());

        return commentMapper.toResponseDTO(comment);

    }

    @Transactional
    @Override
    public void deleteComment(Long postId, Long commentId) {
        Post post = postRepository.findById(postId).orElseThrow(IllegalArgumentException::new);
        Comment comment = commentRepository.findById(commentId).orElseThrow(IllegalArgumentException::new);

        if(!comment.getPost().getId().equals(postId))
            throw new IllegalArgumentException("Post id doesn't match with comment's post id");

        post.getComments().remove(comment);
    }

    @Transactional
    @Override
    public CommentResponseDTO updatePartialComment(Long postId, Long commentId, Map<String, Object> updates) {
        Post post = postRepository.findById(postId).orElseThrow(IllegalArgumentException::new);
        Comment comment = commentRepository.findById(commentId).orElseThrow(IllegalArgumentException::new);

        if(!comment.getPost().getId().equals(postId))
            throw new IllegalArgumentException("Post id doesn't match with comment's post id");

        updates.forEach((key, val) -> {
            switch (key){
                case "name": comment.setName((String) val); break;
                case "email": comment.setEmail((String) val); break;
                case "body": comment.setBody((String) val); break;
                default: throw new IllegalArgumentException("Invalid filed specified.");
            }
        });
        commentRepository.save(comment);
        return commentMapper.toResponseDTO(comment);
    }
}
