package com.tntu.server.docs.core.repositories;

import com.tntu.server.docs.core.models.data.StartRegistrationModel;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RegistrationRepository {

    void save(String email, String code, long roleId);

    Optional<StartRegistrationModel> getRegistrationModelByCode(String code);

    void deleteByCode(String code);

    void deleteByEmail(String code);

    boolean existsByEmail(String email);

}
