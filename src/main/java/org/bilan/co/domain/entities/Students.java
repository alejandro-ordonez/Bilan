package org.bilan.co.domain.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.bilan.co.domain.enums.DocumentType;
import org.bilan.co.domain.enums.UserType;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * @author Manuel Alejandro
 */
@Entity
@XmlRootElement
@EqualsAndHashCode
@ToString
@Data
@NoArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public class Students implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 255)
    @Column(unique = true)
    protected String document;

    @Enumerated(EnumType.STRING)
    @Column(name = "document_type")
    protected DocumentType documentType;

    @Size(max = 255)
    protected String name;

    @Size(max = 255)
    protected String email;

    @Size(max = 255)
    protected String password;

    @Size(max = 255)
    @Column(name = "last_name")
    protected String lastName;

    @Column(name = "created_at")
    @CreatedDate
    protected Date createdAt;

    @Column(name = "modified_at")
    @LastModifiedDate
    protected Date modifiedAt;



    @NotNull
    @Column(name = "cod_grade")
    private String grade;

    @NotNull
    @Column(name = "enabled")
    private Boolean enabled;

    @JsonIgnore
    @OneToMany(mappedBy = "studentId")
    private List<ResolvedAnswerBy> resolvedAnswerByList;

    @JsonIgnore
    @OneToOne(mappedBy = "idStudent", cascade = CascadeType.ALL)
    private StudentStats studentStats;

    @JsonIgnore
    @OneToMany(mappedBy = "idStudent")
    private List<Evidences> evidencesList;

    @JsonIgnore
    @OneToMany(mappedBy = "students")
    private List<Sessions> sessions;

    @Transient
    private UserType userType = UserType.Student;

    public Students(String name, String lastName, String document, DocumentType documentType, String email, String password,
                    List<ResolvedAnswerBy> resolvedAnswerByList, StudentStats studentStats, List<Evidences> evidencesList) {
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
}
