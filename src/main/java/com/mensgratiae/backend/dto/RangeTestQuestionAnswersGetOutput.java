package com.mensgratiae.backend.dto;

import com.mensgratiae.backend.model.RangeTestQuestionAnswer;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RangeTestQuestionAnswersGetOutput extends BasicOutput {
    private Map<Long, List<RangeTestQuestionAnswer>> answers = new HashMap<>();
}
