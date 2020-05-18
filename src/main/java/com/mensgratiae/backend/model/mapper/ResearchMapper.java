package com.mensgratiae.backend.model.mapper;

import com.mensgratiae.backend.dto.ResearchDto;
import com.mensgratiae.backend.model.Research;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface ResearchMapper {
    ResearchMapper INSTANCE = Mappers.getMapper(ResearchMapper.class);

    ResearchDto researchToResearchDto(Research research);
}
