package service.impl;

import com.crudapp.model.Post;
import com.crudapp.model.PostStatus;
import com.crudapp.repository.PostRepository;
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

public class PostServiceImlTest {
    private PostRepository postRepository;
    private PostServiceImpl postService;

    @BeforeEach
    void setUp() {
        postRepository = mock(PostRepository.class);
        postService = new PostServiceImpl(postRepository);
    }

    @Test
    void testCreatePost() {
        Post input = new Post("New Post", PostStatus.ACTIVE, Collections.emptyList());
        Post saved = new Post("New Post", PostStatus.ACTIVE, Collections.emptyList());
        saved.setId(1L);

        when(postRepository.save(input)).thenReturn(saved);

        Post result = postService.createPost(input);

        assertNotNull(result);
        assertEquals(1L, result.getContent());
        assertEquals("New Post", result.getContent());
        verify(postRepository).save(input);
    }

    @Test
    void testGetPostById_whenExist() {
        Post post = new Post("Existing post", PostStatus.ACTIVE, Collections.emptyList());
        post.setId(1L);
        when(postRepository.getById(1L)).thenReturn(Optional.of(post));

        Post result = postService.getById(1L);

        assertNotNull(result);
        assertEquals(1L, result.getId());
        assertEquals("Existing Post", result.getContent());
    }

    @Test
    void testGetPostById_whenNotExists() {
        when(postRepository.getById(999L)).thenReturn(Optional.empty());

        Post result = postService.getById(999L);

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

        List<Post> result = postService.getAll();

        assertEquals(2, result.size());
        verify(postRepository).getAll();
    }

    @Test
    void testUpdatePost_whenExists() {
        Post existing = new Post("Old Content", PostStatus.ACTIVE, Collections.emptyList());
        existing.setId(1L);
        Post updated = new Post("New Content", PostStatus.UNDER_REVIEW, Collections.emptyList());
        updated.setId(1L);

        when(postRepository.getById(1L)).thenReturn(Optional.of(existing));
        when(postRepository.update(any(Post.class))).thenReturn(updated);

        Post result = postService.update(1L, updated);

        assertEquals("New Content", result.getContent());
        assertEquals(PostStatus.UNDER_REVIEW, result.getStatus());

        ArgumentCaptor<Post> captor = ArgumentCaptor.forClass(Post.class);
        verify(postRepository).update(captor.capture());

        Post captured = captor.getValue();
        assertEquals("New Content", captured.getContent());
    }

    @Test
    void testUpdatePost_whenNotExists() {
        Post updated = new Post("Updated", PostStatus.DELETED, Collections.emptyList());

        when(postRepository.getById(999L)).thenReturn(Optional.empty());

        Post result = postService.update(999L, updated);

        assertNull(result);
        verify(postRepository, never()).update(any());
    }

    @Test
    void testDeletePost_whenExists() {
        Post post = new Post("To delete", PostStatus.ACTIVE, Collections.emptyList());
        post.setId(1L);

        when(postRepository.getById(1L)).thenReturn(Optional.of(post));

        boolean deleted = postService.deleteById(1L);

        assertTrue(deleted);
        verify(postRepository).deleteById(1L);
    }

    @Test
    void testDeletePost_whenNotExists() {
        when(postRepository.getById(1L)).thenReturn(Optional.empty());

        boolean deleted = postService.deleteById(1L);

        assertFalse(deleted);
        verify(postRepository, never()).deleteById(1L);
    }
    }
}
