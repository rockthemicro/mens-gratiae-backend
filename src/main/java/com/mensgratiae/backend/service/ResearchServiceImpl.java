package com.mensgratiae.backend.service;

import com.mensgratiae.backend.dto.BasicOutput;
import com.mensgratiae.backend.dto.ResearchDto;
import com.mensgratiae.backend.dto.ResearchGetOutput;
import com.mensgratiae.backend.dto.ResearchesGetOutput;
import com.mensgratiae.backend.model.GenericResearchQuestion;
import com.mensgratiae.backend.model.Research;
import com.mensgratiae.backend.model.Test;
import com.mensgratiae.backend.model.mapper.GenericResearchQuestionMapper;
import com.mensgratiae.backend.model.mapper.ResearchMapper;
import com.mensgratiae.backend.model.mapper.TestMapper;
import com.mensgratiae.backend.repository.GenericResearchQuestionRepository;
import com.mensgratiae.backend.repository.ResearchRepository;
import com.mensgratiae.backend.repository.TestRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class ResearchServiceImpl implements ResearchService {

    private ResearchRepository researchRepository;
    private TestRepository testRepository;
    private GenericResearchQuestionRepository genericResearchQuestionRepository;

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

    @Override
    public ResearchGetOutput getResearch(long id) {
        ResearchGetOutput researchGetOutput = new ResearchGetOutput();

        Optional<Research> researchOpt = researchRepository.findById(id);
        if (researchOpt.isEmpty()) {
            researchGetOutput.setStatus(BasicOutput.StatusEnum.ERROR);
            researchGetOutput.addErrorsItem("Research Id not found");

            return researchGetOutput;
        }

        Research research = researchOpt.get();
        researchGetOutput.setResearch(ResearchMapper.INSTANCE.researchToResearchDto(research));

        List<Test> tests = testRepository.findAllByResearch_Id(id);
        researchGetOutput.setTests(tests
                .stream()
                .map(TestMapper.INSTANCE::testToTestDto)
                .collect(Collectors.toList()));

        List<GenericResearchQuestion> genericResearchQuestions =
                genericResearchQuestionRepository.findAllByResearch_Id(id);
        researchGetOutput.setGenericResearchQuestions(genericResearchQuestions
                .stream()
                .map(GenericResearchQuestionMapper.INSTANCE::questionToQuestionDto)
                .collect(Collectors.toList()));

        return researchGetOutput;
    }
}
