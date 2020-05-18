package com.mensgratiae.backend.service;

import com.mensgratiae.backend.dto.ResearchDto;
import com.mensgratiae.backend.dto.ResearchesGetOutput;
import com.mensgratiae.backend.model.Research;
import com.mensgratiae.backend.model.mapper.ResearchMapper;
import com.mensgratiae.backend.repository.ResearchRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class ResearchServiceImpl implements ResearchService {

    ResearchRepository researchRepository;

    @Override
    public ResearchesGetOutput getResearches() {
        ResearchesGetOutput researchesGetOutput = new ResearchesGetOutput();
        List<Research> researchList = researchRepository.findAll();

        List<ResearchDto> researchDtoList = researchList
                .stream()
                .map(ResearchMapper.INSTANCE::researchToResearchDto)
                .collect(Collectors.toList());
        researchesGetOutput.setResearches(researchDtoList);

        return researchesGetOutput;
    }
}
