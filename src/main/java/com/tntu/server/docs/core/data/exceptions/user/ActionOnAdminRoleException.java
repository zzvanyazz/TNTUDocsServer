package com.tntu.server.docs.core.data.exceptions.user;

import com.tntu.server.docs.core.data.exceptions.DocsException;

public class ActionOnAdminRoleException extends DocsException {

    public ActionOnAdminRoleException() {
        super("Can not assign or remove assign 'Admin' role.");
    }
}
