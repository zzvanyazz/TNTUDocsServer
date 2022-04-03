package com.tntu.server.docs.communication.models.requests.auth;

import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;

import static com.tntu.server.docs.communication.models.Validation.MAX_LENGTH;

public class AuthRequest {

    @NotBlank
    @Length(max = MAX_LENGTH)
    private String email;

    @NotEmpty
    private String password;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
