package com.tntu.server.docs.communication.models.requests.users;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import java.util.List;

public class StartUsersRegistration {

    @NotBlank
    private String roleName;

    @NotEmpty
    private List<String> usersEmails;

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public List<String> getUsersEmails() {
        return usersEmails;
    }

    public void setUsersEmails(List<String> usersEmails) {
        this.usersEmails = usersEmails;
    }
}
