package com.crudapp.repository;

import com.crudapp.model.Label;
import java.util.List;

public interface LabelRepository {
    Label save(Label label);
    Label update(Label label);
    Label findById(Long id);
    List<Label> findAll();
    void deleteById(Long id);
}
