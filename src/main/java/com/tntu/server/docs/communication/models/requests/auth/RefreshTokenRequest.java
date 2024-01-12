package com.tntu.server.docs.communication.models.requests.auth;

import javax.validation.constraints.NotBlank;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public final class RefreshTokenRequest {

    @NotBlank
    String token;

}