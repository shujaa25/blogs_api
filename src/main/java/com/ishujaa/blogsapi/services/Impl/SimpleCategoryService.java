package com.ishujaa.blogsapi.services.Impl;

import com.ishujaa.blogsapi.mapper.CategoryMapper;
import com.ishujaa.blogsapi.model.Category;
import com.ishujaa.blogsapi.payload.req.CategoryRequestDTO;
import com.ishujaa.blogsapi.payload.res.CategoryResponse;
import com.ishujaa.blogsapi.payload.res.CategoryResponseDTO;
import com.ishujaa.blogsapi.repo.CategoryRepository;
import com.ishujaa.blogsapi.services.CategoryService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
@Service
public class SimpleCategoryService implements CategoryService {

    private final CategoryMapper categoryMapper;

    private final CategoryRepository categoryRepository;


    @Transactional
    @Override
    public CategoryResponseDTO createCategory(CategoryRequestDTO categoryRequestDTO) {
        Category category = categoryMapper.toEntity(categoryRequestDTO);
        categoryRepository.save(category);
        return categoryMapper.toResponseDTO(category);
    }

    @Override
    public CategoryResponseDTO getCategoryById(Long id) {
        Category category = categoryRepository.findById(id).orElseThrow(IllegalArgumentException::new);

        return categoryMapper.toResponseDTO(category);
    }

    @Override
    public CategoryResponse getCategories() {
        List<Category> categories = categoryRepository.findAll();

        if(categories.isEmpty()) throw new IllegalArgumentException("No Categories found");

        CategoryResponse categoryResponse = new CategoryResponse();
        categoryResponse.setContent(categories.stream().map(categoryMapper::toResponseDTO).toList());
        return categoryResponse;
    }

    @Transactional
    @Override
    public CategoryResponseDTO updateCategory(Long id, CategoryRequestDTO categoryRequestDTO) {
        Category category = categoryRepository.findById(id).orElseThrow(IllegalArgumentException::new);

        category.setName(categoryRequestDTO.name());
        category.setDescription(categoryRequestDTO.description());

        return categoryMapper.toResponseDTO(category);
    }

    @Transactional
    @Override
    public void deleteCategory(Long id) {
        Category category = categoryRepository.findById(id).orElseThrow(IllegalArgumentException::new);

        categoryRepository.delete(category);
    }

    @Transactional
    @Override
    public CategoryResponseDTO updatePartialCategory(Long id, Map<String, Object> updates) {
        Category category = categoryRepository.findById(id).orElseThrow(IllegalArgumentException::new);

        updates.forEach((key, val) -> {
            switch (key){
                case "name": category.setName((String) val); break;
                case "description": category.setDescription((String) val); break;
                default: throw new IllegalArgumentException("Invalid field");
            }
        });

        return categoryMapper.toResponseDTO(category);
    }
}
