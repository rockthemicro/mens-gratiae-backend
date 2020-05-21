package com.mensgratiae.backend.service;

import com.mensgratiae.backend.dto.*;

public interface ResearchService {
    ResearchesGetOutput getResearches();

    ResearchGetOutput getResearch(long id);

    AddOrUpdateResearchOutput addOrUpdateResearch(ResearchDto researchDto, boolean isAdd);

    AddOrUpdateGenericResearchQuestionOutput addOrUpdateGenericResearchQuestion(GenericResearchQuestionDto questionDto,
                                                                                boolean isAdd);

    BasicOutput deleteResearch(long id);

    BasicOutput deleteGenericResearchQuestion(long id);
}
