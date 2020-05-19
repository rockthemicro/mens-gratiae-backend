package com.mensgratiae.backend.service;

import com.mensgratiae.backend.dto.BasicOutput;
import com.mensgratiae.backend.dto.TestGetOutput;
import com.mensgratiae.backend.dto.TestsGetOutput;
import com.mensgratiae.backend.model.RangeTestQuestion;
import com.mensgratiae.backend.model.Test;
import com.mensgratiae.backend.model.mapper.RangeTestQuestionMapper;
import com.mensgratiae.backend.model.mapper.TestMapper;
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
}
