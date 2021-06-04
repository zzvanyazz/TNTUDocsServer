package com.tntu.server.docs.db.repositories.db;

import com.tntu.server.docs.db.entities.UserEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserDatabaseRepository extends CrudRepository<UserEntity, Long> {

    @Query("SELECT u FROM UserEntity u WHERE u.email = :email AND u.deleteTimestamp IS NULL")
    Optional<UserEntity> findAlive(String email);

    @Query("SELECT u FROM UserEntity u WHERE u.username = :username AND u.deleteTimestamp IS NULL")
    Optional<UserEntity> findActive(String username);

    @Query("SELECT u FROM UserEntity u WHERE u.id = :id AND u.deleteTimestamp IS NULL")
    Optional<UserEntity> findActive(long id);

    boolean existsByEmail(String email);

}
