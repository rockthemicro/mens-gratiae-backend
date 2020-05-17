package com.mensgratiae.backend.controller;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("test")
@CrossOrigin
@AllArgsConstructor
public class TestController {

    @GetMapping("ping")
    public String ping() {
        return "pong";
    }
}
