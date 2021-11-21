package org.bilan.co.domain.entities;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.bilan.co.domain.enums.Phase;
import org.springframework.data.annotation.CreatedDate;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;
import java.util.Date;

/**
 * @author Manuel Alejandro
 */
@Entity
@XmlRootElement
@NoArgsConstructor
@Data
@ToString
public class Evidences implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue
    @Basic(optional = false)
    private Integer id;

    @CreatedDate
    private Date createdAt;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "phase", nullable = false)
    private Phase phase;

    @NotNull
    @Size(max = 255)
    @Column(name = "path", nullable = false)
    private String path;

    @NotNull
    @Size(max = 255)
    @Column(name = "file_name", nullable = false)
    private String fileName;

    @JoinColumn(name = "id_student", referencedColumnName = "document")
    @ManyToOne
    private Students idStudent;

    @JoinColumn(name = "id_tribe")
    @ManyToOne
    private Tribes tribe;
}
