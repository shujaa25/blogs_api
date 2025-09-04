package com.ishujaa.blogsapi.mapper;

import com.ishujaa.blogsapi.model.Category;
import com.ishujaa.blogsapi.model.Comment;
import com.ishujaa.blogsapi.payload.req.CategoryRequestDTO;
import com.ishujaa.blogsapi.payload.req.CommentRequestDTO;
import com.ishujaa.blogsapi.payload.res.CategoryResponse;
import com.ishujaa.blogsapi.payload.res.CategoryResponseDTO;
import com.ishujaa.blogsapi.payload.res.CommentResponseDTO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface CategoryMapper {

    CategoryMapper INSTANCE = Mappers.getMapper(CategoryMapper.class);

    Category toEntity(CategoryRequestDTO categoryRequestDTO);

    CategoryResponseDTO toResponseDTO(Category category);

}
