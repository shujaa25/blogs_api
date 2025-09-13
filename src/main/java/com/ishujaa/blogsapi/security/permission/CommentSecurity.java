package com.ishujaa.blogsapi.security.permission;

import com.ishujaa.blogsapi.model.User;
import com.ishujaa.blogsapi.repo.CommentRepository;
import com.ishujaa.blogsapi.repo.PostRepository;
import com.ishujaa.blogsapi.security.SecurityUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CommentSecurity {

    private final SecurityUtils securityUtils;
    private final CommentRepository commentRepository;

    public boolean isOwner(Long postId){
        User user = securityUtils.getLoggedUser();
        return commentRepository.existsByIdAndUserId(postId, user.getId());
    }

}
