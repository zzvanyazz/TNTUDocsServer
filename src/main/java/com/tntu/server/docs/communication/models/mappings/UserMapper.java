package com.tntu.server.docs.communication.models.mappings;

import com.tntu.server.docs.communication.models.dto.UserDto;
import com.tntu.server.docs.core.models.data.UserModel;

public class UserMapper {

    public static UserDto toDto(UserModel userModel) {
        UserDto userDto = new UserDto();
        userDto.setId(userModel.getId());
        userDto.setUsername(userModel.getUsername());
        userDto.setNormalizedUsername(userModel.getNormalizedUsername());
        userDto.setEnabled(userModel.isEnabled());
        userDto.setEmail(userModel.getEmail());
        userDto.setDeleteTimestamp(userModel.getDeleteTimestamp());
        userDto.setValidTokenTimestamp(userModel.getValidTokenTimestamp());
        return userDto;
    }

    public static UserModel toEntity(UserDto userDto) {
        UserModel userModel = new UserModel();
        userModel.setId(userDto.getId());
        userModel.setUsername(userDto.getUsername());
        userModel.setNormalizedUsername(userDto.getNormalizedUsername());
        userModel.setEnabled(userDto.isEnabled());
        userModel.setEmail(userDto.getEmail());
        userModel.setDeleteTimestamp(userDto.getDeleteTimestamp());
        userModel.setValidTokenTimestamp(userDto.getValidTokenTimestamp());
        return userModel;
    }

}
