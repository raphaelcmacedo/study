package com.example.study.repository;

import com.example.study.entity.Post;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
public class PostRepositoryTest {

    @Autowired
    PostRepository postRepository;
    private Post post;

    private static Post buildAnyPost() {
        return Post.builder()
               .subject("subject")
               .text("text")
               .build();
    }

    @BeforeEach
    public void init() {
        post = buildAnyPost();
        post = postRepository.save(post);
    }

    @AfterEach
    public void tearDown() {
        postRepository.delete(post);
    }

    @Test
    void findBySubjectReturnsValidPost() throws Exception {
        Optional<Post> result = postRepository.findBySubject(post.getSubject());
        assertTrue(result.isPresent());
        assertEquals(post, result.get());
    }

    @Test
    void findBySubjectReturnsEmptyWhenNoPost() throws Exception {
        Optional<Post> result = postRepository.findBySubject("Another subject");
        assertFalse(result.isPresent());
    }



}
