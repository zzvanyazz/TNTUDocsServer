package com.tntu.server.docs.db.repositories.db;

import com.tntu.server.docs.db.entities.RoleEntity;
import java.util.Optional;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleDatabaseRepository extends CrudRepository<RoleEntity, Long> {

    @Query("SELECT id FROM RoleEntity WHERE name = :name")
    Optional<Long> findId(String name);

    Optional<RoleEntity> findByName(String name);

}
