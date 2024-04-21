package com.example.study.controller;

import com.example.study.entity.Post;
import com.example.study.model.PostDTO;
import com.example.study.model.PostRequest;
import com.example.study.service.PostService;
import com.example.study.util.MapperUtil;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.List;

@RestController
@RequestMapping("/post")
public class PostController {

    @Autowired
    private PostService postService;

    private final MapperUtil mapperUtil = new MapperUtil();

    @GetMapping
    public ResponseEntity<Collection<PostDTO>> listAll(){
        List<Post> result = postService.listAll();
        List<PostDTO> resultDTO = mapperUtil.mapList(result, PostDTO.class);

        return ResponseEntity.ok(resultDTO);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PostDTO> findById (@PathVariable Long id){
        Post result = postService.findById(id);
        PostDTO resultDTO = mapperUtil.map(result, PostDTO.class);

        return ResponseEntity.ok(resultDTO);
    }

    @PostMapping
    public ResponseEntity<PostDTO> create(@Valid @RequestBody PostRequest request){
        Post post = mapperUtil.map(request, Post.class);
        Post result = postService.save(post);

        PostDTO postDTO = mapperUtil.map(result, PostDTO.class);
        return ResponseEntity.ok(postDTO);
    }

    @PutMapping("/{id}")
    public ResponseEntity<PostDTO> update(@Valid @PathVariable Long id, @RequestBody PostRequest request){
        Post post = mapperUtil.map(request, Post.class);
        Post result = postService.save(post, id);

        PostDTO postDTO = mapperUtil.map(result, PostDTO.class);
        return ResponseEntity.ok(postDTO);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<HttpStatus> delete(@PathVariable Long id) {
        postService.delete(id);
        return ResponseEntity.ok().build();
    }
}
