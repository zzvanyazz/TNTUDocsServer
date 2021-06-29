package com.tntu.server.docs.db.repositories.impl;

import com.google.common.collect.Streams;
import com.tntu.server.docs.core.data.models.docs.DocumentModel;
import com.tntu.server.docs.core.data.models.docs.SectionModel;
import com.tntu.server.docs.core.repositories.SectionRepository;
import com.tntu.server.docs.db.entities.SectionEntity;
import com.tntu.server.docs.db.mapping.SectionMapper;
import com.tntu.server.docs.db.repositories.db.DocumentsDatabaseRepository;
import com.tntu.server.docs.db.repositories.db.SectionDatabaseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.transaction.TransactionScoped;
import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Repository
public class SectionRepositoryImpl implements SectionRepository {

    @Autowired
    private DocumentsDatabaseRepository documentsRepository;

    @Autowired
    private SectionDatabaseRepository repository;

    @Override
    public boolean exists(long id) {
        return repository.existsById(id);
    }

    @Override
    public boolean exists(String name) {
        return repository.existsByName(name);
    }

    @Override
    public Optional<SectionModel> getSection(long id) {
        return repository.findById(id).map(this::map);
    }

    @Override
    public Optional<SectionModel> getSection(String name) {
        return repository.getSectionByName(name).map(this::map);
    }

    @Override
    public List<SectionModel> getAllSections() {
        var sections = repository.findAll();
        return Streams.stream(sections)
                .map(this::map)
                .collect(Collectors.toList());
    }

    @Override
    public SectionModel save(SectionModel sectionModel) {
        var sectionEntity = SectionMapper.toEntity(sectionModel);
        sectionEntity = repository.save(sectionEntity);
        return SectionMapper.toModel(sectionEntity);
    }

    @Override
    @Transactional
    public void deleteSection(long id) {
        repository.delete(id);
    }

    private SectionModel map(SectionEntity sectionEntity) {
        var sectionId = sectionEntity.getId();
        var documents = documentsRepository.getAllBySectionId(sectionId);
        return SectionMapper.toModel(sectionEntity, documents);
    }
}
