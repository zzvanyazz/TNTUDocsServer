package com.tntu.server.docs.core.repositories;

import com.tntu.server.docs.core.data.models.user.StartRegistrationModel;
import java.util.Optional;
import org.springframework.stereotype.Repository;

@Repository
public interface RegistrationRepository {

    void save(String email, String code, long roleId);

    Optional<StartRegistrationModel> getRegistrationModelByCode(String code);

    void deleteByCode(String code);

    void deleteByEmail(String code);

    boolean existsByEmail(String email);

}
