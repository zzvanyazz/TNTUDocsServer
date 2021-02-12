package com.tntu.server.docs.db.repositories.impl;

import com.google.common.collect.Streams;
import com.tntu.server.docs.core.models.data.RoleModel;
import com.tntu.server.docs.core.repositories.RoleModelRepository;
import com.tntu.server.docs.db.mapping.RoleMapper;
import com.tntu.server.docs.db.mapping.UserMapper;
import com.tntu.server.docs.db.repositories.db.RoleDatabaseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Repository
public class RoleRepositoryImpl implements RoleModelRepository {

    @Autowired
    private RoleDatabaseRepository roleDatabaseRepository;

    @Override
    public Optional<Long> findId(String name) {
        return roleDatabaseRepository.findId(name);
    }

    @Override
    public Optional<RoleModel> findByName(String name) {
        return roleDatabaseRepository.findByName(name).map(RoleMapper::toModel);
    }

    @Override
    public Optional<RoleModel> findById(long id) {
        return roleDatabaseRepository.findById(id).map(RoleMapper::toModel);
    }

    @Override
    public List<RoleModel> getAll() {
        var allRolesIterable = roleDatabaseRepository.findAll();
        return Streams.stream(allRolesIterable).map(RoleMapper::toModel).collect(Collectors.toList());
    }

    @Override
    public boolean isExists(long id) {
        return roleDatabaseRepository.existsById(id);
    }
}
