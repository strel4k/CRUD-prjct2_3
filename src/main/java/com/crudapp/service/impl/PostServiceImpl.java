package com.crudapp.service.impl;

import com.crudapp.model.Label;
import com.crudapp.model.Post;
import com.crudapp.model.PostStatus;
import com.crudapp.model.Writer;
import com.crudapp.repository.LabelRepository;
import com.crudapp.repository.PostRepository;
import com.crudapp.repository.WriterRepository;
import com.crudapp.service.PostService;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class PostServiceImpl implements PostService {

    private final PostRepository postRepository;
    private final WriterRepository writerRepository;
    private final LabelRepository labelRepository;

    public PostServiceImpl(PostRepository postRepository, WriterRepository writerRepository, LabelRepository labelRepository) {
        this.postRepository = postRepository;
        this.writerRepository = writerRepository;
        this.labelRepository = labelRepository;
    }

    @Override
    public Post createPost(String content, Long writerId, List<Long> labelIds) {
        Writer writer = writerRepository.findById(writerId);
        List<Label> labels = new ArrayList<>();
        for (Long labelId : labelIds) {
            labels.add(labelRepository.findById(labelId));
        }

        Post post = new Post(content, writer, labels);
        post.setStatus(PostStatus.ACTIVE);
        return postRepository.save(post);
    }

    @Override
    public Post updatePost(Long id, String content, PostStatus status) {
        Optional<Post> existingOpt = postRepository.getById(id);
        if (existingOpt.isEmpty()) {
            throw new IllegalArgumentException("Post not found with id: " + id);
        }
        Post existing = existingOpt.get();
        existing.setContent(content);
        existing.setStatus(status);
        return postRepository.update(existing);
    }

    @Override
    public Post getPostById(Long id) {
        return postRepository.getById(id).orElse(null);
    }

    @Override
    public List<Post> getAllPosts() {
        return postRepository.getAll();
    }

    @Override
    public void deletePost(Long id) {
        postRepository.deleteById(id);
    }
}