package com.tntu.server.docs.core.data.models.user;

import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CreateUserModel {

  String username;
  String normalizedUsername;
  String password;

}
