package com.mensgratiae.backend.service;

import com.mensgratiae.backend.dto.*;
import com.mensgratiae.backend.model.*;
import com.mensgratiae.backend.model.mapper.GenericResearchQuestionMapper;
import com.mensgratiae.backend.model.mapper.RangeTestQuestionMapper;
import com.mensgratiae.backend.model.mapper.TestMapper;
import com.mensgratiae.backend.repository.RangeTestQuestionAnswerRepository;
import com.mensgratiae.backend.repository.RangeTestQuestionRepository;
import com.mensgratiae.backend.repository.TestRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class TestServiceImpl implements TestService {

    private TestRepository testRepository;
    private RangeTestQuestionRepository rangeTestQuestionRepository;
    private RangeTestQuestionAnswerRepository rangeTestQuestionAnswerRepository;

    @Override
    public TestsGetOutput getTests(long researchId) {
        TestsGetOutput testsGetOutput = new TestsGetOutput();

        List<Test> tests = testRepository.findAllByResearch_Id(researchId);
        testsGetOutput.setTests(tests
                .stream()
                .map(TestMapper.INSTANCE::testToTestDto)
                .collect(Collectors.toList()));

        return testsGetOutput;
    }

    @Override
    public TestGetOutput getTest(long testId) {
        TestGetOutput testGetOutput = new TestGetOutput();

        Optional<Test> testOpt = testRepository.findById(testId);
        if (testOpt.isEmpty()) {
            testGetOutput.setStatus(BasicOutput.StatusEnum.ERROR);
            testGetOutput.addErrorsItem("Test Id not found");

            return testGetOutput;
        }

        Test test = testOpt.get();
        testGetOutput.setTest(TestMapper.INSTANCE.testToTestDto(test));

        List<RangeTestQuestion> questions = rangeTestQuestionRepository.findAllByTest_Id(testId);
        testGetOutput.setRangeTestQuestions(questions
                .stream()
                .map(RangeTestQuestionMapper.INSTANCE::questionToQuestionDto)
                .collect(Collectors.toList()));

        return testGetOutput;
    }

    @Override
    public AddOrUpdateTestOutput addOrUpdateTest(TestDto testDto, boolean isAdd) {
        AddOrUpdateTestOutput addOrUpdateTestOutput = new AddOrUpdateTestOutput();

        Test test = TestMapper.INSTANCE.testDtoToTest(testDto);
        test.setResearch(new Research());
        test.getResearch().setId(testDto.getResearchId());

        if (isAdd) {
            test.setId(0);
        }

        test = testRepository.save(test);
        addOrUpdateTestOutput.setTestId(test.getId());

        return addOrUpdateTestOutput;
    }

    @Override
    public AddOrUpdateRangeTestQuestionOutput addOrUpdateRangeTestQuestion(RangeTestQuestionDto questionDto,
                                                                           boolean isAdd) {
        AddOrUpdateRangeTestQuestionOutput output = new AddOrUpdateRangeTestQuestionOutput();

        RangeTestQuestion question = RangeTestQuestionMapper.INSTANCE.questionDtoToQuestion(questionDto);
        question.setTest(new Test());
        question.getTest().setId(questionDto.getTestId());

        if (isAdd) {
            question.setId(0);
        }

        question = rangeTestQuestionRepository.save(question);
        output.setRangeTestQuestionId(question.getId());

        return output;
    }

    @Override
    public BasicOutput deleteTest(long id) {
        testRepository.deleteById(id);

        return new BasicOutput();
    }

    @Override
    public BasicOutput deleteRangeTestQuestion(long id) {
        rangeTestQuestionRepository.deleteById(id);

        return new BasicOutput();
    }

//    @Override
//    public BasicOutput addRangeTestQuestionAnswers(List<RangeTestQuestionAnswerDto> answersDto) {
//        List<RangeTestQuestionAnswer> answers = answersDto
//                .stream()
//                .map(answerDto -> {
//                    RangeTestQuestionAnswer answer =  RangeTestQuestionMapper
//                            .INSTANCE
//                            .questionAnswerDtoToQuestionAnswer(answerDto);
//
//                    answer.setQuestion(new RangeTestQuestion());
//                    answer.getQuestion().setId(answerDto.getQuestionId());
//
//                    return answer;
//                })
//                .collect(Collectors.toList());
//
//        answers.forEach(rangeTestQuestionAnswerRepository::save);
//
//        return new BasicOutput();
//    }
//
//    @Override
//    public RangeTestQuestionAnswersGetOutput getRangeTestQuestionAnswers(long testId) {
//        RangeTestQuestionAnswersGetOutput output =
//                new RangeTestQuestionAnswersGetOutput();
//
//        List<RangeTestQuestion> rangeTestQuestions =
//                rangeTestQuestionRepository.findAllByTest_Id(testId);
//
//        rangeTestQuestions.forEach(question -> {
//            List<RangeTestQuestionAnswer> answers = rangeTestQuestionAnswerRepository
//                    .findAllByQuestion_Id(question.getId());
//
//            output.getAnswers().put(question.getId(), answers);
//        });
//
//        return output;
//    }
}
