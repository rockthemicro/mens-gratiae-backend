package com.mensgratiae.backend.dto.outputs;

import com.mensgratiae.backend.dto.ResearchDto;
import lombok.*;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ResearchesGetOutput extends BasicOutput {
    private List<ResearchDto> researches;
}
