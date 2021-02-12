package com.tntu.server.docs.core.models.exceptions;

public class CanNotSendMailException extends Exception {

    public CanNotSendMailException(String mail) {
        super(String.format("Can not send letter to '%s'", mail));
    }
}
