/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.bilan.co.domain.entities;

import org.bilan.co.domain.enums.DocumentType;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 *
 * @author Manuel Alejandro
 */
@Entity
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Teachers.findAll", query = "SELECT t FROM Teachers t"),
    @NamedQuery(name = "Teachers.findById", query = "SELECT t FROM Teachers t WHERE t.id = :id"),
    @NamedQuery(name = "Teachers.findByDocument", query = "SELECT t FROM Teachers t WHERE t.document = :document"),
    @NamedQuery(name = "Teachers.findByDocumentType", query = "SELECT t FROM Teachers t WHERE t.documentType = :documentType"),
    @NamedQuery(name = "Teachers.findByName", query = "SELECT t FROM Teachers t WHERE t.name = :name"),
    @NamedQuery(name = "Teachers.findByEmail", query = "SELECT t FROM Teachers t WHERE t.email = :email"),
    @NamedQuery(name = "Teachers.findByPassword", query = "SELECT t FROM Teachers t WHERE t.password = :password"),
    @NamedQuery(name = "Teachers.findByLastName", query = "SELECT t FROM Teachers t WHERE t.lastName = :lastName"),
    @NamedQuery(name = "Teachers.findByIdClass", query = "SELECT t FROM Teachers t WHERE t.idClass = :idClass"),
    @NamedQuery(name = "Teachers.findByPositionName", query = "SELECT t FROM Teachers t WHERE t.positionName = :positionName"),
    @NamedQuery(name = "Teachers.findByCreatedAt", query = "SELECT t FROM Teachers t WHERE t.createdAt = :createdAt"),
    @NamedQuery(name = "Teachers.findByModifiedAt", query = "SELECT t FROM Teachers t WHERE t.modifiedAt = :modifiedAt")})
public class Teachers implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    private Integer id;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 255)
    @Column(unique = true)
    private String document;
    @Enumerated(EnumType.STRING)
    @Column(name = "document_type")
    private DocumentType documentType;
    @Size(max = 255)
    private String name;
    // @Pattern(regexp="[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*@(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?", message="Invalid email")//if the field contains email address consider using this annotation to enforce field validation
    @Size(max = 255)
    private String email;
    @Size(max = 255)
    private String password;
    @Size(max = 255)
    @Column(name = "last_name")
    private String lastName;
    @Column(name = "id_class")
    private Integer idClass;
    @Size(max = 255)
    @Column(name = "position_name")
    private String positionName;
    @Basic(optional = false)
    @NotNull
    @Column(name = "created_at")
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdAt;
    @Basic(optional = false)
    @NotNull
    @Column(name = "modified_at")
    @Temporal(TemporalType.TIMESTAMP)
    private Date modifiedAt;
    @OneToMany(mappedBy = "idTeacher")
    private List<Classrooms> classroomsList;

    public Teachers() {
    }

    public Teachers(Integer id) {
        this.id = id;
    }

    public Teachers(Integer id, String document, Date createdAt, Date modifiedAt) {
        this.id = id;
        this.document = document;
        this.createdAt = createdAt;
        this.modifiedAt = modifiedAt;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getDocument() {
        return document;
    }

    public void setDocument(String document) {
        this.document = document;
    }

    public DocumentType getDocumentType() {
        return documentType;
    }

    public void setDocumentType(DocumentType documentType) {
        this.documentType = documentType;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public Integer getIdClass() {
        return idClass;
    }

    public void setIdClass(Integer idClass) {
        this.idClass = idClass;
    }

    public String getPositionName() {
        return positionName;
    }

    public void setPositionName(String positionName) {
        this.positionName = positionName;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public Date getModifiedAt() {
        return modifiedAt;
    }

    public void setModifiedAt(Date modifiedAt) {
        this.modifiedAt = modifiedAt;
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
        if (!(object instanceof Teachers)) {
            return false;
        }
        Teachers other = (Teachers) object;
        return (this.id != null || other.id == null) && (this.id == null || this.id.equals(other.id));
    }

    @Override
    public String toString() {
        return "org.bilan.co.domain.entities.Teachers[ id=" + id + " ]";
    }

}