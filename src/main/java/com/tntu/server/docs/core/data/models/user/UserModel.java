package com.tntu.server.docs.core.data.models.user;

import java.time.OffsetDateTime;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserModel {

    Long id;
    String username;
    String email;
    String passwordHash;
    OffsetDateTime deleteTimestamp;

}
