package com.example.study.service;

import com.example.study.PostNotFoundException;
import com.example.study.entity.Post;
import com.example.study.factory.PostFactory;
import com.example.study.repository.PostRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

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


    @Test
    void listAllReturnList() throws Exception {
        Post post = PostFactory.buildAnyPost();
        List<Post> posts = List.of(post);
        Page<Post> expected = new PageImpl<>(posts);
        Pageable pageable = Pageable.ofSize(20);

        given(postRepository.findAll(any(Pageable.class))).willReturn(expected);

        Page<Post> result = postService.listAll(pageable);

        assertNotNull(result);
        assertFalse(result.isEmpty());

        verify(postRepository, times(1)).findAll(any(Pageable.class));
    }

    @Test
    void findByIdReturnsPost() throws Exception {
        Post expected = PostFactory.buildAnyPost();
        Long id = 1L;

        given(postRepository.findById(any(Long.class))).willReturn(Optional.ofNullable(expected));

        Post result = postService.findById(id);

        assertEquals(expected, result);
        verify(postRepository, times(1)).findById(any(Long.class));
    }

    @Test
    void findByIdThrowsWhenNotExist() throws Exception {
        Post expected = PostFactory.buildAnyPost();
        Long id = 1L;

        given(postRepository.findById(any(Long.class))).willReturn(Optional.empty());

        assertThrows(PostNotFoundException.class, () -> postService.findById(id));
    }

    @Test
    void createReturnsPost() throws Exception {
        Post expected = PostFactory.buildAnyPost();

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
