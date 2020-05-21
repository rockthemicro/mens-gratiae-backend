package com.mensgratiae.backend.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RangeTestQuestionAnswerDto {
    private long id;
    private Integer answer;
    private long questionId;
}
