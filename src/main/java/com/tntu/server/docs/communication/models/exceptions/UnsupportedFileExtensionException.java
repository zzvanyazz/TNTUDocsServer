package com.tntu.server.docs.communication.models.exceptions;

import com.tntu.server.docs.core.data.exceptions.DocsException;

public class UnsupportedFileExtensionException extends DocsException {

    public UnsupportedFileExtensionException() {
        super("Unsupported file extension for converting to PDF format");
    }
}
