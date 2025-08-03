package com.crudapp.repository;

import com.crudapp.model.Writer;

import java.util.List;

public interface WriterRepository {
    Writer save (Writer writer);
    Writer update (Writer writer);
    Writer findById(Long id);
    List<Writer> findAll();
    void deleteById(Long id);

}
