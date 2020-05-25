package com.mensgratiae.backend.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Type;

import javax.persistence.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class GenericResearchQuestionAnswer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column
    @Type(type="text")
    private String answer;

    @ManyToOne
    @JoinColumn(name = "questionId", referencedColumnName = "id")
    private GenericResearchQuestion question;

    @ManyToOne
    @JoinColumn(name = "researchSubmissionId", referencedColumnName = "id")
    private ResearchSubmission researchSubmission;
}
