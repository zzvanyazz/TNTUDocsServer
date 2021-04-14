package com.tntu.server.docs.core.data.exceptions.user;

public class ActionOnAdminRoleException extends Exception {

    public ActionOnAdminRoleException() {
        super("Can not assign or remove assign 'Admin' role.");
    }
}
