package com.example.study.controller;

import com.example.study.entity.Post;
import com.example.study.service.PostService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@WebMvcTest(PostController.class)
public class PostControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper mapper;


    @MockBean
    private PostService postService;

    private static Post buildAnyPost() {
        return Post.builder()
               .subject("subject")
               .text("text")
               .build();
    }

    @Test
    void listAllReturnList() throws Exception {
        Post post = buildAnyPost();
        List<Post> posts = List.of(post);

        given(postService.listAll()).willReturn(posts);

        mockMvc.perform(get("/post"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].subject", is(post.getSubject())));
    }

    @Test
    void findByIdReturnsPost() throws Exception {
        Post post = buildAnyPost();
        post.setId(1L);

        given(postService.findById(post.getId())).willReturn(post);

        mockMvc.perform(get("/post/{id}", post.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.subject", is(post.getSubject())));
    }

    @Test
    void saveReturnsPost() throws Exception {
        Post post = buildAnyPost();
        String json = mapper.writeValueAsString(post);

        given(postService.save(post)).willReturn(post);

        mockMvc.perform(post("/post").content(json).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.subject", is(post.getSubject())));
    }

    @Test
    void deleteReturnsOk() throws Exception {
        Long id = 1L;
        mockMvc.perform(delete("/post/{id}", id)).andExpect(status().isOk());
    }



}
