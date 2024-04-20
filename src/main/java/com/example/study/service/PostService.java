package com.example.study.service;

import com.example.study.PostNotFoundException;
import com.example.study.entity.Post;
import com.example.study.repository.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PostService {

    @Autowired
    private PostRepository postRepository;
    public List<Post> listAll(){
        return postRepository.findAll();
    }

    public Post findById(Long id) throws PostNotFoundException {
        return postRepository.findById(id)
                .orElseThrow(() -> new PostNotFoundException(String.format("Post with id %s not found", id)));
    }
    public Post save(Post post){
        return postRepository.save(post);
    }

    public void delete(Long id){
        postRepository.deleteById(id);
    }


}
