package com.ishujaa.blogsapi.services;

import com.ishujaa.blogsapi.payload.req.CategoryRequestDTO;
import com.ishujaa.blogsapi.payload.res.CategoryResponse;
import com.ishujaa.blogsapi.payload.res.CategoryResponseDTO;
import jakarta.validation.Valid;

import java.util.Map;

public interface CategoryService {
    CategoryResponseDTO createCategory(@Valid CategoryRequestDTO categoryRequestDTO);

    CategoryResponseDTO getCategoryById(Long id);

    CategoryResponse getCategories();

    CategoryResponseDTO updateCategory(Long id, @Valid CategoryRequestDTO categoryRequestDTO);

    void deleteCategory(Long id);

    CategoryResponseDTO updatePartialCategory(Long id, @Valid Map<String, Object> updates);
}
