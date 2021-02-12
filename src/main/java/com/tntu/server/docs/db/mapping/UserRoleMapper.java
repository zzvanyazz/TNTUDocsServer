package com.tntu.server.docs.db.mapping;

import com.tntu.server.docs.core.models.data.UserRoleModel;
import com.tntu.server.docs.db.entities.UserRoleEntity;
import com.tntu.server.docs.db.models.UserRoleKey;

public class UserRoleMapper {


    public static UserRoleKey toKey(long userId, long roleId) {
        UserRoleKey userRoleKey = new UserRoleKey();
        userRoleKey.setUserId(userId);
        userRoleKey.setRoleId(roleId);
        return userRoleKey;
    }


    public static UserRoleEntity toEntity(long userId, long roleId) {
        UserRoleEntity userRoleEntity = new UserRoleEntity();
        userRoleEntity.setUserId(userId);
        userRoleEntity.setRoleId(roleId);
        return userRoleEntity;
    }

    public static UserRoleModel toModel(UserRoleEntity userRoleEntity) {
        UserRoleModel userRoleModel = new UserRoleModel();
        userRoleModel.setUserId(userRoleEntity.getUserId());
        userRoleModel.setRoleId(userRoleEntity.getRoleId());
        return userRoleModel;
    }

    public static UserRoleEntity toEntity(UserRoleModel userRoleModel) {
        UserRoleEntity userRoleEntity = new UserRoleEntity();
        userRoleEntity.setUserId(userRoleModel.getUserId());
        userRoleEntity.setRoleId(userRoleModel.getRoleId());
        return userRoleEntity;
    }

}
