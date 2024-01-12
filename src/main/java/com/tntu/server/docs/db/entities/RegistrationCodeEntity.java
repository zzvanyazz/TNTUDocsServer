package com.tntu.server.docs.db.entities;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity
@Table(name = "registration_codes")
public class RegistrationCodeEntity {

    @Id
    @Column(name = "registration_code")
    String registrationCode;

    @Column(name = "email")
    String email;

    @Column(name = "role_id")
    long roleId;

}
