package com.mensgratiae.backend.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class User implements Serializable {
//
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private long id;

    public enum ROLE {
        USER, ADMIN
    }

    @Id
    private String username;

    @Column
    private String name;

    @Column
    private ROLE role;

    @Column
    private String password;
}
