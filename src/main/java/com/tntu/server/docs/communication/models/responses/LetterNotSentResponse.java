package com.tntu.server.docs.communication.models.responses;

public class LetterNotSentResponse {

    private String message;

    public LetterNotSentResponse(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
