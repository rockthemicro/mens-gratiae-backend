package com.mensgratiae.backend.repository;

import com.mensgratiae.backend.model.RangeTestQuestion;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RangeTestQuestionRepository extends JpaRepository<RangeTestQuestion, Long> {
    List<RangeTestQuestion> findAllByTest_Id(long testId);
}
