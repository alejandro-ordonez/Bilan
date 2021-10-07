package org.bilan.co.domain.entities;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

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

    @OneToOne
    @JoinColumn(name = "tribe_id", referencedColumnName = "id")
    private Tribes tribeId;

    @ManyToOne
    @JoinColumn(name = "student_id", referencedColumnName = "document")
    private Students students;

    @ManyToOne
    @JoinColumn(name = "challenge_id", referencedColumnName = "id")
    private Challenges challenges;

    private Long score;

    @OneToMany(mappedBy = "sessions")
    private List<ResolvedAnswerBy> resolvedAnswerBy;
}
