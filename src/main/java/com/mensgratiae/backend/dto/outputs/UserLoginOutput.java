package com.mensgratiae.backend.dto.outputs;

import lombok.Data;

@Data
public class UserLoginOutput extends BasicOutput {

    private String name;
    private String email;
}
