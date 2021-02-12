package com.tntu.server.docs.db.mapping;

import com.tntu.server.docs.core.models.data.UserModel;
import com.tntu.server.docs.db.entities.UserEntity;

public class UserMapper {

    public static UserEntity toEntity(UserModel userModel) {
        UserEntity userEntity = new UserEntity();
        userEntity.setId(userModel.getId());
        userEntity.setUsername(userModel.getUsername());
        userEntity.setNormalizedUsername(userModel.getNormalizedUsername());
        userEntity.setPasswordHash(userModel.getPasswordHash());
        userEntity.setEmail(userModel.getEmail());
        userEntity.setEnabled(userModel.isEnabled());
        userEntity.setDeleteTimestamp(userModel.getDeleteTimestamp());
        userEntity.setValidTokenTimestamp(userModel.getValidTokenTimestamp());
        return userEntity;
    }

    public static UserModel toModel(UserEntity userEntity) {
        UserModel userModel = new UserModel();
        userModel.setId(userEntity.getId());
        userModel.setUsername(userEntity.getUsername());
        userModel.setNormalizedUsername(userEntity.getNormalizedUsername());
        userModel.setPasswordHash(userEntity.getPasswordHash());
        userModel.setEnabled(userEntity.isEnabled());
        userModel.setEmail(userEntity.getEmail());
        userModel.setDeleteTimestamp(userEntity.getDeleteTimestamp());
        userModel.setValidTokenTimestamp(userEntity.getValidTokenTimestamp());
        return userModel;
    }

}
