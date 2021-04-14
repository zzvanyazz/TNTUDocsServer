package com.tntu.server.docs.core.data.exceptions.auth;

public class CanNotSendMailException extends Exception {

    public CanNotSendMailException(String mail) {
        super(String.format("Can not send letter to '%s'", mail));
    }
}
