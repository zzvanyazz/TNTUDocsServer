package com.tntu.server.docs.core.models.data;

import java.time.OffsetDateTime;

public class RoleModel {

    public static final String ADMIN = "Admin";
    public static final String MANAGER = "Manager";

    private long id;

    private String name;

    private String description;

    private OffsetDateTime createTimestamp;

    private OffsetDateTime updateTimestamp;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public OffsetDateTime getCreateTimestamp() {
        return createTimestamp;
    }

    public void setCreateTimestamp(OffsetDateTime createTimestamp) {
        this.createTimestamp = createTimestamp;
    }

    public OffsetDateTime getUpdateTimestamp() {
        return updateTimestamp;
    }

    public void setUpdateTimestamp(OffsetDateTime updateTimestamp) {
        this.updateTimestamp = updateTimestamp;
    }

}
