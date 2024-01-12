package com.tntu.server.docs.communication.models.responses;

import java.time.OffsetDateTime;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class AuthResponseData {

  long userId;
  String accessToken;
  String refreshToken;
  OffsetDateTime expiration;

}
