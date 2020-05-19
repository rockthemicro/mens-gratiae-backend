package com.mensgratiae.backend.model.mapper;

import com.mensgratiae.backend.dto.GenericResearchQuestionDto;
import com.mensgratiae.backend.model.GenericResearchQuestion;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface GenericResearchQuestionMapper {
    GenericResearchQuestionMapper INSTANCE =
            Mappers.getMapper(GenericResearchQuestionMapper.class);

    @Mapping(target="researchId", source="question.research.id")
    GenericResearchQuestionDto questionToQuestionDto(GenericResearchQuestion question);
}
