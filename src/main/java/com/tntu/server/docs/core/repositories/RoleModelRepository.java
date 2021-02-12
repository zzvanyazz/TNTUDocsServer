package com.tntu.server.docs.core.repositories;

import com.tntu.server.docs.core.models.data.RoleModel;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface RoleModelRepository {

    Optional<Long> findId(String name);

    Optional<RoleModel> findByName(String name);

    Optional<RoleModel> findById(long id);

    List<RoleModel> getAll();

    boolean isExists(long id);

}
