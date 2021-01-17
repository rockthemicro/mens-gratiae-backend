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
public class ResearchSubmission {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "researchId", referencedColumnName = "id")
    private Research research;

    @OneToMany(cascade = CascadeType.REMOVE, mappedBy = "researchSubmission", fetch = FetchType.LAZY)
    private List<TestSubmission> testSubmissions;

    @OneToMany(cascade = CascadeType.REMOVE, mappedBy = "researchSubmission", fetch = FetchType.LAZY)
    private List<GenericResearchQuestionAnswer> answers;
}
