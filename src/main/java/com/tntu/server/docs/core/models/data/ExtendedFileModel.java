package com.tntu.server.docs.core.models.data;

import java.time.OffsetDateTime;

public class ExtendedFileModel extends FileModel {

    public String location;
    public boolean isPrivate;

    public ExtendedFileModel(FileModel fileModel, String location, boolean isPrivate) {
        super(fileModel.getName(), fileModel.getCreationDate());
        this.location = location;
        this.isPrivate = isPrivate;
    }


    public ExtendedFileModel(String location, String name, OffsetDateTime creationDate, boolean isPrivate) {
        super(name, creationDate);
        this.location = location;
        this.isPrivate = isPrivate;
    }

    public boolean isPrivate() {
        return isPrivate;
    }

    public void setPrivate(boolean isPrivate) {
        this.isPrivate = isPrivate;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }
}
