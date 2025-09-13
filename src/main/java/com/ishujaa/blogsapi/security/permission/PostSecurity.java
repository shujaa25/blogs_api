package com.ishujaa.blogsapi.security.permission;

import com.ishujaa.blogsapi.model.User;
import com.ishujaa.blogsapi.repo.PostRepository;
import com.ishujaa.blogsapi.security.SecurityUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class PostSecurity {

    private final PostRepository postRepository;
    private final SecurityUtils securityUtils;

    public boolean isOwner(Long postId){
        User user = securityUtils.getLoggedUser();
        return postRepository.existsByIdAndUserId(postId, user.getId());
    }

}
