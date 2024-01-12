package com.tntu.server.docs.core.models.data;

import java.util.List;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
@AllArgsConstructor
public class FolderModel {

    String name;
    List<FolderModel> folders;
    List<String> fileNames;

}