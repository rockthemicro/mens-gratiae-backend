package com.mensgratiae.backend.dto;

import com.mensgratiae.backend.model.GenericResearchQuestionAnswer;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GenericResearchQuestionAnswersGetOutput extends BasicOutput {
    private Map<Long, List<GenericResearchQuestionAnswer>> answers = new HashMap<>();
}
