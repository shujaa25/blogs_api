package com.ishujaa.blogsapi.services.Impl;

import com.ishujaa.blogsapi.exception.APIException;
import com.ishujaa.blogsapi.repo.UserRepository;
import com.ishujaa.blogsapi.services.UserService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SimpleUserService implements UserService {

    private final UserRepository userRepository;

    @Transactional
    @Override
    public void deleteUser(Long userId) {

        if(!userRepository.existsById(userId))
            throw new APIException("User not found by id.");

        userRepository.deleteById(userId);
    }
}
