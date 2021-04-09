package com.mensgratiae.backend.dto.outputs;

import com.mensgratiae.backend.dto.RangeTestQuestionDto;
import com.mensgratiae.backend.dto.TestDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TestGetOutput extends BasicOutput {
    private TestDto test;
    private List<RangeTestQuestionDto> rangeTestQuestions;
}
