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
import javax.persistence.Lob;
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
    @NamedQuery(name = "Activities.findAll", query = "SELECT a FROM Activities a"),
    @NamedQuery(name = "Activities.findById", query = "SELECT a FROM Activities a WHERE a.id = :id"),
    @NamedQuery(name = "Activities.findByIdTribe", query = "SELECT a FROM Activities a WHERE a.idTribe = :idTribe")})
public class Activities implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    private Integer id;
    @Column(name = "id_tribe")
    private Integer idTribe;
    @Lob
    @Size(max = 65535)
    private String description;
    @OneToMany(mappedBy = "idActiviy")
    private List<Evidences> evidencesList;

    public Activities() {
    }

    public Activities(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getIdTribe() {
        return idTribe;
    }

    public void setIdTribe(Integer idTribe) {
        this.idTribe = idTribe;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @XmlTransient
    public List<Evidences> getEvidencesList() {
        return evidencesList;
    }

    public void setEvidencesList(List<Evidences> evidencesList) {
        this.evidencesList = evidencesList;
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
        if (!(object instanceof Activities)) {
            return false;
        }
        Activities other = (Activities) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "org.bilan.co.domain.entities.Activities[ id=" + id + " ]";
    }

}
