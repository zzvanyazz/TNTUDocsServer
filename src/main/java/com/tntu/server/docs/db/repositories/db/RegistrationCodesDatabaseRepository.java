package com.tntu.server.docs.db.repositories.db;

import com.tntu.server.docs.db.entities.RegistrationCodeEntity;
import java.util.Optional;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RegistrationCodesDatabaseRepository extends CrudRepository<RegistrationCodeEntity, String> {

    Optional<RegistrationCodeEntity> getByRegistrationCode(String code);

    void deleteByRegistrationCode(String registrationCode);

    void deleteAllByEmail(String email);

    boolean existsByEmail(String email);

}
