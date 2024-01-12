package com.tntu.server.docs.communication.models.dto;

import java.time.OffsetDateTime;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class RoleDto {

    long id;
    String name;
    String description;
    OffsetDateTime createTimestamp;
    OffsetDateTime updateTimestamp;

}
