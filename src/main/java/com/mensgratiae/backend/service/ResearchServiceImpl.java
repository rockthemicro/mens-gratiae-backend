package com.mensgratiae.backend.service;

import com.mensgratiae.backend.dto.*;
import com.mensgratiae.backend.model.*;
import com.mensgratiae.backend.model.mapper.GenericResearchQuestionMapper;
import com.mensgratiae.backend.model.mapper.ResearchMapper;
import com.mensgratiae.backend.model.mapper.TestMapper;
import com.mensgratiae.backend.repository.*;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
    private RangeTestQuestionAnswerRepository rangeTestQuestionAnswerRepository;
    private ResearchSubmissionRepository researchSubmissionRepository;
    private TestSubmissionRepository testSubmissionRepository;

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

    @Transactional
    @Override
    public BasicOutput addSubmission(AddSubmissionInput submission) {
        ResearchSubmission researchSubmission = new ResearchSubmission();

        researchSubmission.setId(0);
        researchSubmission.setResearch(new Research());
        researchSubmission.getResearch().setId(submission.getResearchId());

        ResearchSubmission finalResearchSubmission = researchSubmissionRepository.save(researchSubmission);

        submission.getGenericResearchQuestionAnswers().forEach((questionId, answers) -> {
            for (String answer : answers) {
                GenericResearchQuestionAnswer genericResearchQuestionAnswer =
                        new GenericResearchQuestionAnswer();

                genericResearchQuestionAnswer.setId(0);
                genericResearchQuestionAnswer.setAnswer(answer);

                genericResearchQuestionAnswer.setResearchSubmission(new ResearchSubmission());
                genericResearchQuestionAnswer
                        .getResearchSubmission()
                        .setId(finalResearchSubmission.getId());

                genericResearchQuestionAnswer.setQuestion(new GenericResearchQuestion());
                genericResearchQuestionAnswer
                        .getQuestion()
                        .setId(questionId);

                genericResearchQuestionAnswerRepository
                        .save(genericResearchQuestionAnswer);
            }
        });

        submission.getRangeTestQuestionAnswers().forEach((testId, answers) -> {
            TestSubmission testSubmission = new TestSubmission();

            testSubmission.setId(0);

            testSubmission.setTest(new Test());
            testSubmission.getTest().setId(testId);

            testSubmission.setResearchSubmission(new ResearchSubmission());
            testSubmission
                    .getResearchSubmission()
                    .setId(finalResearchSubmission.getId());

            TestSubmission finalTestSubmission = testSubmissionRepository.save(testSubmission);

            answers.forEach((questionId, answer) -> {
                RangeTestQuestionAnswer rangeTestQuestionAnswer = new RangeTestQuestionAnswer();

                rangeTestQuestionAnswer.setId(0);
                rangeTestQuestionAnswer.setAnswer(answer);

                rangeTestQuestionAnswer.setTestSubmission(new TestSubmission());
                rangeTestQuestionAnswer
                        .getTestSubmission()
                        .setId(finalTestSubmission.getId());

                rangeTestQuestionAnswer.setQuestion(new RangeTestQuestion());
                rangeTestQuestionAnswer
                        .getQuestion()
                        .setId(questionId);

                rangeTestQuestionAnswerRepository
                        .save(rangeTestQuestionAnswer);
            });
        });

        return new BasicOutput();
    }

    @Override
    public BasicOutput deleteSubmission(long id) {
        researchSubmissionRepository.deleteById(id);

        return new BasicOutput();
    }
}
