package com.tntu.server.docs.db.repositories.impl;

import com.tntu.server.docs.core.data.models.user.UserRoleModel;
import com.tntu.server.docs.core.repositories.UserRolesModelRepository;
import com.tntu.server.docs.db.entities.UserRoleEntity;
import com.tntu.server.docs.db.mapping.UserRoleMapper;
import com.tntu.server.docs.db.repositories.db.UserRoleDatabaseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Repository
public class UserRolesRepositoryImpl implements UserRolesModelRepository {

    @Autowired
    private UserRoleDatabaseRepository userRoleDatabaseRepository;

    @Override
    @Transactional
    public List<UserRoleModel> findByUserId(long userId) {
        List<UserRoleEntity> userRole = userRoleDatabaseRepository.findAllByUserId(userId);
        return userRole.stream().map(UserRoleMapper::toModel).collect(Collectors.toList());
    }

    @Override
    public void saveAssign(long userId, long roleId) {
        var entity = UserRoleMapper.toEntity(userId, roleId);
        userRoleDatabaseRepository.save(entity);
    }

    @Override
    @Transactional
    public void removeAssign(long userId, long roleId) {
        var entity = UserRoleMapper.toEntity(userId, roleId);
        userRoleDatabaseRepository.delete(entity);
    }

    @Override
    public boolean isExists(long userId, long roleId) {
        var key = UserRoleMapper.toKey(userId, roleId);
        return userRoleDatabaseRepository.existsById(key);
    }

}
