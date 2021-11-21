package org.bilan.co.domain.entities;

import lombok.Data;

import javax.persistence.*;

@Table(name = "evaluation")
@Entity
@Data
public class Evaluation {
    @Id
    @Column(name = "id", nullable = false)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "evidences_id")
    private Evidences evidence;

    @ManyToOne
    @JoinColumn(name = "teacher_document")
    private Teachers teacher;




}