package com.mensgratiae.backend.model.mapper;

import com.mensgratiae.backend.dto.TestDto;
import com.mensgratiae.backend.model.Test;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface TestMapper {
    TestMapper INSTANCE = Mappers.getMapper(TestMapper.class);

    @Mapping(target="researchId", source="test.research.id")
    TestDto testToTestDto(Test test);
}
