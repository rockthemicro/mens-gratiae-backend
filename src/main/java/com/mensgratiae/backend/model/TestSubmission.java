package com.mensgratiae.backend.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class TestSubmission {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "testId", referencedColumnName = "id")
    private Test test;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "researchSubmissionId", referencedColumnName = "id")
    private ResearchSubmission researchSubmission;

    @OneToMany(cascade = CascadeType.REMOVE, mappedBy = "testSubmission", fetch = FetchType.LAZY)
    private List<RangeTestQuestionAnswer> answers;
}
