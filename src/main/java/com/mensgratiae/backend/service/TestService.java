package com.mensgratiae.backend.service;

import com.mensgratiae.backend.dto.*;

import java.util.List;

public interface TestService {
    TestsGetOutput getTests(long researchId);

    TestGetOutput getTest(long testId);

    AddOrUpdateTestOutput addOrUpdateTest(TestDto testDto, boolean isAdd);

    AddOrUpdateRangeTestQuestionOutput addOrUpdateRangeTestQuestion(RangeTestQuestionDto questionDto, boolean isAdd);

    BasicOutput deleteTest(long id);

    BasicOutput deleteRangeTestQuestion(long id);

    BasicOutput addRangeTestQuestionAnswers(List<RangeTestQuestionAnswerDto> answersDto);

    RangeTestQuestionAnswersGetOutput getRangeTestQuestionAnswers(long testId);
}
