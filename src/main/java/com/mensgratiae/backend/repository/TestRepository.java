package com.mensgratiae.backend.repository;

import com.mensgratiae.backend.model.Test;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TestRepository extends JpaRepository<Test, Long> {
    List<Test> findAllByResearch_Id(long researchId);
}
