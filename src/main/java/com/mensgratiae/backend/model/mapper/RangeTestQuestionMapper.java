package com.mensgratiae.backend.model.mapper;

import com.mensgratiae.backend.dto.RangeTestQuestionDto;
import com.mensgratiae.backend.model.RangeTestQuestion;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface RangeTestQuestionMapper {

    RangeTestQuestionMapper INSTANCE = Mappers.getMapper(RangeTestQuestionMapper.class);

    @Mapping(target="testId", source="question.test.id")
    RangeTestQuestionDto questionToQuestionDto(RangeTestQuestion question);

    RangeTestQuestion questionDtoToQuestion(RangeTestQuestionDto questionDto);
}
