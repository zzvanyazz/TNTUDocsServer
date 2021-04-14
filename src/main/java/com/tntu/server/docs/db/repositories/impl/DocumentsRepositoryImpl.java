package com.tntu.server.docs.db.repositories.impl;

import com.google.common.collect.Streams;
import com.tntu.server.docs.core.data.models.docs.DocumentModel;
import com.tntu.server.docs.core.repositories.DocumentRepository;
import com.tntu.server.docs.db.mapping.DocumentMapper;
import com.tntu.server.docs.db.repositories.db.DocumentsDatabaseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@Repository
public class DocumentsRepositoryImpl implements DocumentRepository {

    @Autowired
    private DocumentsDatabaseRepository repository;

    @Override
    public boolean exists(long id) {
        return repository.existsById(id);
    }

    @Override
    public boolean exists(String name) {
        return repository.existsByName(name);
    }

    @Override
    public Optional<DocumentModel> getDocument(long id) {
        return repository.findById(id).map(DocumentMapper::toModel);
    }

    @Override
    public Optional<DocumentModel> getDocument(String name) {
        return repository.findByName(name).map(DocumentMapper::toModel);
    }

    @Override
    public DocumentModel save(DocumentModel model) {
        var entity = DocumentMapper.toEntity(model);
        entity = repository.save(entity);

        return DocumentMapper.toModel(entity);
    }

    @Override
    public void delete(long id) {
        repository.findById(id);
    }

    @Override
    public List<DocumentModel> getAllBySection(long section) {
        var documents = repository.findAll();

        return Streams.stream(documents)
                .map(DocumentMapper::toModel)
                .collect(Collectors.toList());
    }
}
