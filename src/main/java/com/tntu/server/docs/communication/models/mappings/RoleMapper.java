package com.tntu.server.docs.communication.models.mappings;

import com.tntu.server.docs.communication.models.dto.RoleDto;
import com.tntu.server.docs.core.models.data.RoleModel;

public class RoleMapper {

    public static RoleDto toDto(RoleModel model) {
        RoleDto roleDto = new RoleDto();
        roleDto.setId(model.getId());
        roleDto.setName(model.getName());
        roleDto.setDescription(model.getDescription());
        roleDto.setCreateTimestamp(model.getCreateTimestamp());
        roleDto.setUpdateTimestamp(model.getUpdateTimestamp());
        return roleDto;
    }

}
