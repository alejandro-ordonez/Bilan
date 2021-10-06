package org.bilan.co.domain.entities;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

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
@EqualsAndHashCode
@ToString
public class Evidences implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue
    @Basic(optional = false)
    private Integer id;

    @Size(max = 255)
    @Column(name = "url_file")
    private String urlFile;

    @Basic(optional = false)
    @NotNull
    @Column(name = "created_at")
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdAt;

    @JoinColumn(name = "id_student", referencedColumnName = "document")
    @ManyToOne
    private Students idStudent;

    @JoinColumn(name = "id_activiy", referencedColumnName = "id")
    @ManyToOne
    private Activities idActivity;
}
