/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.bilan.co.domain.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.*;
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
    @NamedQuery(name = "Actions.findAll", query = "SELECT a FROM Actions a"),
    @NamedQuery(name = "Actions.findById", query = "SELECT a FROM Actions a WHERE a.id = :id"),
    @NamedQuery(name = "Actions.findByName", query = "SELECT a FROM Actions a WHERE a.name = :name"),
    @NamedQuery(name = "Actions.findByDescription", query = "SELECT a FROM Actions a WHERE a.description = :description"),
    @NamedQuery(name = "Actions.findByRepresentative", query = "SELECT a FROM Actions a WHERE a.representative = :representative")})
public class Actions implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    private Integer id;
    @Size(max = 255)
    private String name;
    @Size(max = 255)
    private String description;
    @Size(max = 255)
    private String representative;
    @Size(max = 255)
    private String imagePath;
    @JoinColumn(name = "id_tribe", referencedColumnName = "id")
    @ManyToOne
    private Tribes idTribe;

    @JoinColumn(name = "id_challenge", referencedColumnName = "id")
    @OneToOne(cascade = CascadeType.ALL)
    @JsonIgnore
    private StudentActions studentChallenges;

    public Actions() {
    }

    public Actions(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getRepresentative() {
        return representative;
    }

    public void setRepresentative(String representative) {
        this.representative = representative;
    }

    public Tribes getIdTribe() {
        return idTribe;
    }

    public void setIdTribe(Tribes idTribe) {
        this.idTribe = idTribe;
    }

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    public StudentActions getStudentChallenges() {
        return studentChallenges;
    }

    public void setStudentChallenges(StudentActions studentChallenges) {
        this.studentChallenges = studentChallenges;
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
        if (!(object instanceof Actions)) {
            return false;
        }
        Actions other = (Actions) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "org.bilan.co.domain.entities.Actions[ id=" + id + " ]";
    }

}
