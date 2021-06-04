package com.tntu.server.docs.db.entities;

import com.tntu.server.docs.db.entities.models.UserRoleKey;

import javax.persistence.*;

@Entity
@Table(name = "user_roles")
@IdClass(UserRoleKey.class)
public final class UserRoleEntity {
    @Id
    @Column(name = "user_id")
    private long userId;

    @Id
    @Column(name = "role_id")
    private long roleId;


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
}
