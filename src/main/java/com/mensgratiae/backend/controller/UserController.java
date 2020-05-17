package com.mensgratiae.backend.controller;

import com.mensgratiae.backend.dto.UserDTO;
import com.mensgratiae.backend.model.User;
import com.mensgratiae.backend.model.UserDetailsImpl;
import com.mensgratiae.backend.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
@CrossOrigin
@AllArgsConstructor
public class UserController {
    private UserRepository userRepository;
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @GetMapping("getUsername")
    public String getUsername() {
        // fiecare request http este procesat in propriul sau thread
        // => datele de securitate sunt puse pe thread
        UserDetailsImpl user = (UserDetailsImpl) SecurityContextHolder
                .getContext().getAuthentication().getPrincipal();
        return user.getUsername();
    }

    @PostMapping("sign-up")
    public void signUp(@RequestBody UserDTO userDTO) {
        User user = new User();

        user.setPassword(bCryptPasswordEncoder.encode(userDTO.getPassword()));
        //user.setPassword(userDTO.getPassword());
        user.setUsername(userDTO.getUsername());
        user.setName(userDTO.getName());
        user.setRole(User.ROLE.USER);

        userRepository.save(user);
    }

}
