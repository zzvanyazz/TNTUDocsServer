package com.tntu.server.docs.core.data.exceptions.docs;

public class DocumentAlreadyExistsException extends DocumentException {

    public DocumentAlreadyExistsException() {
        super("Document already exists.");
    }

}
