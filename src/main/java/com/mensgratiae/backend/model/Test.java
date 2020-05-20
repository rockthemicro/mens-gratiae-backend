package com.mensgratiae.backend.model;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Test {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToOne
    @JoinColumn(name = "researchId", referencedColumnName = "id")
    private Research research;

    @Column
    private String name;

    @Column
    private String description;

    @Column
    private int scale;

    @Column
    @ElementCollection(targetClass = String.class)
    private List<String> options;

    @Column
    private int relativePosition;
}
