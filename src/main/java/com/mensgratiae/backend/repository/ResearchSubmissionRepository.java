package com.mensgratiae.backend.repository;

import com.mensgratiae.backend.model.ResearchSubmission;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ResearchSubmissionRepository extends
        JpaRepository<ResearchSubmission, Long> {

    List<ResearchSubmission> findAllByResearchId(long researchId);
}
