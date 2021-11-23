package org.bilan.co.domain.entities;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Table(name = "evaluation")
@Entity
@Data
@EqualsAndHashCode
public class Evaluation {

    @Id
    @GeneratedValue
    @Column(name = "id", nullable = false)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "evidences_id")
    private Evidences evidence;

    @ManyToOne
    @JoinColumn(name = "teacher_document")
    private Teachers teacher;

    @Column(name = "cb_score")
    @NotNull
    private Integer cbScore;

    @Column(name = "cc_score")
    @NotNull
    private Integer ccScore;

    @Column(name = "cs_score")
    @NotNull
    private Integer csScore;

    @Column(name = "tribe_score")
    @NotNull
    private Integer tribeScore;
}