package com.mensgratiae.backend.service;

import com.mensgratiae.backend.dto.AddOrUpdateTestOutput;
import com.mensgratiae.backend.dto.TestDto;
import com.mensgratiae.backend.dto.TestGetOutput;
import com.mensgratiae.backend.dto.TestsGetOutput;

public interface TestService {
    TestsGetOutput getTests(long researchId);

    TestGetOutput getTest(long testId);

    AddOrUpdateTestOutput addOrUpdateTest(TestDto testDto, boolean isAdd);
}
