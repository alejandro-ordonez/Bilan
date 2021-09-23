/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.bilan.co.domain.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.io.Serializable;
import javax.persistence.*;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Manuel Alejandro
 */
@Entity
@Table(name = "student_challenges")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "StudentChallenges.findAll", query = "SELECT s FROM StudentChallenges s"),
    @NamedQuery(name = "StudentChallenges.findById", query = "SELECT s FROM StudentChallenges s WHERE s.id = :id"),
    @NamedQuery(name = "StudentChallenges.findByCurrentPoints", query = "SELECT s FROM StudentChallenges s WHERE s.currentPoints = :currentPoints")})
public class StudentActions implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    private Integer id;
    @Column(name = "current_points")
    private Integer currentPoints;
    @JoinColumn(name = "id_student_stat", referencedColumnName = "id")
    @ManyToOne(targetEntity = StudentStats.class)
    @JsonIgnore
    private StudentStats idStudentStat;
    @JoinColumn(name = "id_challenge", referencedColumnName = "id")
    @OneToOne(mappedBy = "studentChallenges", cascade = CascadeType.ALL)
    private Actions idAction;

    public StudentActions() {
    }

    public StudentActions(Integer currentPoints) {
        this.currentPoints = currentPoints;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getCurrentPoints() {
        return currentPoints;
    }

    public void setCurrentPoints(Integer currentPoints) {
        this.currentPoints = currentPoints;
    }

    public StudentStats getIdStudentStat() {
        return idStudentStat;
    }

    public void setIdStudentStat(StudentStats idStudentStat) {
        this.idStudentStat = idStudentStat;
    }

    public Actions getIdAction() {
        return idAction;
    }

    public void setIdAction(Actions idAction) {
        this.idAction = idAction;
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
        if (!(object instanceof StudentActions)) {
            return false;
        }
        StudentActions other = (StudentActions) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "org.bilan.co.domain.entities.StudentChallenges[ id=" + id + " ]";
    }

}
