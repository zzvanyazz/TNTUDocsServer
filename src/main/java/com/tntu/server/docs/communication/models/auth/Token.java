package com.tntu.server.docs.communication.models.auth;

import java.time.OffsetDateTime;

public final class Token {
    private long userId;
    private OffsetDateTime issuedAt;

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public OffsetDateTime getIssuedAt() {
        return issuedAt;
    }

    public void setIssuedAt(OffsetDateTime issuedAt) {
        this.issuedAt = issuedAt;
    }
}
