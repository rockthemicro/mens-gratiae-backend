package com.mensgratiae.backend.service;

import com.mensgratiae.backend.dto.UserDto;
import com.mensgratiae.backend.dto.UserSignUpOutput;

public interface UserService {
    public UserSignUpOutput signUp(UserDto userDTO);
}
