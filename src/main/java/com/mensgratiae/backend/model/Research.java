package com.mensgratiae.backend.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Type;

import javax.persistence.*;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Research {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column
    private String title;

    @Column
    @Type(type="text")
    private String shortDesc;

    @Column
    @Type(type="text")
    private String fullDesc;

    @Column
    private LanguageEnum language;

    public enum LanguageEnum {
        ENG, ITA, ROM
    }
}
