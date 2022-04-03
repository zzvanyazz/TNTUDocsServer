package com.tntu.server.docs.communication.models.requests;

import javax.validation.constraints.NotNull;

public class AssignUserRoleRequest {

    @NotNull
    private Long roleId;

    public Long getRoleId() {
        return roleId;
    }

    public void setRoleId(Long roleId) {
        this.roleId = roleId;
    }
}
