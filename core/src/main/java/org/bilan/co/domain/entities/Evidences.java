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
@Table(name = "evidences")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Evidences.findAll", query = "SELECT e FROM Evidences e"),
    @NamedQuery(name = "Evidences.findById", query = "SELECT e FROM Evidences e WHERE e.id = :id"),
    @NamedQuery(name = "Evidences.findByUrlFile", query = "SELECT e FROM Evidences e WHERE e.urlFile = :urlFile"),
    @NamedQuery(name = "Evidences.findByCreatedAt", query = "SELECT e FROM Evidences e WHERE e.createdAt = :createdAt")})
public class Evidences implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Column(name = "url_file")
    private String urlFile;
    @Basic(optional = false)
    @Column(name = "created_at")
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdAt;
    @JoinColumn(name = "id_student", referencedColumnName = "id")
    @ManyToOne
    private Students idStudent;
    @JoinColumn(name = "id_activiy", referencedColumnName = "id")
    @ManyToOne
    private Activities idActiviy;

    public Evidences() {
    }

    public Evidences(Integer id) {
        this.id = id;
    }

    public Evidences(Integer id, Date createdAt) {
        this.id = id;
        this.createdAt = createdAt;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUrlFile() {
        return urlFile;
    }

    public void setUrlFile(String urlFile) {
        this.urlFile = urlFile;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public Students getIdStudent() {
        return idStudent;
    }

    public void setIdStudent(Students idStudent) {
        this.idStudent = idStudent;
    }

    public Activities getIdActiviy() {
        return idActiviy;
    }

    public void setIdActiviy(Activities idActiviy) {
        this.idActiviy = idActiviy;
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
        if (!(object instanceof Evidences)) {
            return false;
        }
        Evidences other = (Evidences) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "org.bilan.co.bilanbackend.domain.entities.Evidences[ id=" + id + " ]";
    }

}
