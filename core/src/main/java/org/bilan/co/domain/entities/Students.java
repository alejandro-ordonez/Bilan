/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.bilan.co.domain.entities;

import org.bilan.co.domain.enums.DocumentType;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
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
    @NamedQuery(name = "Students.findAll", query = "SELECT s FROM Students s"),
    @NamedQuery(name = "Students.findById", query = "SELECT s FROM Students s WHERE s.id = :id"),
    @NamedQuery(name = "Students.findByDocument", query = "SELECT s FROM Students s WHERE s.document = :document"),
    @NamedQuery(name = "Students.findByDocumentType", query = "SELECT s FROM Students s WHERE s.documentType = :documentType"),
    @NamedQuery(name = "Students.findByName", query = "SELECT s FROM Students s WHERE s.name = :name"),
    @NamedQuery(name = "Students.findByEmail", query = "SELECT s FROM Students s WHERE s.email = :email"),
    @NamedQuery(name = "Students.findByPassword", query = "SELECT s FROM Students s WHERE s.password = :password"),
    @NamedQuery(name = "Students.findByLastName", query = "SELECT s FROM Students s WHERE s.lastName = :lastName"),
    @NamedQuery(name = "Students.findByCreatedAt", query = "SELECT s FROM Students s WHERE s.createdAt = :createdAt"),
    @NamedQuery(name = "Students.findByModifiedAt", query = "SELECT s FROM Students s WHERE s.modifiedAt = :modifiedAt")})
public class Students implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    private Integer id;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 255)
    private String document;
    @Enumerated(EnumType.STRING)
    @Column(name = "document_type")
    private DocumentType documentType;
    @Size(max = 255)
    private String name;
    @Pattern(regexp="[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*@(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?", message="Invalid email")//if the field contains email address consider using this annotation to enforce field validation
    @Size(max = 255)
    private String email;
    @Size(max = 255)
    private String password;
    @Size(max = 255)
    @Column(name = "last_name")
    private String lastName;
    @Lob
    @Column(name = "last_state")
    private String lastState;
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
    @OneToMany(mappedBy = "idStudent")
    private List<ResolvedAnswerBy> resolvedAnswerByList;
    @OneToOne(mappedBy = "idStudent")
    private StudentStats studentStatsList;
    @OneToMany(mappedBy = "idStudent")
    private List<Evidences> evidencesList;

    public Students() {
        createdAt = new Date();
    }

    public Students(Integer id) {
        this();
        this.id = id;
    }

    public Students(Integer id, String document, Date createdAt, Date modifiedAt) {
        this();
        this.id = id;
        this.document = document;
        this.createdAt = createdAt;
        this.modifiedAt = modifiedAt;
    }

    public Students(String name, String lastName, String document, DocumentType documentType, String email, String password,
                    List<ResolvedAnswerBy> resolvedAnswerByList, StudentStats studentStatsList, List<Evidences> evidencesList) {
        this();
        this.document = document;
        this.documentType = documentType;
        this.name = name;
        this.email = email;
        this.lastName = lastName;
        this.password = password;
        this.resolvedAnswerByList = resolvedAnswerByList;
        this.studentStatsList = studentStatsList;
        this.evidencesList = evidencesList;
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

    public String getLastState() {
        return lastState;
    }

    public void setLastState(String lastState) {
        this.lastState = lastState;
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
    public List<ResolvedAnswerBy> getResolvedAnswerByList() {
        return resolvedAnswerByList;
    }

    public void setResolvedAnswerByList(List<ResolvedAnswerBy> resolvedAnswerByList) {
        this.resolvedAnswerByList = resolvedAnswerByList;
    }

    @XmlTransient
    public List<StudentStats> getStudentStatsList() {
        return studentStatsList;
    }

    public void setStudentStatsList(List<StudentStats> studentStatsList) {
        this.studentStatsList = studentStatsList;
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
        if (!(object instanceof Students)) {
            return false;
        }
        Students other = (Students) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "org.bilan.co.domain.entities.Students[ id=" + id + " ]";
    }

}
