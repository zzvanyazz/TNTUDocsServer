package com.tntu.server.docs.communication.models.responses;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.Map;

public final class Error {
    private int code;
    private String message;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Map<String, String> details;


    public Error() {
    }

    public Error(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Map<String, String> getDetails() {
        return details;
    }

    public void setDetails(Map<String, String> details) {
        this.details = details;
    }
}
