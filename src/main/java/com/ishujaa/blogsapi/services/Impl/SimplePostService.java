package com.ishujaa.blogsapi.services.Impl;

import com.ishujaa.blogsapi.exception.APIException;
import com.ishujaa.blogsapi.exception.ResourceNotFoundException;
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

        Category category = categoryRepository.findById(postRequestDTO.categoryId())
                .orElseThrow(() -> new ResourceNotFoundException("Category", "id", postRequestDTO.categoryId()));

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
        if(posts.isEmpty()) throw new APIException("No posts found.");

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
        Post post = postRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Post", "id", id));
        return postMapper.toResponseDTO(post);
    }

    @Transactional
    @Override
    public PostResponseDTO updatePost(Long id, PostRequestDTO postRequestDTO) {

        Category category = categoryRepository.findById(postRequestDTO.categoryId())
                .orElseThrow(() -> new ResourceNotFoundException("Category", "id", postRequestDTO.categoryId()));
        Post post = postRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Post", "id", id));

        post.setContent(postRequestDTO.content());
        post.setTitle(postRequestDTO.title());
        post.setDescription(postRequestDTO.description());
        post.setCategory(category);

        return postMapper.toResponseDTO(post);
    }

    @Transactional
    @Override
    public void deletePost(Long id) {
        Post post = postRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Post", "id", id));
        Category category = categoryRepository.findById(post.getCategory().getId())
                .orElseThrow(() -> new ResourceNotFoundException("Category", "id", post.getCategory().getId()));
        category.getPosts().remove(post);
    }

    @Override
    public PostResponse getPostsByCategoryId(Long id) {
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Category", "id", id));

        List<Post> posts = postRepository.findAllByCategory(category);

        if(posts.isEmpty()) throw new APIException("No posts found for the category.");

        List<PostResponseDTO> postResponseDTOList = posts.stream().map(postMapper::toResponseDTO).toList();

        PostResponse postResponse = new PostResponse();
        postResponse.setContent(postResponseDTOList);

        return postResponse;
    }

    @Transactional
    @Override
    public PostResponseDTO updatePartialPost(Long id, Map<String, Object> updates) {

        Post post = postRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Post", "id", id));

        updates.forEach((key, value) -> {

            if(value == null) throw new APIException("Field value cannot be null.");

            switch (key){
                case "title":
                    post.setTitle((String) value); break;
                case "description":
                    post.setDescription((String) value); break;
                case "content":
                    post.setContent((String) value); break;
                case "categoryId":
                    final Long catId = switch (value) {
                        case Long l -> l;
                        case Integer i -> i.longValue();
                        case String s -> Long.parseLong(s);
                        default -> throw new APIException("Invalid data type for categoryId.");
                    };

                    Category category = categoryRepository.findById(catId)
                            .orElseThrow(() -> new ResourceNotFoundException("Category", "id", catId));
                    post.setCategory(category); break;
                default: throw new APIException("Invalid field specified.");
            }
        });
        postRepository.save(post);
        return postMapper.toResponseDTO(post);

    }

}
