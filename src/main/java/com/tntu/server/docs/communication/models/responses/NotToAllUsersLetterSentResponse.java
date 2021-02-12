package com.tntu.server.docs.communication.models.responses;

import java.util.List;

public class NotToAllUsersLetterSentResponse {

    List<LetterNotSentResponse> letterNotSentResponses;

    public List<LetterNotSentResponse> getLetterNotSentResponses() {
        return letterNotSentResponses;
    }

    public void setLetterNotSentResponses(List<LetterNotSentResponse> letterNotSentResponses) {
        this.letterNotSentResponses = letterNotSentResponses;
    }
}
