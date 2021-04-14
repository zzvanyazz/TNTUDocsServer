package com.tntu.server.docs.core.data.models.file;

import java.time.OffsetDateTime;

public class ExtendedFileModel extends FileModel {

    public String location;

    public ExtendedFileModel(FileModel fileModel, String location) {
        super(fileModel.getName(), fileModel.getCreationDate());
        this.location = location;
    }


    public ExtendedFileModel(String location, String name, OffsetDateTime creationDate) {
        super(name, creationDate);
        this.location = location;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }
}
