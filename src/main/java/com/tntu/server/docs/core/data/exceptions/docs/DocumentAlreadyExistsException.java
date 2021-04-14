package com.tntu.server.docs.core.data.exceptions.docs;

public class DocumentAlreadyExistsException extends Exception {

    public DocumentAlreadyExistsException() {
        super("Document already exists.");
    }

}
