package com.tntu.server.docs.communication.models.responses;

import java.util.List;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class NotToAllUsersLetterSentResponse {

    List<LetterNotSentResponse> letterNotSentResponses;

}
