package com.mensgratiae.backend.model.mapper;

import com.mensgratiae.backend.dto.RangeTestQuestionAnswerDto;
import com.mensgratiae.backend.dto.RangeTestQuestionDto;
import com.mensgratiae.backend.model.RangeTestQuestion;
import com.mensgratiae.backend.model.RangeTestQuestionAnswer;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface RangeTestQuestionMapper {

    RangeTestQuestionMapper INSTANCE = Mappers.getMapper(RangeTestQuestionMapper.class);

    @Mapping(target="testId", source="question.test.id")
    RangeTestQuestionDto questionToQuestionDto(RangeTestQuestion question);

    RangeTestQuestion questionDtoToQuestion(RangeTestQuestionDto questionDto);

    @Mapping(target="questionId", source="answer.question.id")
    RangeTestQuestionAnswerDto questionAnswerToQuestionAnswerDto(
            RangeTestQuestionAnswer answer
    );

    RangeTestQuestionAnswer questionAnswerDtoToQuestionAnswer(
            RangeTestQuestionAnswerDto answerDto
    );
}
