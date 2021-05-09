/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.bilan.co.domain.entities;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
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
public class StudentChallenges implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    private Integer id;
    @Column(name = "current_points")
    private Integer currentPoints;
    @JoinColumn(name = "id_student_stat", referencedColumnName = "id")
    @ManyToOne
    private StudentStats idStudentStat;
    @JoinColumn(name = "id_challenge", referencedColumnName = "id")
    @ManyToOne
    private Challenges idChallenge;

    public StudentChallenges() {
    }

    public StudentChallenges(Integer id) {
        this.id = id;
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

    public Challenges getIdChallenge() {
        return idChallenge;
    }

    public void setIdChallenge(Challenges idChallenge) {
        this.idChallenge = idChallenge;
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
        if (!(object instanceof StudentChallenges)) {
            return false;
        }
        StudentChallenges other = (StudentChallenges) object;
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
