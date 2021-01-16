package com.mensgratiae.backend.service;

import com.mensgratiae.backend.dto.*;

import java.util.List;

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

    BasicOutput getSubmissions(long researchId);
}
