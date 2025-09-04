package com.ishujaa.blogsapi;

import com.ishujaa.blogsapi.model.Comment;
import com.ishujaa.blogsapi.services.Impl.SimplePostService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class PostServiceTest {

    @Autowired
    private SimplePostService postService;

    @Test
    void testAddComment(){

        Comment comment = Comment.builder().name("Shujaa").email("abc@xyz.com").body("First comment").build();

        postService.addComment(comment, 1L);

        System.out.println(comment);

    }

}
