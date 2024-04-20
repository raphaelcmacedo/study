package com.example.study.service;

import com.example.study.entity.Post;
import com.example.study.repository.PostRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.willDoNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
public class PostServiceTest {

    @InjectMocks
    PostService postService;

    @Mock
    PostRepository postRepository;

    @BeforeEach
    public void init() {
        MockitoAnnotations.openMocks(this);
    }

    private static Post buildAnyPost() {
        return Post.builder()
               .subject("subject")
               .text("text")
               .build();
    }

    @Test
    void listAllReturnList() throws Exception {
        Post post = buildAnyPost();
        List<Post> expected = List.of(post);

        given(postRepository.findAll()).willReturn(expected);

        List<Post> result = postService.listAll();

        assertNotNull(result);
        assertFalse(result.isEmpty());
        assertEquals(1, result.size());

        verify(postRepository, times(1)).findAll();
    }

    @Test
    void findByIdReturnsPost() throws Exception {
        Post expected = buildAnyPost();
        Long id = 1L;

        given(postRepository.findById(any(Long.class))).willReturn(Optional.ofNullable(expected));

        Post result = postService.findById(id);

        assertEquals(expected, result);
        verify(postRepository, times(1)).findById(any(Long.class));
    }

    @Test
    void saveReturnsPost() throws Exception {
        Post expected = buildAnyPost();

        given(postRepository.save(expected)).willReturn(expected);

        Post result = postService.save(expected);

        assertEquals(expected, result);
        verify(postRepository, times(1)).save(any(Post.class));
    }

    @Test
    void deleteReturnsOk() throws Exception {
        Long id = 1L;
        willDoNothing().given(postRepository).deleteById(any(Long.class));

        postService.delete(id);

        verify(postRepository, times(1)).deleteById(any(Long.class));
    }



}
