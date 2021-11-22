package org.bilan.co.domain.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.bilan.co.domain.enums.DocumentType;
import org.bilan.co.domain.enums.UserType;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;
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
public class Students extends UserInfo implements Serializable {

    private static final long serialVersionUID = 1L;

    @Column(name = "cod_grade")
    private String grade;

    @ManyToOne
    @JoinColumn(name = "course_id", referencedColumnName = "id")
    private Courses courses;

    @ManyToOne
    @JoinColumn(name = "college_id",referencedColumnName = "id")
    private Colleges colleges;

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
