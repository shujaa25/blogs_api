package com.ishujaa.blogsapi.mapper;

import com.ishujaa.blogsapi.model.Comment;
import com.ishujaa.blogsapi.payload.req.CommentRequestDTO;
import com.ishujaa.blogsapi.payload.res.CommentResponseDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface CommentMapper {

    CommentMapper INSTANCE = Mappers.getMapper(CommentMapper.class);

    Comment toEntity(CommentRequestDTO commentRequestDTO);

    CommentResponseDTO toResponseDTO(Comment comment);

}
