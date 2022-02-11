package com.tntu.server.docs.db.repositories.db;

import com.tntu.server.docs.core.data.enums.DocumentStatus;
import com.tntu.server.docs.db.entities.DocumentEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Repository
public interface DocumentsDatabaseRepository extends JpaRepository<DocumentEntity, Long> {

    boolean existsByName(String name);

    Optional<DocumentEntity> findByName(String name);

    List<DocumentEntity> getAllBySectionId(long sectionId);

    @Transactional
    @Modifying
    @Query("DELETE FROM DocumentEntity WHERE id = :id")
    void delete(long id);

    List<DocumentEntity> findAllByNameContains(String find);

}
