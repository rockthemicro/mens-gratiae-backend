package com.mensgratiae.backend.service;

import com.mensgratiae.backend.dto.*;
import com.mensgratiae.backend.dto.outputs.AddOrUpdateRangeTestQuestionOutput;
import com.mensgratiae.backend.dto.outputs.AddOrUpdateTestOutput;
import com.mensgratiae.backend.dto.outputs.BasicOutput;
import com.mensgratiae.backend.dto.outputs.TestGetOutput;
import com.mensgratiae.backend.dto.outputs.TestsGetOutput;

public interface TestService {
    TestsGetOutput getTests(long researchId);

    TestGetOutput getTest(long testId);

    AddOrUpdateTestOutput addOrUpdateTest(TestDto testDto, boolean isAdd);

    AddOrUpdateRangeTestQuestionOutput addOrUpdateRangeTestQuestion(RangeTestQuestionDto questionDto, boolean isAdd);

    BasicOutput deleteTest(long id);

    BasicOutput deleteRangeTestQuestion(long id);
}
