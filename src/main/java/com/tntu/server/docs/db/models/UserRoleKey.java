package com.tntu.server.docs.db.models;

import java.io.Serializable;
import java.util.Objects;

public class UserRoleKey implements Serializable {
    private long userId;
    private long roleId;


    public UserRoleKey() {
    }

    public UserRoleKey(long userId, long roleId) {
        this.userId = userId;
        this.roleId = roleId;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public long getRoleId() {
        return roleId;
    }

    public void setRoleId(long roleId) {
        this.roleId = roleId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }

        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        var other = (UserRoleKey) o;

        return userId == other.userId &&
                roleId == other.roleId;
    }

    @Override
    public int hashCode() {
        return Objects.hash(userId, roleId);
    }
}
