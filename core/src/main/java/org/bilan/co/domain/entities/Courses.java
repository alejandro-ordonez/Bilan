/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.bilan.co.domain.entities;

import javax.persistence.*;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
import java.io.Serializable;
import java.util.List;

/**
 *
 * @author Manuel Alejandro
 */
@Entity
@XmlRootElement
@NamedQueries({
        @NamedQuery(name = "Courses.findAll", query = "SELECT c FROM Courses c"),
        @NamedQuery(name = "Courses.findById", query = "SELECT c FROM Courses c WHERE c.id = :id"),
        @NamedQuery(name = "Courses.findByGroups", query = "SELECT c FROM Courses c WHERE c.groups = :groups"),
        @NamedQuery(name = "Courses.findByGrade", query = "SELECT c FROM Courses c WHERE c.grade = :grade"),
        @NamedQuery(name = "Courses.findBySchool", query = "SELECT c FROM Courses c WHERE c.school = :school")})
public class Courses implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    private Integer id;
    @Size(max = 255)
    private String groups;
    @Size(max = 255)
    private String grade;
    @Size(max = 255)
    private String school;
    @OneToMany(mappedBy = "idCourse")
    private List<Classrooms> classroomsList;

    public Courses() {
    }

    public Courses(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getGroups() {
        return groups;
    }

    public void setGroups(String groups) {
        this.groups = groups;
    }

    public String getGrade() {
        return grade;
    }

    public void setGrade(String grade) {
        this.grade = grade;
    }

    public String getSchool() {
        return school;
    }

    public void setSchool(String school) {
        this.school = school;
    }

    @XmlTransient
    public List<Classrooms> getClassroomsList() {
        return classroomsList;
    }

    public void setClassroomsList(List<Classrooms> classroomsList) {
        this.classroomsList = classroomsList;
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
        if (!(object instanceof Courses)) {
            return false;
        }
        Courses other = (Courses) object;
        return (this.id != null || other.id == null) && (this.id == null || this.id.equals(other.id));
    }

    @Override
    public String toString() {
        return "org.bilan.co.domain.entities.Courses[ id=" + id + " ]";
    }

}
