package com.mensgratiae.backend.repository;

import com.mensgratiae.backend.model.RangeTestQuestionAnswer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RangeTestQuestionAnswerRepository extends
        JpaRepository<RangeTestQuestionAnswer, Long> {

}
