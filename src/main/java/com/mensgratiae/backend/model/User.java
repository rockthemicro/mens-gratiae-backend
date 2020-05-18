package com.mensgratiae.backend.model;

import lombok.Data;

import javax.persistence.*;

@Entity
@Data
public class User {

    @Id
    @Column
    private String username;

    @Column
    private String password;

    @Column
    private String name;

    @Column
    private String email;
}
