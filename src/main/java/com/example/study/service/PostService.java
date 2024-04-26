package com.example.study.service;

import com.example.study.entity.Post;
import com.example.study.exception.PostNotFoundException;
import com.example.study.exception.SubjectAlreadyExistsException;
import com.example.study.repository.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional
public class PostService {

    @Autowired
    private PostRepository postRepository;
    public Page<Post> listAll(Pageable pageable){
        return postRepository.findAll(pageable);
    }

    public Post findById(Long id) throws PostNotFoundException {
        return postRepository.findById(id)
                .orElseThrow(() -> new PostNotFoundException(String.format("Post with id %s not found", id)));
    }

    public Post save(Post post) throws SubjectAlreadyExistsException {
        validate(post);
        return postRepository.save(post);
    }

    public Post save(Post post, Long id) throws PostNotFoundException, SubjectAlreadyExistsException {
            Post saved = postRepository.findById(id)
                    .orElseThrow(() -> new PostNotFoundException(String.format("Post with id %s not found", id)));
            saved = new Post(saved.getId(), post.getSubject(), post.getText());

            return save(saved);
    }

    public void delete(Long id){
        postRepository.deleteById(id);
    }

    private void validate(Post post) throws SubjectAlreadyExistsException {
        if(findBySubject(post).isPresent()){
            throw new SubjectAlreadyExistsException(String.format("Subject %s already exists", post.getSubject()));
        }
    }
    private Optional<Post> findBySubject(Post post){
        if(post.getId() != null){
            return postRepository.findBySubjectAndIdNot(post.getSubject(), post.getId());
        }else{
            return postRepository.findBySubject(post.getSubject());
        }

    }


}
