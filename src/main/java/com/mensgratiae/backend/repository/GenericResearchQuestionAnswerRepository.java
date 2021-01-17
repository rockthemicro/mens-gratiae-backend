package com.mensgratiae.backend.repository;

import com.mensgratiae.backend.model.GenericResearchQuestionAnswer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GenericResearchQuestionAnswerRepository extends
        JpaRepository<GenericResearchQuestionAnswer, Long> {

}
