package com.mensgratiae.backend.service;

import com.mensgratiae.backend.dto.UserDto;
import com.mensgratiae.backend.dto.UserLoginOutput;
import com.mensgratiae.backend.dto.UserSignUpOutput;

public interface UserService {
    UserSignUpOutput signUp(UserDto userDTO);

    UserLoginOutput login(String username, String password);
}
