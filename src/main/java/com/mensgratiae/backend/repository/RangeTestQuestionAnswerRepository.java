package com.mensgratiae.backend.repository;

import com.mensgratiae.backend.model.RangeTestQuestionAnswer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RangeTestQuestionAnswerRepository extends
        JpaRepository<RangeTestQuestionAnswer, Long> {
    List<RangeTestQuestionAnswer> findAllByQuestion_Id(long questionId);
}
