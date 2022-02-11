package com.tntu.server.docs.db.repositories.impl;

import com.tntu.server.docs.core.data.enums.DocumentStatus;
import com.tntu.server.docs.core.data.models.docs.DocumentModel;
import com.tntu.server.docs.core.repositories.DocumentRepository;
import com.tntu.server.docs.db.entities.DocumentEntity;
import com.tntu.server.docs.db.mapping.DocumentMapper;
import com.tntu.server.docs.db.repositories.db.DocumentsDatabaseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
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
    @Transactional
    @Modifying
    public void delete(long id) {
        repository.delete(id);
    }

    @Override
    public List<DocumentModel> getAllBySection(long section) {
        var documents = repository.findAll();

        return documents.stream()
                .map(DocumentMapper::toModel)
                .collect(Collectors.toList());
    }

    @Override
    public List<DocumentModel> getAll() {
        var documents = repository.findAll();

        return documents.stream()
                .map(DocumentMapper::toModel)
                .collect(Collectors.toList());
    }

    @Override
    public List<DocumentModel> find(String find) {
        return repository.findAllByNameContains(find)
                .stream()
                .map(DocumentMapper::toModel)
                .collect(Collectors.toList());
    }


}
