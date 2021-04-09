package com.mensgratiae.backend.model.mapper;

import com.mensgratiae.backend.dto.UserDto;
import com.mensgratiae.backend.model.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Mapper
public abstract class UserMapper {

    public static UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);

    @Autowired
    protected BCryptPasswordEncoder bCryptPasswordEncoder;

    @Mappings({
            @Mapping(target = "password", expression = "java(bCryptPasswordEncoder.encode(userDto.getPassword()))")
    })
    public abstract User userDtoToUser(UserDto userDto);
}
