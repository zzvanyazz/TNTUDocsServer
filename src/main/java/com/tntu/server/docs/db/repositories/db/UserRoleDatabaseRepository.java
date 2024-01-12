package com.tntu.server.docs.db.repositories.db;

import com.tntu.server.docs.db.entities.UserRoleEntity;
import com.tntu.server.docs.db.entities.models.UserRoleKey;
import java.util.List;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRoleDatabaseRepository extends CrudRepository<UserRoleEntity, UserRoleKey> {
    List<UserRoleEntity> findAllByUserId(Long userName);

}
