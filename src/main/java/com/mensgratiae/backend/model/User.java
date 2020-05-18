package com.mensgratiae.backend.model;

import lombok.Data;
import lombok.Getter;
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

    @Column
    private RoleEnum role;

    @Getter
    public enum RoleEnum {
        USER ("USER"),
        ADMIN ("ADMIN"),
        ;

        private final String role;

        RoleEnum(String role) {
            this.role = role;
        }
    }
}
