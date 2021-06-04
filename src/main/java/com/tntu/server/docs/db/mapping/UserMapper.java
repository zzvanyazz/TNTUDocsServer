package com.tntu.server.docs.db.mapping;

import com.tntu.server.docs.core.data.models.user.UserModel;
import com.tntu.server.docs.db.entities.UserEntity;

public class UserMapper {

    public static UserEntity toEntity(UserModel userModel) {
        UserEntity userEntity = new UserEntity();
        userEntity.setId(userModel.getId());
        userEntity.setUsername(userModel.getUsername());
        userEntity.setPasswordHash(userModel.getPasswordHash());
        userEntity.setEmail(userModel.getEmail());
        userEntity.setDeleteTimestamp(userModel.getDeleteTimestamp());
        userEntity.setValidTokenTimestamp(userModel.getValidTokenTimestamp());
        return userEntity;
    }

    public static UserModel toModel(UserEntity userEntity) {
        UserModel userModel = new UserModel();
        userModel.setId(userEntity.getId());
        userModel.setUsername(userEntity.getUsername());
        userModel.setPasswordHash(userEntity.getPasswordHash());
        userModel.setEmail(userEntity.getEmail());
        userModel.setDeleteTimestamp(userEntity.getDeleteTimestamp());
        userModel.setValidTokenTimestamp(userEntity.getValidTokenTimestamp());
        return userModel;
    }

}
