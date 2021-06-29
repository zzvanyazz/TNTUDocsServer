package com.tntu.server.docs.db.repositories.db;

import com.tntu.server.docs.db.entities.SectionEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import javax.transaction.Transactional;
import java.util.Optional;

public interface SectionDatabaseRepository extends JpaRepository<SectionEntity, Long> {

    boolean existsByName(String name);

    Optional<SectionEntity> getSectionByName(String name);

    @Transactional
    @Modifying
    @Query("DELETE FROM SectionEntity WHERE id = :id")
    void delete(long id);

}
