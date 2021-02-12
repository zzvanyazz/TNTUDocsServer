package com.tntu.server.docs.core.models.exceptions;

public class RoleNotFoundException extends Exception {

    public RoleNotFoundException(String roleName) {
        super(String.format("Can not find role '%s'", roleName));
    }
    public RoleNotFoundException() {
        super("Can not find role");
    }
}
