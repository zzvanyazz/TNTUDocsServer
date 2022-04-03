package com.tntu.server.docs.communication.models.dto;

import java.time.OffsetDateTime;

public class UserDto {

    private long id;

    private String username;

    private String email;

    private OffsetDateTime deleteTimestamp;

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

}
