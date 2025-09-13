package com.ishujaa.blogsapi.mapper;

import com.ishujaa.blogsapi.model.User;
import com.ishujaa.blogsapi.payload.res.LoginResponseDTO;
import com.ishujaa.blogsapi.payload.res.SignupResponseDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface UserMapper {

    UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);

    @Mapping(source = "id", target = "userId")
    LoginResponseDTO toLoginResponseDTO(User user);

    SignupResponseDTO toSignupResponseDTO(User user);

}
