package com.ishujaa.blogsapi.controller;

import com.ishujaa.blogsapi.payload.req.CategoryRequestDTO;
import com.ishujaa.blogsapi.payload.req.CommentRequestDTO;
import com.ishujaa.blogsapi.payload.res.CategoryResponse;
import com.ishujaa.blogsapi.payload.res.CategoryResponseDTO;
import com.ishujaa.blogsapi.payload.res.CommentResponseDTO;
import com.ishujaa.blogsapi.services.CategoryService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RequiredArgsConstructor
@RestController
public class CategoryController {

    private final CategoryService categoryService;

    @PostMapping("/admin/categories")
    public ResponseEntity<CategoryResponseDTO> createCategory(@Valid @RequestBody CategoryRequestDTO categoryRequestDTO){
        return ResponseEntity.status(HttpStatus.CREATED).body(categoryService.createCategory(categoryRequestDTO));
    }

    @GetMapping("/public/categories/{id}")
    public ResponseEntity<CategoryResponseDTO> getCategoryById(@PathVariable Long id){
        return ResponseEntity.ok(categoryService.getCategoryById(id));
    }

    @GetMapping("/public/categories")
    public ResponseEntity<CategoryResponse> getCategories(){
        return ResponseEntity.ok(categoryService.getCategories());
    }

    @PutMapping("/admin/categories/{id}")
    public ResponseEntity<CategoryResponseDTO> updateCategory(@PathVariable Long id,
                                                              @Valid @RequestBody CategoryRequestDTO categoryRequestDTO){
        return ResponseEntity.ok(categoryService.updateCategory(id, categoryRequestDTO));
    }

    @DeleteMapping("/admin/categories/{id}")
    public ResponseEntity<Void> deleteCategory(@PathVariable Long id){
        categoryService.deleteCategory(id);
        return ResponseEntity.ok(null);
    }

    @PatchMapping("/admin/categories/{id}")
    public ResponseEntity<CategoryResponseDTO> updatePartialCategory(@PathVariable Long id,
                                                                     @Valid @RequestBody Map<String, Object> updates){
        return ResponseEntity.ok(categoryService.updatePartialCategory(id, updates));
    }

}
