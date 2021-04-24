/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.bilan.co.domain.entities;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author Manuel Alejandro
 */
@Entity
@Table(name = "questions")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Questions.findAll", query = "SELECT q FROM Questions q"),
    @NamedQuery(name = "Questions.findById", query = "SELECT q FROM Questions q WHERE q.id = :id"),
    @NamedQuery(name = "Questions.findByTitle", query = "SELECT q FROM Questions q WHERE q.title = :title"),
    @NamedQuery(name = "Questions.findByDifficulty", query = "SELECT q FROM Questions q WHERE q.difficulty = :difficulty"),
    @NamedQuery(name = "Questions.findByClueChaman", query = "SELECT q FROM Questions q WHERE q.clueChaman = :clueChaman")})
public class Questions implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Column(name = "title")
    private String title;
    @Lob
    @Column(name = "statments")
    private String statments;
    @Column(name = "difficulty")
    private Integer difficulty;
    @Column(name = "clue_chaman")
    private String clueChaman;
    @OneToMany(mappedBy = "idQuestion")
    private List<Answers> answersList;
    @JoinColumn(name = "id_challenge", referencedColumnName = "id")
    @ManyToOne
    private Challenges idChallenge;
    @OneToMany(mappedBy = "idQuestion")
    private List<ResolvedAnswerBy> resolvedAnswerByList;

    public Questions() {
    }

    public Questions(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getStatments() {
        return statments;
    }

    public void setStatments(String statments) {
        this.statments = statments;
    }

    public Integer getDifficulty() {
        return difficulty;
    }

    public void setDifficulty(Integer difficulty) {
        this.difficulty = difficulty;
    }

    public String getClueChaman() {
        return clueChaman;
    }

    public void setClueChaman(String clueChaman) {
        this.clueChaman = clueChaman;
    }

    @XmlTransient
    public List<Answers> getAnswersList() {
        return answersList;
    }

    public void setAnswersList(List<Answers> answersList) {
        this.answersList = answersList;
    }

    public Challenges getIdChallenge() {
        return idChallenge;
    }

    public void setIdChallenge(Challenges idChallenge) {
        this.idChallenge = idChallenge;
    }

    @XmlTransient
    public List<ResolvedAnswerBy> getResolvedAnswerByList() {
        return resolvedAnswerByList;
    }

    public void setResolvedAnswerByList(List<ResolvedAnswerBy> resolvedAnswerByList) {
        this.resolvedAnswerByList = resolvedAnswerByList;
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
        if (!(object instanceof Questions)) {
            return false;
        }
        Questions other = (Questions) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "org.bilan.co.bilanbackend.domain.entities.Questions[ id=" + id + " ]";
    }

}
