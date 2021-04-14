package com.tntu.server.docs.core.data.models.user;

import java.time.OffsetDateTime;

public class UserModel {

    private long id;

    private String username;

    private String normalizedUsername;

    private String email;

    private String passwordHash;

    private boolean enabled;

    private OffsetDateTime deleteTimestamp;

    private OffsetDateTime validTokenTimestamp;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getNormalizedUsername() {
        return normalizedUsername;
    }

    public void setNormalizedUsername(String normalizedUsername) {
        this.normalizedUsername = normalizedUsername;
    }

    public String getPasswordHash() {
        return passwordHash;
    }

    public void setPasswordHash(String passwordHash) {
        this.passwordHash = passwordHash;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
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
