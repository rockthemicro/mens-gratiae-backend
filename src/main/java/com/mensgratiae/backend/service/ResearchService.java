package com.mensgratiae.backend.service;

import com.mensgratiae.backend.dto.AddOrUpdateResearchOutput;
import com.mensgratiae.backend.dto.ResearchDto;
import com.mensgratiae.backend.dto.ResearchGetOutput;
import com.mensgratiae.backend.dto.ResearchesGetOutput;

public interface ResearchService {
    ResearchesGetOutput getResearches();

    ResearchGetOutput getResearch(long id);

    AddOrUpdateResearchOutput addOrUpdateResearch(ResearchDto researchDto, boolean isAdd);
}
