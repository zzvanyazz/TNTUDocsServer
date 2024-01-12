package com.tntu.server.docs.communication.models.dto;

import java.time.OffsetDateTime;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserDto {

    long id;
    String username;
    String email;
    OffsetDateTime deleteTimestamp;

}
