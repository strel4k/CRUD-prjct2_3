package com.crudapp.service.impl;

import com.crudapp.model.Label;
import com.crudapp.repository.LabelRepository;
import com.crudapp.service.LabelService;
import java.util.List;

public class LabelServiceImpl implements LabelService {
    private final LabelRepository labelRepository;

    public LabelServiceImpl(LabelRepository labelRepository) {
        this.labelRepository = labelRepository;
    }

    @Override
    public Label createLabel(String name) {
        return labelRepository.save(new Label(name));
    }

    @Override
    public Label updateLabel(Long id, String name) {
        Label existing = labelRepository.findById(id);
        if (existing == null) {
            throw new IllegalArgumentException("Label not found with id: " + id);
        }
        existing.setName(name);
        return labelRepository.update(existing);
    }

    @Override
    public Label getLabelById(Long id) {
        return labelRepository.findById(id);
    }

    @Override
    public List<Label> getAllLabels() {
        return labelRepository.findAll();
    }

    @Override
    public void deleteLabel(Long id) {
        labelRepository.deleteById(id);
    }
}
