package com.ishujaa.blogsapi.services.Impl;

import com.ishujaa.blogsapi.mapper.PostMapper;
import com.ishujaa.blogsapi.model.Category;
import com.ishujaa.blogsapi.model.Post;
import com.ishujaa.blogsapi.payload.req.PostRequestDTO;
import com.ishujaa.blogsapi.payload.res.PostResponse;
import com.ishujaa.blogsapi.payload.res.PostResponseDTO;
import com.ishujaa.blogsapi.repo.CategoryRepository;
import com.ishujaa.blogsapi.repo.PostRepository;
import com.ishujaa.blogsapi.services.PostService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class SimplePostService implements PostService {

    private final PostMapper postMapper;

    private final PostRepository postRepository;
    private final CategoryRepository categoryRepository;

    @Transactional
    @Override
    public PostResponseDTO createPost(PostRequestDTO postRequestDTO) {

        Category category = categoryRepository.findById(postRequestDTO.categoryId()).orElseThrow(IllegalArgumentException::new);

        Post post = postMapper.toEntity(postRequestDTO);
        post.setCategory(category);
        postRepository.save(post);
        category.getPosts().add(post); //required?
        return postMapper.toResponseDTO(post);

    }

    @Override
    public PostResponse getAllPosts(Integer pageNo, Integer pageSize, String sortBy, String sortOrder) {

        Sort sortByAndOrder = sortOrder.equalsIgnoreCase("asc") ? Sort.by(sortBy).ascending() :
                Sort.by(sortBy).descending();

        Pageable pageDetails = PageRequest.of(pageNo, pageSize, sortByAndOrder);
        Page<Post> postPage = postRepository.findAll(pageDetails);

        List<Post> posts = postPage.getContent();
        if(posts.isEmpty()) throw new IllegalArgumentException("No posts found");

        List<PostResponseDTO> postResponseDTOList = posts.stream().map(postMapper::toResponseDTO).toList();

        PostResponse postResponse = new PostResponse();
        postResponse.setContent(postResponseDTOList);
        postResponse.setPageNo(postPage.getNumber());
        postResponse.setPageSize(postPage.getSize());
        postResponse.setTotalElements(postPage.getTotalElements());
        postResponse.setTotalPages(postPage.getTotalPages());
        postResponse.setLastPage(postPage.isLast());

        return postResponse;
    }

    @Override
    public PostResponseDTO getPostById(Long id) {
        Post post = postRepository.findById(id).orElseThrow(IllegalArgumentException::new);

        return postMapper.toResponseDTO(post);
    }

    @Transactional
    @Override
    public PostResponseDTO updatePost(Long id, PostRequestDTO postRequestDTO) {

        Category category = categoryRepository.findById(postRequestDTO.categoryId()).orElseThrow(IllegalArgumentException::new);

        Post post = postRepository.findById(id).orElseThrow(IllegalArgumentException::new);

        post.setContent(postRequestDTO.content());
        post.setTitle(postRequestDTO.title());
        post.setDescription(postRequestDTO.description());
        post.setCategory(category);

        return postMapper.toResponseDTO(post);
    }

    @Transactional
    @Override
    public void deletePost(Long id) {
        Post post = postRepository.findById(id).orElseThrow(IllegalArgumentException::new);

        Category category = categoryRepository.findById(post.getCategory().getId()).orElseThrow(IllegalArgumentException::new);

        category.getPosts().remove(post);
    }

    @Override
    public PostResponse getPostsByCategoryId(Long id) {
        Category category = categoryRepository.findById(id).orElseThrow(IllegalArgumentException::new);

        List<Post> posts = postRepository.findAllByCategory(category);

        if(posts.isEmpty()) throw new IllegalArgumentException("No posts found for category");

        List<PostResponseDTO> postResponseDTOList = posts.stream().map(postMapper::toResponseDTO).toList();

        PostResponse postResponse = new PostResponse();
        postResponse.setContent(postResponseDTOList);

        return postResponse;
    }

    @Transactional
    @Override
    public PostResponseDTO updatePartialPost(Long id, Map<String, Object> updates) {

        Post post = postRepository.findById(id).orElseThrow(IllegalArgumentException::new);

        updates.forEach((key, value) -> {
            switch (key){
                case "title":
                    post.setTitle((String) value); break;
                case "description":
                    post.setDescription((String) value); break;
                case "content":
                    post.setContent((String) value); break;
                case "categoryId":
                    Category category = categoryRepository.findById(Long.valueOf((String) value)) //issue (requires string)
                            .orElseThrow(IllegalArgumentException::new);
                    post.setCategory(category); break;
                default: throw new IllegalArgumentException("Invalid field specified.");
            }
        });
        postRepository.save(post);
        return postMapper.toResponseDTO(post);

    }

}
