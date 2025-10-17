package org.bilan.co.domain.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.bilan.co.domain.dtos.user.TeacherInfoImportDto;
import org.bilan.co.domain.enums.UserType;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serial;
import java.io.Serializable;
import java.util.List;

/**
 * @author Manuel Alejandro
 */
@Entity
@XmlRootElement
@NoArgsConstructor
@Data
@EqualsAndHashCode(callSuper = false)
@ToString
@EnableJpaAuditing
public class Teachers extends UserInfo implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @ManyToOne
    private Colleges college;

    @JsonIgnore
    @OneToMany(mappedBy = "teacher", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Classroom> classrooms;

    @Transient
    private UserType userType = UserType.Teacher;

    public static Teachers buildTeacher(TeacherInfoImportDto info) {
        Teachers teacher = new Teachers();

        teacher.setDocument(info.getDocument());
        teacher.setDocumentType(info.getDocumentType());
        teacher.setName(info.getName());
        teacher.setLastName(info.getLastName());
        teacher.setEmail(info.getEmail());
        return teacher;
    }
}
