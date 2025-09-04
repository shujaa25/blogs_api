package com.ishujaa.blogsapi.mapper;

import com.ishujaa.blogsapi.model.Post;
import com.ishujaa.blogsapi.payload.req.PostRequestDTO;
import com.ishujaa.blogsapi.payload.res.PostResponseDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface PostMapper {

    PostMapper INSTANCE = Mappers.getMapper(PostMapper.class);

    Post toEntity(PostRequestDTO postRequestDTO);

    @Mapping(source = "category.id", target = "categoryId")
    PostResponseDTO toResponseDTO(Post post);

}
