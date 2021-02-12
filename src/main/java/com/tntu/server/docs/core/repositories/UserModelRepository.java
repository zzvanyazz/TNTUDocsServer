package com.tntu.server.docs.core.repositories;

import com.tntu.server.docs.core.models.data.UserModel;
import com.tntu.server.docs.db.entities.UserEntity;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserModelRepository {

    List<UserModel> getAll();

    Optional<UserModel> findLive(String normalizedUsername);

    Optional<UserModel> findActive(String normalizedUsername);

    Optional<UserModel> findActive(Long id);

    Optional<UserModel> findByName(String normalizedUsername);

    boolean existsByName(String normalizedUsername);

    boolean existsById(long id);

    boolean existsByEmail(String email);

    Optional<UserModel> getUser(long userId);

    Optional<UserModel> createUser(UserModel userModel);

}
