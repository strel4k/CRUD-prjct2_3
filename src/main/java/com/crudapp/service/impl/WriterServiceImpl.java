package com.crudapp.service.impl;

import com.crudapp.model.Writer;
import com.crudapp.repository.WriterRepository;
import com.crudapp.service.WriterService;
import java.util.List;

public class WriterServiceImpl implements WriterService {

    private final WriterRepository writerRepository;

    public WriterServiceImpl(WriterRepository writerRepository) {
        this.writerRepository = writerRepository;
    }

    @Override
    public Writer createWriter(String firstName, String lastName) {
        Writer writer = new Writer(firstName, lastName);
        return writerRepository.save(writer);
    }

    @Override
    public Writer updateWriter(Long id, String firstName, String lastName) {
        Writer existing = writerRepository.findById(id);
        if (existing == null) {
            throw new IllegalArgumentException("Writer not found with id: " + id);
        }
            existing.setFirstName(firstName);
            existing.setLastName(lastName);
            return writerRepository.update(existing);
    }

    @Override
    public Writer getWriterById(Long id) {
        return writerRepository.findById(id);
    }

    @Override
    public List<Writer> getAllWriters() {
        return writerRepository.findAll();
    }

    @Override
    public void deleteWriter(Long id) {
        writerRepository.deleteById(id);
    }
}
