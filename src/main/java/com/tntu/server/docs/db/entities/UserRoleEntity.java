package com.tntu.server.docs.db.entities;

import com.tntu.server.docs.db.entities.models.UserRoleKey;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Table;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity
@Table(name = "user_roles")
@IdClass(UserRoleKey.class)
public final class UserRoleEntity {

    @Id
    @Column(name = "user_id")
    long userId;

    @Id
    @Column(name = "role_id")
    long roleId;

}
