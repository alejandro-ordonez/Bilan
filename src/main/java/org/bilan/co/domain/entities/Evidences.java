package org.bilan.co.domain.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.bilan.co.domain.enums.Phase;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * @author Manuel Alejandro
 */
@Entity
@XmlRootElement
@NoArgsConstructor
@Data
@ToString
@EntityListeners(AuditingEntityListener.class)
public class Evidences implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    private Long id;

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

    @NotNull
    @Size(max = 255)
    @Column(name = "file_type", nullable = false)
    private String fileType;

    @JoinColumn(name = "id_student", referencedColumnName = "document")
    @ManyToOne
    private Students idStudent;

    @JoinColumn(name = "id_tribe")
    @ManyToOne
    private Tribes tribe;

    @OneToMany(mappedBy = "evidence", fetch = FetchType.LAZY)
    private List<Evaluation> evaluations;
}
