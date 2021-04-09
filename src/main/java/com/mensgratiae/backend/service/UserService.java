package com.mensgratiae.backend.service;

import com.mensgratiae.backend.dto.UserDto;
import com.mensgratiae.backend.dto.inputs.UserAuthenticationInput;
import com.mensgratiae.backend.dto.outputs.UserAuthenticationOutput;
import com.mensgratiae.backend.dto.outputs.UserLoginOutput;
import com.mensgratiae.backend.dto.outputs.UserSignUpOutput;

public interface UserService {
    UserSignUpOutput signUp(UserDto userDTO);

    UserLoginOutput login(String username, String password);

    UserAuthenticationOutput userAuthenticate(UserAuthenticationInput userAuthenticationInput);
}
