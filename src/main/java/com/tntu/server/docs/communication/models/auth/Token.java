package com.tntu.server.docs.communication.models.auth;

import java.time.OffsetDateTime;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public final class Token {

    long userId;
    OffsetDateTime issuedAt;

}
