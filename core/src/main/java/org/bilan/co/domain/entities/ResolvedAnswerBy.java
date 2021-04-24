/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.bilan.co.domain.entities;

import java.io.Serializable;
import java.util.Date;
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
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Manuel Alejandro
 */
@Entity
@Table(name = "resolved_answer_by")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "ResolvedAnswerBy.findAll", query = "SELECT r FROM ResolvedAnswerBy r"),
    @NamedQuery(name = "ResolvedAnswerBy.findById", query = "SELECT r FROM ResolvedAnswerBy r WHERE r.id = :id"),
    @NamedQuery(name = "ResolvedAnswerBy.findByCreatedAt", query = "SELECT r FROM ResolvedAnswerBy r WHERE r.createdAt = :createdAt")})
public class ResolvedAnswerBy implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Basic(optional = false)
    @Column(name = "created_at")
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdAt;
    @JoinColumn(name = "id_challenge", referencedColumnName = "id")
    @ManyToOne
    private Challenges idChallenge;
    @JoinColumn(name = "id_student", referencedColumnName = "id")
    @ManyToOne
    private Students idStudent;
    @JoinColumn(name = "id_question", referencedColumnName = "id")
    @ManyToOne
    private Questions idQuestion;
    @JoinColumn(name = "id_answer", referencedColumnName = "id")
    @ManyToOne
    private Answers idAnswer;

    public ResolvedAnswerBy() {
    }

    public ResolvedAnswerBy(Integer id) {
        this.id = id;
    }

    public ResolvedAnswerBy(Integer id, Date createdAt) {
        this.id = id;
        this.createdAt = createdAt;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public Challenges getIdChallenge() {
        return idChallenge;
    }

    public void setIdChallenge(Challenges idChallenge) {
        this.idChallenge = idChallenge;
    }

    public Students getIdStudent() {
        return idStudent;
    }

    public void setIdStudent(Students idStudent) {
        this.idStudent = idStudent;
    }

    public Questions getIdQuestion() {
        return idQuestion;
    }

    public void setIdQuestion(Questions idQuestion) {
        this.idQuestion = idQuestion;
    }

    public Answers getIdAnswer() {
        return idAnswer;
    }

    public void setIdAnswer(Answers idAnswer) {
        this.idAnswer = idAnswer;
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
        if (!(object instanceof ResolvedAnswerBy)) {
            return false;
        }
        ResolvedAnswerBy other = (ResolvedAnswerBy) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "org.bilan.co.bilanbackend.domain.entities.ResolvedAnswerBy[ id=" + id + " ]";
    }

}
