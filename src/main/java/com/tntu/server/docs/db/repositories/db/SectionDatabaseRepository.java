package com.tntu.server.docs.db.repositories.db;

import com.tntu.server.docs.db.entities.SectionEntity;
import java.util.Optional;
import javax.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface SectionDatabaseRepository extends JpaRepository<SectionEntity, Long> {

    boolean existsByName(String name);

    Optional<SectionEntity> getSectionByName(String name);

    @Transactional
    @Modifying
    @Query("DELETE FROM SectionEntity WHERE id = :id")
    void delete(long id);

}
