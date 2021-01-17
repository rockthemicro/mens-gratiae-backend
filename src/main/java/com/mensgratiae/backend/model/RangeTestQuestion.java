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
public class RangeTestQuestion {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "testId", referencedColumnName = "id")
    private Test test;

    @Column
    @Type(type="text")
    private String question;

    @Column
    private int relativePosition;

    @OneToMany(cascade = CascadeType.REMOVE, mappedBy = "question", fetch = FetchType.LAZY)
    private List<RangeTestQuestionAnswer> answers;
}
