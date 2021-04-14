package com.tntu.server.docs.db.repositories.impl;

import com.tntu.server.docs.core.data.models.user.StartRegistrationModel;
import com.tntu.server.docs.core.repositories.RegistrationRepository;
import com.tntu.server.docs.db.mapping.RegistrationCodeMapper;
import com.tntu.server.docs.db.repositories.db.RegistrationCodesDatabaseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.Optional;

@Repository
public class RegistrationCodeRepository implements RegistrationRepository {

    @Autowired
    private RegistrationCodesDatabaseRepository repository;


    @Override
    public void save(String email, String code, long roleId) {
        var entity = RegistrationCodeMapper.toEntity(email, code, roleId);
        repository.save(entity);
    }

    @Override
    public Optional<StartRegistrationModel> getRegistrationModelByCode(String code) {
        return repository.getByRegistrationCode(code).map(RegistrationCodeMapper::toModel);
    }

    @Transactional
    @Override
    public void deleteByCode(String code) {
        repository.deleteByRegistrationCode(code);
    }

    @Transactional
    @Override
    public void deleteByEmail(String email) {
        repository.deleteAllByEmail(email);
    }


    @Override
    public boolean existsByEmail(String email) {
        return repository.existsByEmail(email);
    }


}
