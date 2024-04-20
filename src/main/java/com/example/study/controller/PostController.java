package com.example.study.controller;

import com.example.study.entity.Post;
import com.example.study.service.PostService;
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
    @GetMapping
    public ResponseEntity<Collection<Post>> listAll(){
        List<Post> result = postService.listAll();
        return ResponseEntity.ok(result);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Post> findById (@PathVariable Long id){
        Post result = postService.findById(id);
        return ResponseEntity.ok(result);
    }

    @PostMapping
    public ResponseEntity<Post> save(@RequestBody Post post){
        Post result = postService.save(post);
        return ResponseEntity.ok(post);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<HttpStatus> delete(@PathVariable Long id){
        postService.delete(id);
       return ResponseEntity.ok().build();
    }
}
