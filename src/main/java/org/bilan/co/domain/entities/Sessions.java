package org.bilan.co.domain.entities;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.util.Date;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@EqualsAndHashCode
@ToString
@EntityListeners(AuditingEntityListener.class)
public class Sessions {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
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

    @CreatedDate
    private Date createdAt;

    @LastModifiedDate
    private Date modifiedAt;

    @OneToMany(mappedBy = "sessions", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<ResolvedAnswerBy> resolvedAnswerBy;
}
