package org.bilan.co.domain.entities;

import lombok.*;

import javax.persistence.*;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@EqualsAndHashCode
@ToString
public class Sessions {

    @Id
    @GeneratedValue
    @Basic(optional = false)
    private Integer id;

    @OneToOne
    @JoinColumn(name = "actions_id", referencedColumnName = "id")
    private Actions actions;

    @ManyToOne
    @JoinColumn(name = "student_id", referencedColumnName = "document")
    private Students students;

    private Long score;

    @OneToMany(mappedBy = "sessions")
    private List<ResolvedAnswerBy> resolvedAnswerBy;
}
