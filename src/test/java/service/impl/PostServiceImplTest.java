package service.impl;

import com.crudapp.model.Label;
import com.crudapp.model.Post;
import com.crudapp.model.PostStatus;
import com.crudapp.model.Writer;
import com.crudapp.repository.LabelRepository;
import com.crudapp.repository.PostRepository;
import com.crudapp.repository.WriterRepository;
import com.crudapp.service.impl.PostServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class PostServiceImplTest {
    private PostRepository postRepository;
    private PostServiceImpl postService;
    private WriterRepository writerRepository;
    private LabelRepository labelRepository;

    @BeforeEach
    void setUp() {
        postRepository = mock(PostRepository.class);
        writerRepository = mock(WriterRepository.class);
        labelRepository = mock(LabelRepository.class);
        postService = new PostServiceImpl(postRepository, writerRepository, labelRepository);
    }

    @Test
    void testCreatePost() {
        Long writerId = 1L;
        List<Long> labelIds = List.of(100L, 200L);
        String content = "New Post";

        // mocks
        Writer writer = new Writer(writerId, "John", "Doe");
        Label label1 = new Label(100L, "Java");
        Label label2 = new Label(200L, "Spring");
        List<Label> labels = List.of(label1, label2);

        Post savedPost = new Post(content, writer, labels);
        savedPost.setId(1L);
        savedPost.setStatus(PostStatus.ACTIVE);

        when(writerRepository.findById(writerId)).thenReturn(writer);
        when(labelRepository.findById(100L)).thenReturn(label1);
        when(labelRepository.findById(200L)).thenReturn(label2);
        when(postRepository.save(any(Post.class))).thenReturn(savedPost);

        Post result = postService.createPost(content, writerId, labelIds);

        assertNotNull(result);
        assertEquals(1L, result.getId());
        assertEquals("New Post", result.getContent());
        assertEquals(PostStatus.ACTIVE, result.getStatus());
        assertEquals(2, result.getLabels().size());

        verify(postRepository).save(any(Post.class));
        verify(writerRepository).findById(writerId);
        verify(labelRepository).findById(100L);
        verify(labelRepository).findById(200L);
    }

    @Test
    void testGetPostById_whenExist() {
        Post post = new Post("Existing post", PostStatus.ACTIVE, Collections.emptyList());
        post.setId(1L);
        when(postRepository.getById(1L)).thenReturn(Optional.of(post));

        Post result = postService.getPostById(1L);

        assertNotNull(result);
        assertEquals(1L, result.getId());
        assertEquals("Existing post", result.getContent());
    }

    @Test
    void testGetPostById_whenNotExists() {
        when(postRepository.getById(999L)).thenReturn(Optional.empty());

        Post result = postService.getPostById(999L);

        assertNull(result);
    }

    @Test
    void testGetAllPosts() {
        Post post1 = new Post("Post 1", PostStatus.ACTIVE, Collections.emptyList());
        post1.setId(1L);
        Post post2 = new Post("Post 2", PostStatus.DELETED, Collections.emptyList());
        post2.setId(2L);
        List<Post> posts = Arrays.asList(post1, post2);

        when(postRepository.getAll()).thenReturn(posts);

        List<Post> result = postService.getAllPosts();

        assertEquals(2, result.size());
        verify(postRepository).getAll();
    }

    @Test
    void testUpdatePost_whenExists() {
        Post existing = new Post("Old Content", PostStatus.ACTIVE, Collections.emptyList());
        existing.setId(1L);
        when(postRepository.getById(1L)).thenReturn(Optional.of(existing));
        when(postRepository.update(any(Post.class))).thenAnswer(invocation -> invocation.getArgument(0));

        Post result = postService.updatePost(1L, "New Content", PostStatus.UNDER_REVIEW); // ✅

        assertEquals("New Content", result.getContent());
        assertEquals(PostStatus.UNDER_REVIEW, result.getStatus());
    }

    @Test
    void testUpdatePost_whenNotExists() {
        Post updated = new Post("Updated", PostStatus.DELETED, Collections.emptyList());

        when(postRepository.getById(999L)).thenReturn(Optional.empty());

        assertThrows(IllegalArgumentException.class, () -> {
            postService.updatePost(999L, updated.getContent(), updated.getStatus());
        });

        verify(postRepository, never()).update(any());
    }

    @Test
    void testDeletePost() {
        postService.deletePost(1L);
        verify(postRepository).deleteById(1L);
    }
}

