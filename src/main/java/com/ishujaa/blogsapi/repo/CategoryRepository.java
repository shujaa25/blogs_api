package com.ishujaa.blogsapi.repo;

import com.ishujaa.blogsapi.model.Category;
import jakarta.validation.constraints.NotBlank;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category, Long> {
    boolean existsByName(@NotBlank String name);
}