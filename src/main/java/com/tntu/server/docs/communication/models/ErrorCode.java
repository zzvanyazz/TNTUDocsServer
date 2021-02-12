package com.tntu.server.docs.communication.models;

public final class ErrorCode {
    public static final int BAD_REQUEST = 1;
    public static final int FORBIDDEN = 2;
    public static final int NOT_FOUND = 3;
    public static final int CONFLICT = 4;

    private ErrorCode() {
    }
}
