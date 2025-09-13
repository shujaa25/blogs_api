package com.ishujaa.blogsapi.security;

import com.ishujaa.blogsapi.exception.APIException;
import com.ishujaa.blogsapi.mapper.UserMapper;
import com.ishujaa.blogsapi.model.User;
import com.ishujaa.blogsapi.model.type.RoleType;
import com.ishujaa.blogsapi.payload.req.LoginRequestDTO;
import com.ishujaa.blogsapi.payload.req.SignupRequestDTO;
import com.ishujaa.blogsapi.payload.res.LoginResponseDTO;
import com.ishujaa.blogsapi.payload.res.SignupResponseDTO;
import com.ishujaa.blogsapi.repo.UserRepository;
import com.ishujaa.blogsapi.security.jwt.AuthUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;

    private final AuthenticationManager authenticationManager;
    private final AuthUtil authUtil;
    private final UserRepository userRepository;

    public LoginResponseDTO login(LoginRequestDTO loginRequestDTO) {

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequestDTO.username(),
                        loginRequestDTO.password())
        );

        User user = (User) authentication.getPrincipal();

        String token = authUtil.generateAccessToken(user);

        return new LoginResponseDTO(token, user.getId());

    }

    public SignupResponseDTO signup(SignupRequestDTO signupRequestDTO) {
        User user = userRepository.findByUsername(signupRequestDTO.username()).orElse(null);

        if(user != null) throw new APIException("User already exists.");

        user = User.builder().username(signupRequestDTO.username())
                .password(passwordEncoder.encode(signupRequestDTO.password()))
                .roles(Set.of(RoleType.USER)).build();

        userRepository.save(user);

        return userMapper.toSignupResponseDTO(user);
    }
}
