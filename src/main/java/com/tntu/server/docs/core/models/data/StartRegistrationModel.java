package com.tntu.server.docs.core.models.data;

import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class StartRegistrationModel {

    String registrationCode;
    String email;
    long roleId;

}
