package com.mensgratiae.backend.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class RangeTestQuestionAnswer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column
    private Integer answer;

    @ManyToOne
    @JoinColumn(name = "questionId", referencedColumnName = "id")
    private RangeTestQuestion question;
}
