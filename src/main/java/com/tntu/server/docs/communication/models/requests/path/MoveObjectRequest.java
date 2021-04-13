package com.tntu.server.docs.communication.models.requests.path;

import javax.validation.constraints.NotBlank;

public class MoveObjectRequest {

    @NotBlank
    private String location;
    @NotBlank
    private String locationTo;

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getLocationTo() {
        return locationTo;
    }

    public void setLocationTo(String locationTo) {
        this.locationTo = locationTo;
    }
}
