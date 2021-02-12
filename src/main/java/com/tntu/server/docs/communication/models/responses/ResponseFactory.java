package com.tntu.server.docs.communication.models.responses;

import com.tntu.server.docs.communication.models.ErrorCode;

public final class ResponseFactory {
    private ResponseFactory() {
    }

    public static Response<?> createForbidden(Exception exception) {
        var error = new Error(ErrorCode.FORBIDDEN, exception.getMessage());

        return new Response<>(error);
    }

    public static Response<?> createNotFound(Exception exception) {
        var error = new Error(ErrorCode.NOT_FOUND, exception.getMessage());

        return new Response<>(error);
    }

    public static Response<?> createConflict(Exception exception) {
        var error = new Error(ErrorCode.CONFLICT, exception.getMessage());

        return new Response<>(error);
    }
}
