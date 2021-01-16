package com.mensgratiae.backend.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class GenericResearchQuestion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "researchId", referencedColumnName = "id")
    private Research research;

    @Column
    @Type(type="text")
    private String question;

    @Column
    private QuestionType type;

    @Column
    private int numberOfOptions;

    @Column
    @ElementCollection(targetClass = String.class)
    List<String> options;

    @Column
    private int relativePosition;

    public enum QuestionType {
        YES_NO, RANGE, TEXT, SINGLE_CHOICE, MULTIPLE_CHOICE, NUMBER
    }

    @OneToMany(cascade = CascadeType.REMOVE, mappedBy = "question", fetch = FetchType.LAZY)
    private List<GenericResearchQuestionAnswer> answers;
}
