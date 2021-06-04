package com.tntu.server.docs.db.repositories.impl;

import com.google.common.collect.Streams;
import com.tntu.server.docs.core.data.models.docs.SectionModel;
import com.tntu.server.docs.core.repositories.SectionRepository;
import com.tntu.server.docs.db.mapping.SectionMapper;
import com.tntu.server.docs.db.repositories.db.SectionDatabaseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Repository
public class SectionRepositoryImpl implements SectionRepository {

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
        return repository.findById(id).map(SectionMapper::toModel);
    }

    @Override
    public Optional<SectionModel> getSection(String name) {
        return repository.getSectionByName(name).map(SectionMapper::toModel);
    }

    @Override
    public List<SectionModel> getAllSections() {
        var sections = repository.findAll();
        return Streams.stream(sections)
                .map(SectionMapper::toModel)
                .collect(Collectors.toList());
    }

    @Override
    public SectionModel save(SectionModel sectionModel) {
        var sectionEntity = SectionMapper.toEntity(sectionModel);
        sectionEntity = repository.save(sectionEntity);
        return SectionMapper.toModel(sectionEntity);
    }

    @Override
    public void deleteSection(long id) {
        repository.deleteById(id);
    }
}
