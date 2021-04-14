package com.tntu.server.docs.db.repositories.db;

import com.tntu.server.docs.core.data.models.docs.SectionModel;
import com.tntu.server.docs.db.entities.SectionEntity;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface SectionDatabaseRepository extends CrudRepository<SectionEntity, Long> {

    boolean existsByName(String name);

    Optional<SectionEntity> getSectionByName(String name);


}
