package org.bilan.co.domain.entities;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;
import javax.persistence.*;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * @author Manuel Alejandro
 */
@Entity
@XmlRootElement
@NoArgsConstructor
@Data
@EqualsAndHashCode
@ToString
public class Classrooms implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue
    @Basic(optional = false)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY)
    private Tribes tribe;

    @JoinColumn(name = "id_course", referencedColumnName = "id")
    @ManyToOne
    private Courses idCourse;

    @JoinColumn(name = "id_teacher", referencedColumnName = "id")
    @ManyToOne
    private Teachers idTeacher;
}
