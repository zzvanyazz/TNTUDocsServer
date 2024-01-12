package com.tntu.server.docs.communication.models.requests.auth;

import static com.tntu.server.docs.communication.models.Validation.MAX_LENGTH;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;
import org.hibernate.validator.constraints.Length;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class AuthRequest {

    @NotBlank
    @Length(max = MAX_LENGTH)
    String email;

    @NotEmpty
    String password;

}
