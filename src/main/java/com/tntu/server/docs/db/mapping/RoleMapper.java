package com.tntu.server.docs.db.mapping;

import com.tntu.server.docs.core.data.models.user.RoleModel;
import com.tntu.server.docs.db.entities.RoleEntity;

public class RoleMapper {

    public static RoleModel toModel(RoleEntity roleEntity) {
        RoleModel roleModel = new RoleModel();
        roleModel.setId(roleEntity.getId());
        roleModel.setName(roleEntity.getName());
        roleModel.setDescription(roleEntity.getDescription());
        roleModel.setCreateTimestamp(roleEntity.getCreateTimestamp());
        roleModel.setUpdateTimestamp(roleEntity.getUpdateTimestamp());
        return roleModel;
    }

    public static RoleEntity toEntity(RoleModel roleModel) {
        RoleEntity roleEntity = new RoleEntity();
        roleEntity.setId(roleModel.getId());
        roleEntity.setName(roleModel.getName());
        roleEntity.setDescription(roleModel.getDescription());
        roleEntity.setCreateTimestamp(roleModel.getCreateTimestamp());
        roleEntity.setUpdateTimestamp(roleModel.getUpdateTimestamp());
        return roleEntity;
    }

}
