package com.tntu.server.docs.db.mapping;

import com.tntu.server.docs.core.models.data.StartRegistrationModel;
import com.tntu.server.docs.db.entities.RegistrationCodeEntity;

public class RegistrationCodeMapper {

    public static RegistrationCodeEntity toEntity(String email, String code, long roleId) {
        RegistrationCodeEntity registrationCodeEntity = new RegistrationCodeEntity();
        registrationCodeEntity.setRegistrationCode(code);
        registrationCodeEntity.setEmail(email);
        registrationCodeEntity.setRoleId(roleId);
        return registrationCodeEntity;
    }

    public static StartRegistrationModel toModel(RegistrationCodeEntity entity) {
        StartRegistrationModel startRegistrationModel = new StartRegistrationModel();
        startRegistrationModel.setRegistrationCode(entity.getRegistrationCode());
        startRegistrationModel.setEmail(entity.getEmail());
        startRegistrationModel.setRoleId(entity.getRoleId());
        return startRegistrationModel;
    }

}
