package com.tntu.server.docs.communication.models.responses;

import com.fasterxml.jackson.annotation.JsonInclude;

public class Response<T> extends ResponseBase {
    public static final Response<EmptyData> empty = new Response<>();

    @JsonInclude(value = JsonInclude.Include.NON_NULL)
    private T data;


    public Response() {
    }

    public Response(Error error) {
        super(error);
    }

    public Response(T data) {
        this.data = data;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
