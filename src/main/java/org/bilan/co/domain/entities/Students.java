/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.bilan.co.domain.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.bilan.co.domain.enums.DocumentType;
import org.bilan.co.domain.enums.UserType;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
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
    @NamedQuery(name = "Students.findAll", query = "SELECT s FROM Students s"),
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
    @JsonIgnore
    @OneToMany(mappedBy = "idStudent")
    private List<ResolvedAnswerBy> resolvedAnswerByList;
    @JsonIgnore
    @OneToOne(mappedBy = "idStudent", cascade = CascadeType.ALL)
    private StudentStats studentStats;
    @JsonIgnore
    @OneToMany(mappedBy = "idStudent")
    private List<Evidences> evidencesList;

    @Transient
    private UserType userType = UserType.Student;

    public Students() {
        createdAt = new Date();
        modifiedAt = new Date();
    }

    public Students(String document, Date createdAt, Date modifiedAt) {
        this();
        this.document = document;
        this.createdAt = createdAt;
        this.modifiedAt = modifiedAt;
    }

    public Students(String name, String lastName, String document, DocumentType documentType, String email, String password,
                    List<ResolvedAnswerBy> resolvedAnswerByList, StudentStats studentStats, List<Evidences> evidencesList) {
        this();
        this.document = document;
        this.documentType = documentType;
        this.name = name;
        this.email = email;
        this.lastName = lastName;
        this.password = password;
        this.resolvedAnswerByList = resolvedAnswerByList;
        this.studentStats = studentStats;
        this.evidencesList = evidencesList;
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

    public UserType getUserType() {
        return userType;
    }

    public void setUserType(UserType userType) {
        this.userType = userType;
    }

    @XmlTransient
    public List<ResolvedAnswerBy> getResolvedAnswerByList() {
        return resolvedAnswerByList;
    }

    public void setResolvedAnswerByList(List<ResolvedAnswerBy> resolvedAnswerByList) {
        this.resolvedAnswerByList = resolvedAnswerByList;
    }

    @XmlTransient
    public StudentStats getStudentStats() {
        return studentStats;
    }

    public void setStudentStats(StudentStats studentStats) {
        this.studentStats = studentStats;
    }

    @XmlTransient
    public List<Evidences> getEvidencesList() {
        return evidencesList;
    }

    public void setEvidencesList(List<Evidences> evidencesList) {
        this.evidencesList = evidencesList;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Students)) {
            return false;
        }
        Students other = (Students) object;
        return (this.document != null || other.document == null) && (this.document == null || this.document.equals(other.document));
    }

    @Override
    public String toString() {
        return "org.bilan.co.domain.entities.Students[ id=" + document + " ]";
    }

}
