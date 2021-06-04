package com.tntu.server.docs.db.repositories.db;

import com.tntu.server.docs.db.entities.DocumentEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface DocumentsDatabaseRepository extends CrudRepository<DocumentEntity, Long> {

    boolean existsByName(String name);

    Optional<DocumentEntity> findByName(String name);

}
