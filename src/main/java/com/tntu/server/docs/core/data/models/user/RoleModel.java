package com.tntu.server.docs.core.data.models.user;

import java.time.OffsetDateTime;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class RoleModel {

    public static final String ADMIN = "Admin";
    public static final String MANAGER = "Manager";

    long id;
    String name;
    String description;
    OffsetDateTime createTimestamp;
    OffsetDateTime updateTimestamp;

}
