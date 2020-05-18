package com.mensgratiae.backend.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Research {

    @Id
    private long id;

    @Column
    private String title;

    @Column
    private String shortDesc;

    @Column
    private String fullDesc;

    @Column
    private LanguageEnum language;

    public enum LanguageEnum {
        ENG, ITA, ROM
    }
}
