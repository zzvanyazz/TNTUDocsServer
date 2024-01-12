package com.tntu.server.docs.communication.models.requests;

import javax.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class AssignUserRoleRequest {

    @NotNull
    Long roleId;

}
