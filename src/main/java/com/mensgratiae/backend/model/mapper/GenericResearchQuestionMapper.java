package com.mensgratiae.backend.model.mapper;

import com.mensgratiae.backend.dto.GenericResearchQuestionAnswerDto;
import com.mensgratiae.backend.dto.GenericResearchQuestionDto;
import com.mensgratiae.backend.model.GenericResearchQuestion;
import com.mensgratiae.backend.model.GenericResearchQuestionAnswer;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface GenericResearchQuestionMapper {
    GenericResearchQuestionMapper INSTANCE =
            Mappers.getMapper(GenericResearchQuestionMapper.class);

    @Mapping(target="researchId", source="question.research.id")
    GenericResearchQuestionDto questionToQuestionDto(GenericResearchQuestion question);

    GenericResearchQuestion questionDtoToQuestion(GenericResearchQuestionDto questionDto);

    @Mapping(target="questionId", source="answer.question.id")
    GenericResearchQuestionAnswerDto questionAnswerToQuestionAnswerDto(
            GenericResearchQuestionAnswer answer
    );

    GenericResearchQuestionAnswer questionAnswerDtoToQuestionAnswer(
            GenericResearchQuestionAnswerDto answerDto
    );
}
