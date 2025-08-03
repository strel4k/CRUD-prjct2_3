package com.crudapp.repository;

import com.crudapp.model.Post;

import java.util.List;
import java.util.Optional;

public interface PostRepository {
    Post save(Post post);
    Post update(Post post);
    Optional<Post> getById(Long id);
    List<Post> getAll();
    void deleteById(Long id);
}
