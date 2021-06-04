package com.tntu.server.docs.db.repositories.impl;

import com.google.common.collect.Streams;
import com.tntu.server.docs.core.data.models.user.UserModel;
import com.tntu.server.docs.core.repositories.UserModelRepository;
import com.tntu.server.docs.db.entities.UserEntity;
import com.tntu.server.docs.db.mapping.UserMapper;
import com.tntu.server.docs.db.repositories.db.UserDatabaseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Repository
public class UserRepositoryImpl implements UserModelRepository {

    @Autowired
    private UserDatabaseRepository userDatabaseRepository;

    @Override
    public List<UserModel> getAll() {
        var allUsersIterable = userDatabaseRepository.findAll();
        return Streams.stream(allUsersIterable).map(UserMapper::toModel).collect(Collectors.toList());
    }

    @Override
    public Optional<UserModel> findLive(String email) {
        Optional<UserEntity> userEntity = userDatabaseRepository.findAlive(email);
        return userEntity.map(UserMapper::toModel);
    }

    @Override
    public Optional<UserModel> findActive(String normalizedUsername) {
        Optional<UserEntity> userEntity = userDatabaseRepository.findActive(normalizedUsername);
        return userEntity.map(UserMapper::toModel);
    }

    @Override
    public Optional<UserModel> findActive(Long id) {
        Optional<UserEntity> userEntity = userDatabaseRepository.findActive(id);
        return userEntity.map(UserMapper::toModel);
    }

    @Override
    public boolean existsById(long id) {
        return userDatabaseRepository.existsById(id);
    }

    @Override
    public boolean existsByEmail(String email) {
        return userDatabaseRepository.existsByEmail(email);
    }


    @Override
    public Optional<UserModel> getUser(long userId) {
        return userDatabaseRepository.findById(userId).map(UserMapper::toModel);
    }

    @Override
    public Optional<UserModel> createUser(UserModel userModel) {
        var userEntity = UserMapper.toEntity(userModel);
        userEntity = userDatabaseRepository.save(userEntity);
        userModel = UserMapper.toModel(userEntity);
        return Optional.of(userModel);
    }
}
