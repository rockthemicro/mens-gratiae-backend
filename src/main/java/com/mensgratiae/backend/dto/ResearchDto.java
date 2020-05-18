package com.mensgratiae.backend.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ResearchDto {
    private long id;
    private String title;
    private String shortDesc;
    private String fullDesc;
    private LanguageEnum language;

    public enum LanguageEnum {
        ENG, ITA, ROM
    }
}
