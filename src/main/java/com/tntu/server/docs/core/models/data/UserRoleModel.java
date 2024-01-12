package com.tntu.server.docs.core.models.data;

import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserRoleModel {

    long userId;
    long roleId;

}
