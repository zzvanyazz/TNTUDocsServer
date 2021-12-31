package com.tntu.server.docs.communication.models.exceptions;

import com.tntu.server.docs.core.data.exceptions.DocsException;

public class CanNotConvertDocumentException extends DocsException {
    public CanNotConvertDocumentException() {
        super("Can not convert document exception");
    }
}
