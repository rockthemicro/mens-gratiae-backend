package com.mensgratiae.backend.controller;

import com.mensgratiae.backend.dto.UserDto;
import com.mensgratiae.backend.dto.UserLoginOutput;
import com.mensgratiae.backend.dto.UserSignUpOutput;
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
    public ResponseEntity<UserLoginOutput> login(@RequestParam String username,
                                                 @RequestParam String password) {

        UserLoginOutput userLoginOutput = userService.login(username, password);

        return ResponseEntity
                .ok()
                .body(userLoginOutput);
    }
}
