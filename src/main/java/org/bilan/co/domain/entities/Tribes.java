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
    @NamedQuery(name = "Tribes.findAll", query = "SELECT t FROM Tribes t"),
    @NamedQuery(name = "Tribes.findById", query = "SELECT t FROM Tribes t WHERE t.id = :id"),
    @NamedQuery(name = "Tribes.findByName", query = "SELECT t FROM Tribes t WHERE t.name = :name"),
    @NamedQuery(name = "Tribes.findByCulture", query = "SELECT t FROM Tribes t WHERE t.culture = :culture"),
    @NamedQuery(name = "Tribes.findByElement", query = "SELECT t FROM Tribes t WHERE t.element = :element")})
public class Tribes implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    private Integer id;
    @Size(max = 255)
    private String name;
    @Size(max = 255)
    private String culture;
    @Size(max = 255)
    private String element;
    @OneToMany(mappedBy = "oppositeTribeId")
    private List<Tribes> tribesList;
    @JoinColumn(name = "opposite_tribe_id", referencedColumnName = "id")
    @ManyToOne
    private Tribes oppositeTribeId;
    @OneToMany(mappedBy = "adjacentTribeId")
    private List<Tribes> tribesList1;
    @JoinColumn(name = "adjacent_tribe_id", referencedColumnName = "id")
    @ManyToOne
    private Tribes adjacentTribeId;
    @OneToMany(mappedBy = "idTribe")
    private List<Classrooms> classroomsList;
    @OneToMany(mappedBy = "idTribe")
    private List<Actions> actionsList;

    public Tribes() {
    }

    public Tribes(Integer id) {
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

    public String getCulture() {
        return culture;
    }

    public void setCulture(String culture) {
        this.culture = culture;
    }

    public String getElement() {
        return element;
    }

    public void setElement(String element) {
        this.element = element;
    }

    @XmlTransient
    public List<Tribes> getTribesList() {
        return tribesList;
    }

    public void setTribesList(List<Tribes> tribesList) {
        this.tribesList = tribesList;
    }

    public Tribes getOppositeTribeId() {
        return oppositeTribeId;
    }

    public void setOppositeTribeId(Tribes oppositeTribeId) {
        this.oppositeTribeId = oppositeTribeId;
    }

    @XmlTransient
    public List<Tribes> getTribesList1() {
        return tribesList1;
    }

    public void setTribesList1(List<Tribes> tribesList1) {
        this.tribesList1 = tribesList1;
    }

    public Tribes getAdjacentTribeId() {
        return adjacentTribeId;
    }

    public void setAdjacentTribeId(Tribes adjacentTribeId) {
        this.adjacentTribeId = adjacentTribeId;
    }

    @XmlTransient
    public List<Classrooms> getClassroomsList() {
        return classroomsList;
    }

    public void setClassroomsList(List<Classrooms> classroomsList) {
        this.classroomsList = classroomsList;
    }

    @XmlTransient
    public List<Actions> getActionsList() {
        return actionsList;
    }

    public void setActionsList(List<Actions> actionsList) {
        this.actionsList = actionsList;
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
        if (!(object instanceof Tribes)) {
            return false;
        }
        Tribes other = (Tribes) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "org.bilan.co.domain.entities.Tribes[ id=" + id + " ]";
    }

}
