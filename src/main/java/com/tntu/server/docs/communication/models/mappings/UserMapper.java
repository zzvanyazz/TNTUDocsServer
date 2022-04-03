package com.tntu.server.docs.communication.models.mappings;

import com.tntu.server.docs.communication.models.dto.UserDto;
import com.tntu.server.docs.core.data.models.user.UserModel;

public class UserMapper {

    public static UserDto toDto(UserModel userModel) {
        UserDto userDto = new UserDto();
        userDto.setId(userModel.getId());
        userDto.setUsername(userModel.getUsername());
        userDto.setEmail(userModel.getEmail());
        userDto.setDeleteTimestamp(userModel.getDeleteTimestamp());
        return userDto;
    }

    public static UserModel toEntity(UserDto userDto) {
        UserModel userModel = new UserModel();
        userModel.setId(userDto.getId());
        userModel.setUsername(userDto.getUsername());
        userModel.setEmail(userDto.getEmail());
        userModel.setDeleteTimestamp(userDto.getDeleteTimestamp());
        return userModel;
    }

}
