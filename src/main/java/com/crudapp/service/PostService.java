package com.crudapp.service;

import com.crudapp.model.Post;
import com.crudapp.model.PostStatus;


import java.util.List;

public interface PostService {
    Post createPost(String content, Long writerId, List<Long> labelIds);
    Post updatePost(Long id, String content, PostStatus status);
    Post getPostById(Long id);
    List<Post> getAllPosts();
    void deletePost(Long id);
}
