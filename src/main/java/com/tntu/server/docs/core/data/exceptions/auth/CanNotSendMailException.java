package com.tntu.server.docs.core.data.exceptions.auth;

import com.tntu.server.docs.core.data.exceptions.DocsException;

public class CanNotSendMailException extends DocsException {

    public CanNotSendMailException(String mail) {
        super(String.format("Can not send letter to '%s'", mail));
    }
}
