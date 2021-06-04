package com.tntu.server.docs.core.data.models.user;

import java.time.OffsetDateTime;

public class UserModel {

    private Long id;

    private String username;

    private String email;

    private String passwordHash;

    private OffsetDateTime deleteTimestamp;

    private OffsetDateTime validTokenTimestamp;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPasswordHash() {
        return passwordHash;
    }

    public void setPasswordHash(String passwordHash) {
        this.passwordHash = passwordHash;
    }

    public OffsetDateTime getDeleteTimestamp() {
        return deleteTimestamp;
    }

    public void setDeleteTimestamp(OffsetDateTime deleteTimestamp) {
        this.deleteTimestamp = deleteTimestamp;
    }

    public OffsetDateTime getValidTokenTimestamp() {
        return validTokenTimestamp;
    }

    public void setValidTokenTimestamp(OffsetDateTime validTokenTimestamp) {
        this.validTokenTimestamp = validTokenTimestamp;
    }
}
