package com.mensgratiae.backend.model.mapper;

import com.mensgratiae.backend.dto.UserDto;
import com.mensgratiae.backend.model.User;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface UserMapper {

    UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);

    User userDtoToUser(UserDto userDto);
}
