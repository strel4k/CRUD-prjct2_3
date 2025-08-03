package com.crudapp.service;

import com.crudapp.model.Label;

import java.util.List;

public interface LabelService {
    Label createLabel(String name);
    Label updateLabel(Long id, String name);
    Label getLabelById(Long id);
    List<Label> getAllLabels();
    void deleteLabel(Long id);
}
