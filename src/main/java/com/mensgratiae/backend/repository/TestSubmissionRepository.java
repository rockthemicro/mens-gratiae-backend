package com.mensgratiae.backend.repository;

import com.mensgratiae.backend.model.TestSubmission;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TestSubmissionRepository extends
        JpaRepository<TestSubmission, Long> {

}
