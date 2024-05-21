package com.example.study.integration;

import com.example.study.entity.Post;
import com.example.study.factory.PostFactory;
import com.example.study.model.PostDTO;
import com.example.study.model.PostRequest;
import com.example.study.repository.PostRepository;
import org.json.JSONObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import static org.junit.jupiter.api.Assertions.*;

@Testcontainers
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class PostIntegrationTest {

    @Container
    @ServiceConnection
    static PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>("postgres:16.0");

    @Autowired
    private TestRestTemplate restTemplate;
    @Autowired
    private PostRepository postRepository;

    private Long id = 1L;

    @BeforeEach
    void setup(){
        postRepository.deleteAll();

        Post post = Post.builder().subject("Hello").text("World").build();
        postRepository.save(post);
        id = post.getId();
    }

    @Test
    void listAllReturnPosts() throws Exception {
        String json = restTemplate.getForObject("/post", String.class);
        JSONObject data = new JSONObject(json);
        assertTrue(data.getLong("totalElements") > 0);
    }

    @Test
    void findByIdReturnsPostWhenValidId(){
        ResponseEntity<PostDTO> response = restTemplate.exchange("/post/" + id, HttpMethod.GET, null, PostDTO.class);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
    }

    @Test
    void findByIdReturns404WhenNotExist(){
        ResponseEntity<String> response = restTemplate.exchange("/post/1000", HttpMethod.GET, null, String.class);
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    void createValidPost(){
        PostRequest post = PostFactory.buildNewPostRequest();
        HttpEntity<PostRequest> httpEntity = new HttpEntity<>(post);

        ResponseEntity<PostDTO> response = restTemplate.exchange("/post", HttpMethod.POST, httpEntity, PostDTO.class);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(post.getSubject(), response.getBody().getSubject());
        assertEquals(post.getText(), response.getBody().getText());
    }
    @Test
    void createPostWithDuplicatedSubjectReturns409(){
        PostRequest post = PostRequest.builder().subject("Hello").text("World").build();
        HttpEntity<PostRequest> httpEntity = new HttpEntity<>(post);

        ResponseEntity<String> response = restTemplate.exchange("/post", HttpMethod.POST, httpEntity, String.class);
        assertEquals(HttpStatus.CONFLICT, response.getStatusCode());
    }

    @Test
    void updateReturnPostWhenValid(){
        PostRequest post = PostFactory.buildNewPostRequest();
        HttpEntity<PostRequest> httpEntity = new HttpEntity<>(post);

        ResponseEntity<PostDTO> response = restTemplate.exchange("/post/" + id, HttpMethod.PUT, httpEntity, PostDTO.class);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(id, response.getBody().getId());
        assertEquals(post.getSubject(), response.getBody().getSubject());
        assertEquals(post.getText(), response.getBody().getText());
    }

    @Test
    void updateReturn409WhenSubjectIsAlreadyUsed(){
        //Create a new post
        PostRequest postRequest = PostFactory.buildNewPostRequest();
        HttpEntity<PostRequest> httpEntity = new HttpEntity<>(postRequest);
        ResponseEntity<PostDTO> response = restTemplate.exchange("/post", HttpMethod.POST, httpEntity, PostDTO.class);
        PostDTO postDTO = response.getBody();
        assert postDTO != null;

        //Set the same subject as the feed in the database
        postRequest.setSubject("Hello");
        httpEntity = new HttpEntity<>(postRequest);

        ResponseEntity<String> updateResponse = restTemplate.exchange("/post/" + postDTO.getId(), HttpMethod.PUT, httpEntity, String.class);
        assertEquals(HttpStatus.CONFLICT, updateResponse.getStatusCode());
    }
    @Test
    void updateReturnPostWhenOnlyTheTextIsChange(){
        PostRequest post = PostRequest.builder().subject("Hello").text("World!!!").build();
        HttpEntity<PostRequest> httpEntity = new HttpEntity<>(post);

        ResponseEntity<PostDTO> response = restTemplate.exchange("/post/" + id, HttpMethod.PUT, httpEntity, PostDTO.class);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(id, response.getBody().getId());
        assertEquals(post.getSubject(), response.getBody().getSubject());
        assertEquals(post.getText(), response.getBody().getText());
    }

    @Test
    void deleteRemovesPost(){
        ResponseEntity<Void> response = restTemplate.exchange("/post/" + id, HttpMethod.DELETE, null, Void.class);
        assertEquals(HttpStatus.OK, response.getStatusCode());

        ResponseEntity<String> getResponse = restTemplate.exchange("/post/" + id, HttpMethod.GET, null, String.class);
        assertEquals(HttpStatus.NOT_FOUND, getResponse.getStatusCode());
    }

}
