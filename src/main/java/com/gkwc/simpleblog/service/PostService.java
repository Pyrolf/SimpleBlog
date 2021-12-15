package com.gkwc.simpleblog.service;

import java.time.Instant;
import java.util.List;

import com.gkwc.simpleblog.dto.PostDto;
import com.gkwc.simpleblog.exception.PostNotFoundException;
import com.gkwc.simpleblog.model.Post;
import com.gkwc.simpleblog.repository.PostRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Service;

import static java.util.stream.Collectors.toList;

@Service
public class PostService {
    @Autowired
    private AuthService authService;
    @Autowired
    private PostRepository postRepository;

    public List<PostDto> showAllPosts() {
        List<Post> posts = postRepository.findAll();
        return posts.stream().map(this::mapFromPostToDto).collect(toList());
    }

    public void createPost(PostDto postDto) {
        Post post = mapFromDtoToPost(postDto);
        postRepository.save(post);
    }

    private PostDto mapFromPostToDto(Post post) {
        PostDto postDto = new PostDto();
        postDto.setId(post.getId());
        postDto.setTitle(post.getTitle());
        postDto.setContent(post.getContent());
        postDto.setUsername(post.getUsername());
        return postDto;
    }

    private Post mapFromDtoToPost(PostDto postDto) {
        Post post = new Post();
        post.setTitle(postDto.getTitle());
        post.setContent(postDto.getContent());
        User loggedinUser = authService.getCurrentUser().orElseThrow(() -> new IllegalArgumentException("No User logged in"));
        post.setCreatedOn(Instant.now());
        post.setUsername(loggedinUser.getUsername());
        post.setUpdatedOn(Instant.now());
        return post;
    }

    public PostDto readSinglePost(Long id) {
        Post post = postRepository.findById(id).orElseThrow(() -> new PostNotFoundException("For id " + id));
        return mapFromPostToDto(post);
    }
}
