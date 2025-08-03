package service.impl;

import com.crudapp.model.Label;
import com.crudapp.repository.LabelRepository;
import com.crudapp.service.impl.LabelServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class LabelServiceImplTest {
    private LabelRepository labelRepository;
    private LabelServiceImpl labelService;

    @BeforeEach
    void setUp() {
        labelRepository = mock(LabelRepository.class);
        labelService = new LabelServiceImpl(labelRepository);
    }

    @Test
    void testCreateLabel() {
        Label expected = new Label("Test");
        when(labelRepository.save(any(Label.class))).thenReturn(expected);

        Label result = labelService.createLabel("Test");

        assertNotNull(result);
        assertEquals("Test", result.getName());
        verify(labelRepository).save(any(Label.class));
    }

    @Test
    void testUpdateLabel_whenLabelExists() {
        Label existing = new Label(1L, "Old Name");
        when(labelRepository.findById(1L)).thenReturn(existing);
        when(labelRepository.update(any(Label.class))).thenAnswer(invocation -> invocation.getArgument(0));

        Label result = labelService.updateLabel(1L, "New Name");

        assertNotNull(result);
        assertEquals("New Name", result.getName());
        verify(labelRepository).findById(1L);
        verify(labelRepository).update(any(Label.class));
    }

    @Test
    void testUpdateLabel_whenLabelNotFound_shouldThrowException() {
        when(labelRepository.findById(999L)).thenReturn(null);
        Exception exception = assertThrows(IllegalArgumentException.class, () ->
                labelService.updateLabel(999L, "Test"));

        assertEquals("Label not found with id: 999", exception.getMessage());
        verify(labelRepository).findById(999L);
        verify(labelRepository, never()).update(any());
    }

    @Test
    void testGetLabelById() {
        Label label = new Label(2L, "FindMe");
        when(labelRepository.findById(2L)).thenReturn(label);

        Label result = labelService.getLabelById(2L);

        assertNotNull(result);
        assertEquals("FindMe", result.getName());
        verify(labelRepository).findById(2L);
    }

    @Test
    void testGetAllLabels() {
        List<Label> expected = Arrays.asList(
                new Label(1L, "Java"),
                new Label(2L, "C++"));
        when(labelRepository.findAll()).thenReturn(expected);

        List<Label> result = labelService.getAllLabels();

        assertEquals(2, result.size());
        assertEquals("Java", result.get(0).getName());
        verify(labelRepository).findAll();
    }

    @Test
    void testDeleteLabel() {
        labelService.deleteLabel(5L);
        verify(labelRepository).deleteById(5L);
    }
}
