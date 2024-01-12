package com.tntu.server.docs.core.models.data;

import java.time.OffsetDateTime;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserModel {

    long id;
    String username;
    String normalizedUsername;
    String email;
    String passwordHash;
    boolean enabled;
    OffsetDateTime deleteTimestamp;
    OffsetDateTime validTokenTimestamp;

}
