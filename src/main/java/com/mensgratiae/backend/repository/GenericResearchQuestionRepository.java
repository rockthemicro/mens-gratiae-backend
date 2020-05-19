package com.mensgratiae.backend.repository;

import com.mensgratiae.backend.model.GenericResearchQuestion;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface GenericResearchQuestionRepository extends JpaRepository<GenericResearchQuestion, Long> {
    List<GenericResearchQuestion> findAllByResearch_Id(long researchId);
}
