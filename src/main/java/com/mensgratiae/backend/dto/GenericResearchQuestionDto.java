package com.mensgratiae.backend.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GenericResearchQuestionDto {
    private long id;
    private long researchId;
    private String question;
    private QuestionType type;
    private int numberOfOptions;
    List<String> options;
    private int relativePosition;

    public enum QuestionType {
        YES_NO, RANGE, TEXT, SINGLE_CHOICE, MULTIPLE_CHOICE
    }
}
