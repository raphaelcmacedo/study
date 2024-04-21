package com.example.study.factory;

import com.example.study.entity.Post;
import com.example.study.model.PostDTO;
import com.example.study.model.PostRequest;

public class PostFactory {

    public static Post buildAnyPost() {
        return Post.builder()
                .subject("subject")
                .text("text")
                .build();
    }

    public static PostDTO buildAnyDTO() {
        return new PostDTO( 1L, "subject", "text");
    }

    public static PostRequest buildNewPostRequest() {
        return new PostRequest("subject", "text");
    }
}
