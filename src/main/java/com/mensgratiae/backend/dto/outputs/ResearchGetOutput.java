package com.mensgratiae.backend.dto.outputs;

import com.mensgratiae.backend.dto.GenericResearchQuestionDto;
import com.mensgratiae.backend.dto.ResearchDto;
import com.mensgratiae.backend.dto.TestDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ResearchGetOutput extends BasicOutput {
    private ResearchDto research;
    private List<GenericResearchQuestionDto> genericResearchQuestions;
    private List<TestDto> tests;
}
