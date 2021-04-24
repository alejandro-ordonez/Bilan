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
@Table(name = "classrooms")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Classrooms.findAll", query = "SELECT c FROM Classrooms c"),
    @NamedQuery(name = "Classrooms.findById", query = "SELECT c FROM Classrooms c WHERE c.id = :id")})
public class Classrooms implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @JoinColumn(name = "id_tribe", referencedColumnName = "id")
    @ManyToOne
    private Tribes idTribe;
    @JoinColumn(name = "id_course", referencedColumnName = "id")
    @ManyToOne
    private Courses idCourse;
    @JoinColumn(name = "id_teacher", referencedColumnName = "id")
    @ManyToOne
    private Teachers idTeacher;

    public Classrooms() {
    }

    public Classrooms(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Tribes getIdTribe() {
        return idTribe;
    }

    public void setIdTribe(Tribes idTribe) {
        this.idTribe = idTribe;
    }

    public Courses getIdCourse() {
        return idCourse;
    }

    public void setIdCourse(Courses idCourse) {
        this.idCourse = idCourse;
    }

    public Teachers getIdTeacher() {
        return idTeacher;
    }

    public void setIdTeacher(Teachers idTeacher) {
        this.idTeacher = idTeacher;
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
        if (!(object instanceof Classrooms)) {
            return false;
        }
        Classrooms other = (Classrooms) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "org.bilan.co.bilanbackend.domain.entities.Classrooms[ id=" + id + " ]";
    }

}
