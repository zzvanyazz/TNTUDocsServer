package com.tntu.server.docs.communication.models.dto;

import java.time.OffsetDateTime;

public class UserDto {

    private long id;

    private String username;

    private String normalizedUsername;

    private String email;

    private boolean enabled;

    private OffsetDateTime deleteTimestamp;

    private OffsetDateTime validTokenTimestamp;


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

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
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
