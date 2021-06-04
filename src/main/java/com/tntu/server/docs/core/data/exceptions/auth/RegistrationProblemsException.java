package com.tntu.server.docs.core.data.exceptions.auth;

import com.tntu.server.docs.core.data.exceptions.DocsException;

import java.util.List;


public class RegistrationProblemsException extends Exception {

    private List<DocsException> exceptions;

    public RegistrationProblemsException(List<DocsException> exceptions) {
        this.exceptions = exceptions;
    }

    public List<DocsException> getExceptions() {
        return exceptions;
    }

    public void setExceptions(List<DocsException> exceptions) {
        this.exceptions = exceptions;
    }
}
