package com.mensgratiae.backend.controller;

import com.mensgratiae.backend.dto.UserDto;
import com.mensgratiae.backend.dto.inputs.UserAuthenticationInput;
import com.mensgratiae.backend.dto.outputs.UserAuthenticationOutput;
import com.mensgratiae.backend.dto.outputs.UserLoginOutput;
import com.mensgratiae.backend.dto.outputs.UserSignUpOutput;
import com.mensgratiae.backend.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/users")
@CrossOrigin
@AllArgsConstructor
public class UsersController {

    private UserService userService;

    @PostMapping("/sign-up")
    public ResponseEntity<UserSignUpOutput> signUp(@RequestBody @Valid UserDto userDTO) {
        UserSignUpOutput userSignUpOutput = userService.signUp(userDTO);

        return ResponseEntity
                .ok()
                .body(userSignUpOutput);
    }

    @GetMapping("/login")
    public ResponseEntity<UserLoginOutput> login(@RequestParam @Valid String username,
                                                 @RequestParam @Valid String password) {

        UserLoginOutput userLoginOutput = userService.login(username, password);

        return ResponseEntity
                .ok()
                .body(userLoginOutput);
    }

    @RequestMapping(value = "/authenticate", method = RequestMethod.POST)
    public ResponseEntity<UserAuthenticationOutput> createAuthenticationToken(
            @RequestBody UserAuthenticationInput authenticationRequest) {

        UserAuthenticationOutput userAuthenticationOutput = userService.userAuthenticate(authenticationRequest);

        return ResponseEntity
                .ok()
                .body(userAuthenticationOutput);
    }
}
