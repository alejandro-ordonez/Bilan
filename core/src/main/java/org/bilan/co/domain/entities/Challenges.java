/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.bilan.co.domain.entities;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author Manuel Alejandro
 */
@Entity
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Challenges.findAll", query = "SELECT c FROM Challenges c"),
    @NamedQuery(name = "Challenges.findById", query = "SELECT c FROM Challenges c WHERE c.id = :id"),
    @NamedQuery(name = "Challenges.findByCost", query = "SELECT c FROM Challenges c WHERE c.cost = :cost"),
    @NamedQuery(name = "Challenges.findByName", query = "SELECT c FROM Challenges c WHERE c.name = :name"),
    @NamedQuery(name = "Challenges.findByTimer", query = "SELECT c FROM Challenges c WHERE c.timer = :timer"),
    @NamedQuery(name = "Challenges.findByReward", query = "SELECT c FROM Challenges c WHERE c.reward = :reward"),
    @NamedQuery(name = "Challenges.findByType", query = "SELECT c FROM Challenges c WHERE c.type = :type")})
public class Challenges implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    private Integer id;
    private Integer cost;
    @Size(max = 255)
    private String name;
    private Integer timer;
    private Integer reward;
    @Size(max = 255)
    private String type;
    @JoinColumn(name = "id_action", referencedColumnName = "id")
    @ManyToOne
    private Actions idAction;
    @OneToMany(mappedBy = "idChallenge")
    private List<Questions> questionsList;
    @OneToMany(mappedBy = "idChallenge")
    private List<ResolvedAnswerBy> resolvedAnswerByList;
    @OneToMany(mappedBy = "idChallenge")
    private List<StudentChallenges> studentChallengesList;

    public Challenges() {
    }

    public Challenges(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getCost() {
        return cost;
    }

    public void setCost(Integer cost) {
        this.cost = cost;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getTimer() {
        return timer;
    }

    public void setTimer(Integer timer) {
        this.timer = timer;
    }

    public Integer getReward() {
        return reward;
    }

    public void setReward(Integer reward) {
        this.reward = reward;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Actions getIdAction() {
        return idAction;
    }

    public void setIdAction(Actions idAction) {
        this.idAction = idAction;
    }

    @XmlTransient
    public List<Questions> getQuestionsList() {
        return questionsList;
    }

    public void setQuestionsList(List<Questions> questionsList) {
        this.questionsList = questionsList;
    }

    @XmlTransient
    public List<ResolvedAnswerBy> getResolvedAnswerByList() {
        return resolvedAnswerByList;
    }

    public void setResolvedAnswerByList(List<ResolvedAnswerBy> resolvedAnswerByList) {
        this.resolvedAnswerByList = resolvedAnswerByList;
    }

    @XmlTransient
    public List<StudentChallenges> getStudentChallengesList() {
        return studentChallengesList;
    }

    public void setStudentChallengesList(List<StudentChallenges> studentChallengesList) {
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
        if (!(object instanceof Challenges)) {
            return false;
        }
        Challenges other = (Challenges) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "org.bilan.co.domain.entities.Challenges[ id=" + id + " ]";
    }

}
