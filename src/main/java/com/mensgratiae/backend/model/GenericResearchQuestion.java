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

    @ManyToOne
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
        YES_NO, RANGE, TEXT, SINGLE_CHOICE, MULTIPLE_CHOICE
    }

    @OneToMany(cascade = CascadeType.REMOVE, mappedBy = "question")
    private List<GenericResearchQuestionAnswer> answers;
}
