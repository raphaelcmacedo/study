package com.example.study.controller;

import com.c4_soft.springaddons.security.oauth2.test.webmvc.AutoConfigureAddonsWebmvcResourceServerSecurity;
import com.example.study.configuration.SecurityConfig;
import com.example.study.entity.Post;
import com.example.study.exception.PostNotFoundException;
import com.example.study.exception.SubjectAlreadyExistsException;
import com.example.study.factory.PostFactory;
import com.example.study.model.PostDTO;
import com.example.study.model.PostRequest;
import com.example.study.service.PostService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientManager;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.willDoNothing;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@WebMvcTest(value = PostController.class, excludeAutoConfiguration = OAuth2AuthorizedClientManager.class)
@AutoConfigureAddonsWebmvcResourceServerSecurity
@Import(SecurityConfig.class)
public class PostControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper mapper;
    @MockBean
    private PostService postService;

    @Test
    void listAllReturnList() throws Exception {
        Post post = PostFactory.buildAnyPost();
        List<Post> posts = List.of(post);
        Page<Post> page = new PageImpl<>(posts);

        PostDTO postDTO = PostFactory.buildAnyDTO();
        List<PostDTO> postsDTO = List.of(postDTO);

        given(postService.listAll(any(Pageable.class))).willReturn(page);

        mockMvc.perform(get("/post"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.totalPages").value(1))
                .andExpect(jsonPath("$.totalElements").value(1))
                .andExpect(jsonPath("$.content[0].subject", is(post.getSubject())))
                .andExpect(jsonPath("$.content[0].text", is(post.getText())));
    }

    @Test
    void findByIdReturnsPost() throws Exception {
        Post post = PostFactory.buildAnyPost();
        post.setId(1L);

        given(postService.findById(anyLong())).willReturn(post);

        mockMvc.perform(get("/post/{id}", post.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.subject", is(post.getSubject())));
    }

    @Test
    void findThrows404WhenIdNotExists() throws Exception {
        Long id = 1L;
        given(postService.findById(anyLong()))
                .willThrow(new PostNotFoundException(String.format("Post with id %s not found", id)));

        mockMvc.perform(get("/post/{id}", id))
                .andExpect(status().isNotFound())   ;
    }

    @Test
    void createReturnsPost() throws Exception {
        Post post = PostFactory.buildAnyPost();
        PostRequest request = PostFactory.buildNewPostRequest();
        String json = mapper.writeValueAsString(request);

        given(postService.save(any(Post.class))).willReturn(post);

        mockMvc.perform(post("/post").content(json).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.subject", is(post.getSubject())));
    }
    @Test
    void createThrows409WhenSubjectExists() throws Exception {
        PostRequest request = PostFactory.buildNewPostRequest();
        String json = mapper.writeValueAsString(request);
        given(postService.save(any(Post.class))).willThrow(new SubjectAlreadyExistsException(""));

        mockMvc.perform(post("/post").content(json).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isConflict());
    }


    @Test
    void updateReturnsPostWhenIdExists() throws Exception {
        Post post = PostFactory.buildAnyPost();
        Long id = 1L;
        PostRequest request = PostFactory.buildNewPostRequest();
        String json = mapper.writeValueAsString(request);

        given(postService.save(any(Post.class), anyLong())).willReturn(post);

        mockMvc.perform(put("/post/{id}", id).content(json).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.subject", is(post.getSubject())));
    }

    @Test
    void updateThrows404WhenIdNotExists() throws Exception {
        Post post = PostFactory.buildAnyPost();
        Long id = 1L;
        PostRequest request = PostFactory.buildNewPostRequest();
        String json = mapper.writeValueAsString(request);

        given(postService.save(any(Post.class), anyLong()))
                .willThrow(new PostNotFoundException(String.format("Post with id %s not found", id)));

        mockMvc.perform(put("/post/{id}", id).content(json).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())   ;
    }

    @Test
    void deleteReturnsOk() throws Exception {
        Long id = 1L;

        willDoNothing().given(postService).delete(anyLong());

        mockMvc.perform(delete("/post/{id}", id)).andExpect(status().isOk());
    }



}
