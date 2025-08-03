package service.impl;

import com.crudapp.model.Writer;
import com.crudapp.repository.WriterRepository;
import com.crudapp.service.impl.WriterServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class WriterServiceImplTest {
    @Mock
    private WriterRepository writerRepository;

    @InjectMocks
    private WriterServiceImpl writerService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCreateWriter() {
        Writer savedWriter = new Writer(1L, "Alex", "Pushkin");

        when(writerRepository.save(any(Writer.class))).thenReturn(savedWriter);

        Writer result = writerService.createWriter("Alex", "Pushkin");

        assertNotNull(result);
        assertEquals(savedWriter.getId(), result.getId());
        assertEquals("Alex", result.getFirstName());
        assertEquals("Pushkin", result.getLastName());
    }

    @Test
    void testUpdateWriter_Success() {
        Writer existing = new Writer(1L, "Old", "Name");
        Writer updated = new Writer(1L, "New", "Name");

        when(writerRepository.findById(1L)).thenReturn(existing);
        when(writerRepository.update(existing)).thenReturn(updated);

        Writer result = writerService.updateWriter(1L, "New", "Name");
        Long id = result.getId();

        assertEquals("New", result.getFirstName());
        verify(writerRepository).update(existing);
    }

    @Test
    void testUpdateWriter_NotFound() {
        when(writerRepository.findById(99L)).thenReturn(null);

        assertThrows(IllegalArgumentException.class, () -> writerService.updateWriter(99L, "New", "Name"));
    }

    @Test
    void testGetWriterById() {
        Writer writer = new Writer(1L, "Leo", "Tolsoy");
        when(writerRepository.findById(1L)).thenReturn(writer);

        Writer result = writerService.getWriterById(1L);

        assertNotNull(result);
        assertEquals("Leo", result.getFirstName());
    }

    @Test
    void testGetAllWriters() {
        List<Writer> writers = Arrays.asList(new Writer(1L, "A", "B"),
                                             new Writer(2L, "C", "D"));
        when(writerRepository.findAll()).thenReturn(writers);

        List<Writer> result = writerService.getAllWriters();

        assertEquals(2, result.size());
    }

    @Test
    void testDeleteWriter() {
        doNothing().when(writerRepository).deleteById(1L);
        writerService.deleteWriter(1L);
        verify(writerRepository).deleteById(1L);
    }
}
