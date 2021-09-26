package org.bilan.co.domain.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.bilan.co.domain.enums.DocumentType;
import org.bilan.co.domain.enums.UserType;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
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

    @Pattern(regexp = "[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*@(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?", message = "Invalid email")
    @Size(max = 255)
    private String email;

    @Size(max = 255)
    private String password;

    @Size(max = 255)
    @Column(name = "last_name")
    private String lastName;

    private String grade;

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

    @NotNull
    @Column(name = "cod_grade")
    private String grade;

    @JsonIgnore
    @OneToMany(mappedBy = "idStudent")
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
}
