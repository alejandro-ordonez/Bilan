package org.bilan.co.domain.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
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
public class StudentStats implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue
    @Basic(optional = false)
    private Integer id;

    @Column(name = "general_totems")
    private Integer generalTotems = 10;

    @Column(name = "analytical_totems")
    private Integer analyticalTotems = 0;

    @Column(name = "critical_totems")
    private Integer criticalTotems = 0;

    @Column(name = "current_cycle")
    private Integer currentCycle = 1;

    @Column(name = "current_spirits")
    private Integer currentSpirits = 3;

    @Column(name = "tribes_balance")
    private String tribesBalance;

    @Column(name = "created_at")
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdAt;

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
