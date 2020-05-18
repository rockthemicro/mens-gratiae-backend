package com.mensgratiae.backend.service;

import com.mensgratiae.backend.dto.UserDto;
import com.mensgratiae.backend.dto.UserSignUpOutput;
import com.mensgratiae.backend.model.User;
import com.mensgratiae.backend.model.mapper.UserMapper;
import com.mensgratiae.backend.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService {

    private UserRepository userRepository;

    public UserSignUpOutput signUp(UserDto userDto) {
        UserSignUpOutput userSignUpOutput = new UserSignUpOutput();

        Optional<User> userOpt = userRepository.findById(userDto.getUsername());
        if (userOpt.isPresent()) {
            userSignUpOutput.setStatus(UserSignUpOutput.StatusEnum.ERROR);
            userSignUpOutput.getErrors().add("User is already present");

            return userSignUpOutput;
        }

        User user = UserMapper.INSTANCE.userDtoToUser(userDto);
        user.setRole(User.RoleEnum.USER);
        userRepository.save(user);

        return userSignUpOutput;
    }
}
