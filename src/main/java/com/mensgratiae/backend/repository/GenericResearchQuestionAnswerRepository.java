package com.mensgratiae.backend.repository;

import com.mensgratiae.backend.model.GenericResearchQuestionAnswer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface GenericResearchQuestionAnswerRepository extends
        JpaRepository<GenericResearchQuestionAnswer, Long> {

    List<GenericResearchQuestionAnswer> findAllByQuestion_Id(long questionId);
}
