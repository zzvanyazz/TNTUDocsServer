package com.tntu.server.docs.communication.models.requests.documents;

import javax.validation.constraints.NotBlank;

public class FindFileRequest {

    @NotBlank
    private String filePart;
    private String location;
    private Boolean isPrivate;

    public String getFilePart() {
        return filePart;
    }

    public void setFilePart(String filePart) {
        this.filePart = filePart;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public Boolean isPrivate() {
        return isPrivate;
    }

    public void setPrivate(Boolean aPrivate) {
        isPrivate = aPrivate;
    }
}