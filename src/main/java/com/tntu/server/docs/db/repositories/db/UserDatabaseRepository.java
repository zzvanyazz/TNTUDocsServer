package com.tntu.server.docs.db.repositories.db;

import com.tntu.server.docs.db.entities.UserEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserDatabaseRepository extends CrudRepository<UserEntity, Long> {

    @Query("SELECT u FROM UserEntity u WHERE u.normalizedUsername = :username AND u.enabled = true AND u.deleteTimestamp IS NULL")
    Optional<UserEntity> findAlive(String username);

    @Query("SELECT u FROM UserEntity u WHERE u.normalizedUsername = :username AND u.deleteTimestamp IS NULL")
    Optional<UserEntity> findActive(String username);

    @Query("SELECT u FROM UserEntity u WHERE u.id = :id AND u.enabled = true AND u.deleteTimestamp IS NULL")
    Optional<UserEntity> findActive(long id);

    Optional<UserEntity> findByNormalizedUsername(String normalisedUsername);

    boolean existsByNormalizedUsername(String normalisedUsername);

    boolean existsByEmail(String email);

}
