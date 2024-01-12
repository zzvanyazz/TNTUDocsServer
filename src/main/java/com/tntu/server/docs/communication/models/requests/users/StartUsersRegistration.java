package com.tntu.server.docs.communication.models.requests.users;

import java.util.List;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class StartUsersRegistration {

    @NotBlank
    String roleName;

    @NotEmpty
    List<String> usersEmails;

}
