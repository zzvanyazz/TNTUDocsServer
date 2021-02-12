package com.tntu.server.docs.communication.models.requests.auth;

import javax.validation.constraints.NotBlank;

public final class RefreshTokenRequest {
    @NotBlank
    private String token;


    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}