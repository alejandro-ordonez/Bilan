/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.bilan.co.domain.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * @author Manuel Alejandro
 */
@Entity
@Table(name = "student_stats")
@XmlRootElement
@NamedQueries({
        @NamedQuery(name = "StudentStats.findAll", query = "SELECT s FROM StudentStats s"),
        @NamedQuery(name = "StudentStats.findById", query = "SELECT s FROM StudentStats s WHERE s.id = :id"),
        @NamedQuery(name = "StudentStats.findByGeneralTotems", query = "SELECT s FROM StudentStats s WHERE s.generalTotems = :generalTotems"),
        @NamedQuery(name = "StudentStats.findByAnalyticalTotems", query = "SELECT s FROM StudentStats s WHERE s.analyticalTotems = :analyticalTotems"),
        @NamedQuery(name = "StudentStats.findByCriticalTotems", query = "SELECT s FROM StudentStats s WHERE s.criticalTotems = :criticalTotems"),
        @NamedQuery(name = "StudentStats.findByCurrentCycle", query = "SELECT s FROM StudentStats s WHERE s.currentCycle = :currentCycle"),
        @NamedQuery(name = "StudentStats.findByLastTotemUpdate", query = "SELECT s FROM StudentStats s WHERE s.lastTotemUpdate = :lastTotemUpdate")})
public class StudentStats implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    private Integer id;

    @Column(name = "general_totems")
    private Integer generalTotems;

    @Column(name = "analytical_totems")
    private Integer analyticalTotems;

    @Column(name = "critical_totems")
    private Integer criticalTotems;

    @Column(name = "current_cycle")
    private Integer currentCycle;

    @Column(name = "current_spirits")
    private Integer currentSpirits;

    @Lob
    @Size(max = 2147483647)
    @Column(name = "tribes_points")
    private String tribesPoints;

    @Basic(optional = false)
    @NotNull
    @Column(name = "current_cycle_end")
    @Temporal(TemporalType.TIMESTAMP)
    private Date lastTotemUpdate;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "id_student", referencedColumnName = "document")
    @JsonIgnore
    private Students idStudent;

    @OneToMany(mappedBy = "idStudentStat", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<StudentActions> studentChallengesList;

    public StudentStats() {
    }

    public static StudentStats getDefault() {
        StudentStats stats = new StudentStats();
        stats.analyticalTotems = 0;
        stats.criticalTotems = 0;
        stats.currentCycle = 1;
        stats.currentSpirits = 3;
        stats.lastTotemUpdate = new Date();
        stats.tribesPoints = "TODO";

        return stats;
    }

    public StudentStats(Integer id) {
        this.id = id;
    }

    public StudentStats(Integer id, Date lastTotemUpdate) {
        this.id = id;
        this.lastTotemUpdate = lastTotemUpdate;
    }

    public StudentStats(Integer generalTotems, Integer analyticalTotems, Integer criticalTotems, Integer currentCycle, Date lastTotemUpdate, List<StudentActions> studentChallengesList) {
        this.generalTotems = generalTotems;
        this.analyticalTotems = analyticalTotems;
        this.criticalTotems = criticalTotems;
        this.currentCycle = currentCycle;
        this.lastTotemUpdate = lastTotemUpdate;
        this.studentChallengesList = studentChallengesList;
    }

    public String getTribesPoints() {
        return tribesPoints;
    }

    public void setTribesPoints(String tribesPoints) {
        this.tribesPoints = tribesPoints;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getGeneralTotems() {
        return generalTotems;
    }

    public void setGeneralTotems(Integer generalTotems) {
        this.generalTotems = generalTotems;
    }

    public Integer getAnalyticalTotems() {
        return analyticalTotems;
    }

    public void setAnalyticalTotems(Integer analyticalTotems) {
        this.analyticalTotems = analyticalTotems;
    }

    public Integer getCriticalTotems() {
        return criticalTotems;
    }

    public void setCriticalTotems(Integer criticalTotems) {
        this.criticalTotems = criticalTotems;
    }

    public Integer getCurrentCycle() {
        return currentCycle;
    }

    public void setCurrentCycle(Integer currentCycle) {
        this.currentCycle = currentCycle;
    }

    public Date getLastTotemUpdate() {
        return lastTotemUpdate;
    }

    public void setLastTotemUpdate(Date lastTotemUpdate) {
        this.lastTotemUpdate = lastTotemUpdate;
    }

    public Students getIdStudent() {
        return idStudent;
    }

    public void setIdStudent(Students idStudent) {
        this.idStudent = idStudent;
    }

    public Integer getCurrentSpirits() {
        return currentSpirits;
    }

    public void setCurrentSpirits(Integer spirits) {
        this.currentSpirits = spirits;
    }

    @XmlTransient
    public List<StudentActions> getStudentChallengesList() {
        return studentChallengesList;
    }

    public void setStudentChallengesList(List<StudentActions> studentChallengesList) {
        this.studentChallengesList = studentChallengesList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof StudentStats)) {
            return false;
        }
        StudentStats other = (StudentStats) object;
        return (this.id != null || other.id == null) && (this.id == null || this.id.equals(other.id));
    }

    @Override
    public String toString() {
        return "org.bilan.co.domain.entities.StudentStats[ id=" + id + " ]";
    }

}
