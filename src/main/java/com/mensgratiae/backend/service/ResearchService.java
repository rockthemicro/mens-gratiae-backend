package com.mensgratiae.backend.service;

import com.mensgratiae.backend.dto.*;
import com.mensgratiae.backend.dto.inputs.AddSubmissionInput;
import com.mensgratiae.backend.dto.outputs.AddOrUpdateGenericResearchQuestionOutput;
import com.mensgratiae.backend.dto.outputs.AddOrUpdateResearchOutput;
import com.mensgratiae.backend.dto.outputs.BasicOutput;
import com.mensgratiae.backend.dto.outputs.ResearchGetOutput;
import com.mensgratiae.backend.dto.outputs.ResearchesGetOutput;

import java.io.File;

public interface ResearchService {
    ResearchesGetOutput getResearches();

    ResearchGetOutput getResearch(long id);

    AddOrUpdateResearchOutput addOrUpdateResearch(ResearchDto researchDto, boolean isAdd);

    AddOrUpdateGenericResearchQuestionOutput addOrUpdateGenericResearchQuestion(GenericResearchQuestionDto questionDto,
                                                                                boolean isAdd);

    BasicOutput deleteResearch(long id);

    BasicOutput deleteGenericResearchQuestion(long id);

    BasicOutput addSubmission(AddSubmissionInput submission);

    BasicOutput deleteSubmission(long submissionId);

    File getSubmissions(long researchId);
}
