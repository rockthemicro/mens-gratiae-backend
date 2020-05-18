package com.mensgratiae.backend.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class ResearchesGetOutput extends BasicOutput {
    private List<ResearchDto> researches;
}
