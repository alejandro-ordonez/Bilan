package org.bilan.co.domain.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;
import java.util.Date;

/**
 * @author Manuel Alejandro
 */
@Entity
@Table(name = "student_stats")
@XmlRootElement
@NoArgsConstructor
@Data
@EqualsAndHashCode
@ToString
@EntityListeners(AuditingEntityListener.class)
public class StudentStats implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    private Integer id;

    @Max(15)
    @Column(name = "general_totems")
    private Integer generalTotems = 4;

    @Max(3)
    @Column(name = "analytical_totems")
    private Integer analyticalTotems = 3;

    @Max(3)
    @Column(name = "critical_totems")
    private Integer criticalTotems = 3;

    @Max(5)
    @Column(name = "current_cycle")
    private Integer currentCycle = 1;

    @Max(3)
    @Column(name = "current_spirits")
    private Integer currentSpirits = 3;

    @Column(name = "time_in_game")
    private Float timeInGame;

    @Column(name = "tribes_balance")
    private String tribesBalance;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "created_at")
    @CreatedDate
    private Date createdAt;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "last_played")
    @LastModifiedDate
    private Date lastPlayed;

    @Basic(optional = false)
    @NotNull
    @Column(name = "current_cycle_end")
    @Temporal(TemporalType.TIMESTAMP)
    private Date lastTotemUpdate = new Date();

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "id_student", referencedColumnName = "document")
    @JsonIgnore
    private Students idStudent;
}
