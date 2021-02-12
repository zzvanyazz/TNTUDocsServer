package com.tntu.server.docs.core.models.exceptions;

public class ActionOnAdminRoleException extends Exception {

    public ActionOnAdminRoleException() {
        super("Can not assign or remove assign 'Admin' role.");
    }
}
