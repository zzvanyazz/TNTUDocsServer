package com.tntu.server.docs.core.data.models.file;

public class FindFileModel {

    private String filePart;
    private String location;
    private Boolean isPrivate;

    public FindFileModel() {
    }

    public FindFileModel(FindFileModel findFileModel) {
        setFilePart(findFileModel.getFilePart());
        setLocation(findFileModel.getLocation());
        setPrivate(findFileModel.isPrivate());
    }

    public FindFileModel(String filePart, String location, Boolean isPrivate) {
        this.filePart = filePart;
        this.location = location;
        this.isPrivate = isPrivate;
    }

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

    public void addChild(String childPart) {
        this.location = location + "/" + childPart;
    }

    public Boolean isPrivate() {
        return isPrivate;
    }

    public void setPrivate(Boolean aPrivate) {
        isPrivate = aPrivate;
    }
}

