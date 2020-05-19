package com.mensgratiae.backend.service;

import com.mensgratiae.backend.dto.ResearchGetOutput;
import com.mensgratiae.backend.dto.ResearchesGetOutput;

public interface ResearchService {
    ResearchesGetOutput getResearches();

    ResearchGetOutput getResearch(long id);
}
