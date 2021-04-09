package com.mensgratiae.backend.dto.inputs;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Map;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AddSubmissionInput {

    private long researchId;

    /**
     * Map<questionId, List<answer>>
     */
    private Map<Long, List<String>> genericResearchQuestionAnswers;

    /**
     * Map<testId, Map<questionId, answer>>
     */
    private Map<Long, Map<Long, Integer>> rangeTestQuestionAnswers;
}
