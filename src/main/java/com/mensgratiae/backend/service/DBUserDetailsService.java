package com.mensgratiae.backend.service;

import com.mensgratiae.backend.model.UserDetailsImpl;
import com.mensgratiae.backend.model.User;
import com.mensgratiae.backend.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.Optional;

@Component
public class DBUserDetailsService implements UserDetailsService {

    @Autowired
    UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> userOpt = userRepository.findById(username);
        User user = userOpt.orElseThrow(() -> new UsernameNotFoundException(username));

        return new UserDetailsImpl(username, user.getName(), Arrays.asList("USER"));
    }
}
