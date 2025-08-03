package com.crudapp.service;

import com.crudapp.model.Writer;

import java.util.List;

public interface WriterService {
    Writer createWriter(String firstName, String lastName);
    Writer updateWriter(Long id, String firstName, String lastName);
    Writer getWriterById(Long id);
    List<Writer> getAllWriters();
    void deleteWriter(Long id);
}
