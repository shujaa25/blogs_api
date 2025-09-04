package com.ishujaa.blogsapi.repo;

import com.ishujaa.blogsapi.model.Comment;
import com.ishujaa.blogsapi.model.Post;
import com.ishujaa.blogsapi.payload.res.CommentResponseDTO;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {
    List<Comment> findAllByPost(Post post);
}