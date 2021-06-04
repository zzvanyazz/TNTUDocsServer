package com.tntu.server.docs.core.data.exceptions.user;

import com.tntu.server.docs.core.data.exceptions.DocsException;

public class RoleNotFoundException extends DocsException {

    public RoleNotFoundException(String roleName) {
        super(String.format("Can not find role '%s'", roleName));
    }

    public RoleNotFoundException() {
        super("Can not find role");
    }
}
