package com.mensgratiae.backend.repository;

import com.mensgratiae.backend.model.ResearchSubmission;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ResearchSubmissionRepository extends
        JpaRepository<ResearchSubmission, Long> {
}
