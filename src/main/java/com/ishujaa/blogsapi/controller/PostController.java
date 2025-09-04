package com.ishujaa.blogsapi.controller;

import com.ishujaa.blogsapi.config.AppConstants;
import com.ishujaa.blogsapi.payload.req.PostRequestDTO;
import com.ishujaa.blogsapi.payload.res.PostResponse;
import com.ishujaa.blogsapi.payload.res.PostResponseDTO;
import com.ishujaa.blogsapi.services.PostService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
@RestController
@RequestMapping("/posts")
public class PostController {

    private final PostService postService;

    @GetMapping
    public ResponseEntity<PostResponse> getAllPosts(
            @RequestParam(name = "pageNo", defaultValue = AppConstants.PAGE_NUMBER, required = false) Integer pageNo,
            @RequestParam(name = "pageSize", defaultValue = AppConstants.PAGE_SIZE, required = false) Integer pageSize,
            @RequestParam(name = "sortBy", defaultValue = AppConstants.SORT_POSTS_BY, required = false) String sortBy,
            @RequestParam(name = "sortOrder", defaultValue = AppConstants.SORT_DIRECTION, required = false) String sortOrder
    ){
        return ResponseEntity.ok(postService.getAllPosts(pageNo, pageSize, sortBy, sortOrder));
    }

    @PostMapping
    public ResponseEntity<PostResponseDTO> createPost(@Valid @RequestBody PostRequestDTO postRequestDTO){
        return ResponseEntity.status(HttpStatus.CREATED).body(postService.createPost(postRequestDTO));
    }

    @GetMapping("/{id}")
    public ResponseEntity<PostResponseDTO> getPostById(@PathVariable(name = "id") Long id){
        return ResponseEntity.ok(postService.getPostById(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<PostResponseDTO> updatePost(@PathVariable(name = "id") Long id,
                                                      @Valid @RequestBody PostRequestDTO postRequestDTO){
        return ResponseEntity.ok(postService.updatePost(id, postRequestDTO));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deletePost(@PathVariable(name = "id") Long id){
        postService.deletePost(id);
        return ResponseEntity.ok("Post with id " + id + " is deleted.");
    }

    @PatchMapping("/{id}")
    public ResponseEntity<PostResponseDTO> updatePartialPost(@PathVariable(name = "id") Long id,
                                                             @RequestBody Map<String, Object> updates){
        //updates.forEach((key, val) -> System.out.println(key + "," + val));

        return ResponseEntity.ok(postService.updatePartialPost(id, updates));
    }

    //pagination needed?
    @GetMapping("/category/{id}")
    public ResponseEntity<PostResponse> getPostsByCategoryId(@PathVariable(name = "id") Long id){
        return ResponseEntity.ok(postService.getPostsByCategoryId(id));
    }

}
