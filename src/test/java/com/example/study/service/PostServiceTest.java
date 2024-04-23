package com.example.study.service;

import com.example.study.entity.Post;
import com.example.study.exception.PostNotFoundException;
import com.example.study.exception.SubjectAlreadyExistsException;
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
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.BDDMockito.*;

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

        then(postRepository).should().findAll(any(Pageable.class));
    }

    @Test
    void findByIdReturnsPost() throws Exception {
        Post expected = PostFactory.buildAnyPost();
        Long id = 1L;

        given(postRepository.findById(anyLong())).willReturn(Optional.ofNullable(expected));

        Post result = postService.findById(id);

        assertEquals(expected, result);

        then(postRepository).should().findById(anyLong());
    }

    @Test
    void findByIdThrowsWhenNotExist() throws Exception {
        Post expected = PostFactory.buildAnyPost();
        Long id = 1L;

        given(postRepository.findById(anyLong())).willReturn(Optional.empty());

        assertThrows(PostNotFoundException.class, () -> postService.findById(id));
    }

    @Test
    void createReturnsPost() throws Exception {
        Post expected = PostFactory.buildAnyPost();

        given(postRepository.save(any(Post.class))).willReturn(expected);
        given(postRepository.findBySubject(anyString()))
                .willReturn(Optional.empty());

        Post result = postService.save(expected);

        assertEquals(expected, result);

        then(postRepository).should().save(any(Post.class));
        then(postRepository).should().findBySubject(anyString());
    }

    @Test
    void createThrowsIfSubjectExists() throws Exception {
        Post expected = PostFactory.buildAnyPost();

        given(postRepository.findBySubject(anyString())).willReturn(Optional.of(expected));

        assertThrows(SubjectAlreadyExistsException.class, () -> postService.save(expected));

        then(postRepository).should().findBySubject(anyString());
    }

    @Test
    void deleteReturnsOk() throws Exception {
        Long id = 1L;
        willDoNothing().given(postRepository).deleteById(anyLong());

        postService.delete(id);

        then(postRepository).should().deleteById(anyLong());
    }



}
