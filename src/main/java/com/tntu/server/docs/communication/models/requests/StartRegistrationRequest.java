package com.tntu.server.docs.communication.models.requests;

import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class StartRegistrationRequest {

    String registrationCode;
    String userName;
    String normalisedUserName;
    String password;

}
