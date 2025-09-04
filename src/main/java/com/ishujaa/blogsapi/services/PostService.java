package com.ishujaa.blogsapi.services;

import com.ishujaa.blogsapi.model.Comment;
import com.ishujaa.blogsapi.payload.req.PostRequestDTO;
import com.ishujaa.blogsapi.payload.res.PostResponse;
import com.ishujaa.blogsapi.payload.res.PostResponseDTO;
import jakarta.validation.Valid;

import java.util.Map;

public interface PostService {

    PostResponseDTO createPost(PostRequestDTO postRequestDTO);

    PostResponse getAllPosts(Integer pageNo, Integer pageSize, String sortBy, String sortOrder);

    PostResponseDTO getPostById(Long id);

    PostResponseDTO updatePost(Long id, PostRequestDTO postRequestDTO);

    void deletePost(Long id);

    PostResponse getPostsByCategoryId(Long id);

    PostResponseDTO updatePartialPost(Long id, Map<String, Object> updates);
}
