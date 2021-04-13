package com.tntu.server.docs.communication.models.requests.path;

import javax.validation.constraints.NotBlank;

public class CreatePathRequest {

    private String location;
    @NotBlank
    private String name;

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
