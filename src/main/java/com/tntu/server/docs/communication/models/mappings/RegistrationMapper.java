package com.tntu.server.docs.communication.models.mappings;

import com.tntu.server.docs.communication.models.responses.LetterNotSentResponse;
import com.tntu.server.docs.communication.models.responses.NotToAllUsersLetterSentResponse;
import com.tntu.server.docs.core.data.exceptions.auth.RegistrationProblemsException;

import java.util.stream.Collectors;

public class RegistrationMapper {

    public static NotToAllUsersLetterSentResponse toResponse(RegistrationProblemsException exception) {
        var response = new NotToAllUsersLetterSentResponse();
        var list = exception.getExceptions()
                .stream()
                .map(RegistrationMapper::toResponse)
                .collect(Collectors.toList());
        response.setLetterNotSentResponses(list);
        return response;
    }

    public static LetterNotSentResponse toResponse(Exception exception) {
        return new LetterNotSentResponse(exception.getMessage());
    }

}
