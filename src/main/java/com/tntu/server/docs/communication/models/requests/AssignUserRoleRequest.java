package com.tntu.server.docs.communication.models.requests;

import javax.validation.constraints.NotNull;

public class AssignUserRoleRequest {

    @NotNull
    private Long roleId;

    public long getRoleId() {
        return roleId;
    }

    public void setRoleId(long roleId) {
        this.roleId = roleId;
    }
}
