package com.mensgratiae.backend.dto;

import lombok.*;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ResearchesGetOutput extends BasicOutput {
    private List<ResearchDto> researches;
}
