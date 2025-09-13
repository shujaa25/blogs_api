package com.ishujaa.blogsapi.repo;

import com.ishujaa.blogsapi.model.Category;
import com.ishujaa.blogsapi.model.Post;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PostRepository extends JpaRepository<Post, Long> {

    List<Post> findAllByCategory(Category category);

    boolean existsByIdAndUserId(Long postId, Long id);
}