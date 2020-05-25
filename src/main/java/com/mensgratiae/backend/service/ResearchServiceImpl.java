package com.mensgratiae.backend.service;

import com.mensgratiae.backend.dto.*;
import com.mensgratiae.backend.model.GenericResearchQuestion;
import com.mensgratiae.backend.model.GenericResearchQuestionAnswer;
import com.mensgratiae.backend.model.Research;
import com.mensgratiae.backend.model.Test;
import com.mensgratiae.backend.model.mapper.GenericResearchQuestionMapper;
import com.mensgratiae.backend.model.mapper.ResearchMapper;
import com.mensgratiae.backend.model.mapper.TestMapper;
import com.mensgratiae.backend.repository.GenericResearchQuestionAnswerRepository;
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
    private GenericResearchQuestionAnswerRepository genericResearchQuestionAnswerRepository;

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

    @Override
    public AddOrUpdateResearchOutput addOrUpdateResearch(ResearchDto researchDto, boolean isAdd) {
        AddOrUpdateResearchOutput addOrUpdateResearchOutput = new AddOrUpdateResearchOutput();

        Research research = ResearchMapper.INSTANCE.researchDtoToResearch(researchDto);
        if (isAdd) {
            /* if we want to add, then remove the id so that .save() will generate a new one */
            research.setId(0);
        }

        research = researchRepository.save(research);
        addOrUpdateResearchOutput.setResearchId(research.getId());

        return addOrUpdateResearchOutput;
    }

    @Override
    public AddOrUpdateGenericResearchQuestionOutput addOrUpdateGenericResearchQuestion(
            GenericResearchQuestionDto questionDto, boolean isAdd) {

        AddOrUpdateGenericResearchQuestionOutput output = new AddOrUpdateGenericResearchQuestionOutput();

        GenericResearchQuestion question = GenericResearchQuestionMapper.INSTANCE.questionDtoToQuestion(questionDto);
        question.setResearch(new Research());
        question.getResearch().setId(questionDto.getResearchId());

        if (isAdd) {
            question.setId(0);
        }

        question = genericResearchQuestionRepository.save(question);
        output.setGenericResearchQuestionId(question.getId());

        return output;
    }

    @Override
    public BasicOutput deleteResearch(long id) {
        researchRepository.deleteById(id);

        return new BasicOutput();
    }

    @Override
    public BasicOutput deleteGenericResearchQuestion(long id) {
        genericResearchQuestionRepository.deleteById(id);

        return new BasicOutput();
    }

//    @Override
//    public BasicOutput addGenericResearchQuestionAnswers(List<GenericResearchQuestionAnswerDto> answersDto) {
//        List<GenericResearchQuestionAnswer> answers = answersDto
//                .stream()
//                .map(answerDto -> {
//                    GenericResearchQuestionAnswer answer =  GenericResearchQuestionMapper
//                            .INSTANCE
//                            .questionAnswerDtoToQuestionAnswer(answerDto);
//
//                    answer.setQuestion(new GenericResearchQuestion());
//                    answer.getQuestion().setId(answerDto.getQuestionId());
//
//                    return answer;
//                })
//                .collect(Collectors.toList());
//
//        answers.forEach(genericResearchQuestionAnswerRepository::save);
//
//        return new BasicOutput();
//    }
//
//    @Override
//    public GenericResearchQuestionAnswersGetOutput getGenericResearchQuestionAnswers(
//            long researchId) {
//
//        GenericResearchQuestionAnswersGetOutput output =
//                new GenericResearchQuestionAnswersGetOutput();
//
//        List<GenericResearchQuestion> genericResearchQuestions =
//                genericResearchQuestionRepository.findAllByResearch_Id(researchId);
//
//        genericResearchQuestions.forEach(question -> {
//            List<GenericResearchQuestionAnswer> answers = genericResearchQuestionAnswerRepository
//                    .findAllByQuestion_Id(question.getId());
//
//            output.getAnswers().put(question.getId(), answers);
//        });
//
//        return output;
//    }
}
