package com.tntu.server.docs.core.data.exceptions.auth;

import java.util.List;
import com.tntu.server.docs.core.data.exceptions.DocsException;


public class RegistrationProblemsException extends Exception {

    private List<Exception> exceptions;

    public RegistrationProblemsException(List<Exception> exceptions) {
        this.exceptions = exceptions;
    }

    public List<Exception> getExceptions() {
        return exceptions;
    }

    public void setExceptions(List<Exception> exceptions) {
        this.exceptions = exceptions;
    }
}
