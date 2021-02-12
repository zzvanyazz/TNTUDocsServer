package com.tntu.server.docs.db.repositories.db;

import com.tntu.server.docs.db.entities.UserRoleEntity;
import com.tntu.server.docs.db.models.UserRoleKey;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

@Repository
public interface UserRoleDatabaseRepository extends CrudRepository<UserRoleEntity, UserRoleKey> {
    List<UserRoleEntity> findAllByUserId(Long userName);

}
