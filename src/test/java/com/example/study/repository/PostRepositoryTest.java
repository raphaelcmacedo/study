package com.example.study.repository;

import com.example.study.entity.Post;
import com.example.study.factory.PostFactory;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@Testcontainers
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class PostRepositoryTest {

    @Container
    @ServiceConnection
    static PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>("postgres:16.0");

    @Autowired
    PostRepository postRepository;
    private Post post;

    @BeforeEach
    public void init() {
        post = PostFactory.buildAnyPost();
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

    @Test
    void findBySubjectAndIdNotReturnsPostIfNotSamePost() throws Exception {
        Optional<Post> result = postRepository.findBySubjectAndIdNot(post.getSubject(), 0L);
        assertTrue(result.isPresent());
    }

    @Test
    void findBySubjectAndIdNotReturnsEmptyIfSamePost() throws Exception {
        Optional<Post> result = postRepository.findBySubjectAndIdNot(post.getSubject(), post.getId());
        assertFalse(result.isPresent());
    }



}
