package org.bilan.co.domain.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.util.Date;

@Table(name = "evaluation")
@Entity
@Data
@EqualsAndHashCode
@EntityListeners(AuditingEntityListener.class)
public class Evaluation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
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

    @CreatedDate
    private Date createdAt;
}