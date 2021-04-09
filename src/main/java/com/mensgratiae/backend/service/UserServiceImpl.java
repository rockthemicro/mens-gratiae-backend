package com.mensgratiae.backend.service;

import com.mensgratiae.backend.dto.inputs.UserAuthenticationInput;
import com.mensgratiae.backend.dto.outputs.BasicOutput;
import com.mensgratiae.backend.dto.UserDto;
import com.mensgratiae.backend.dto.outputs.UserAuthenticationOutput;
import com.mensgratiae.backend.dto.outputs.UserLoginOutput;
import com.mensgratiae.backend.dto.outputs.UserSignUpOutput;
import com.mensgratiae.backend.model.User;
import com.mensgratiae.backend.model.mapper.UserMapper;
import com.mensgratiae.backend.repository.UserRepository;
import com.mensgratiae.backend.util.JwtUtil;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtTokenUtil;
    private final DBUserDetailsService userDetailsService;

    public UserSignUpOutput signUp(UserDto userDto) {
        UserSignUpOutput userSignUpOutput = new UserSignUpOutput();

        Optional<User> userOpt = userRepository.findById(userDto.getUsername());
        if (userOpt.isPresent()) {
            userSignUpOutput.setStatus(UserSignUpOutput.StatusEnum.ERROR);
            userSignUpOutput.addErrorsItem("User is already present");

            return userSignUpOutput;
        }

        User user = UserMapper.INSTANCE.userDtoToUser(userDto);
        user.setRole(User.RoleEnum.USER);
        userRepository.save(user);

        return userSignUpOutput;
    }

    @Override
    public UserLoginOutput login(String username, String password) {
        UserLoginOutput userLoginOutput = new UserLoginOutput();

        Optional<User> userOpt = userRepository.findById(username);
        if (userOpt.isEmpty()) {
            userLoginOutput.setStatus(BasicOutput.StatusEnum.ERROR);
            userLoginOutput.addErrorsItem("Username not found");

            return userLoginOutput;
        }

        User user = userOpt.get();
        if (!user.getPassword().equals(password)) {
            userLoginOutput.setStatus(BasicOutput.StatusEnum.ERROR);
            userLoginOutput.addErrorsItem("Password is incorrect");

            return userLoginOutput;
        }

        userLoginOutput.setEmail(user.getEmail());
        userLoginOutput.setName(user.getName());

        return userLoginOutput;
    }

    @Override
    public UserAuthenticationOutput userAuthenticate(UserAuthenticationInput userAuthenticationInput) {
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(userAuthenticationInput.getUsername(), userAuthenticationInput.getPassword())
            );
        }
        catch (BadCredentialsException e) {
            throw new RuntimeException("Incorrect username or password", e);
        }


        final UserDetails userDetails = userDetailsService
                .loadUserByUsername(userAuthenticationInput.getUsername());

        final String jwt = jwtTokenUtil.generateToken(userDetails);

        return new UserAuthenticationOutput(jwt);
    }


}
