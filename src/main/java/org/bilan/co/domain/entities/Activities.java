package org.bilan.co.domain.entities;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;
import java.util.List;

/**
 * @author Manuel Alejandro
 */
@Entity
@XmlRootElement
@NoArgsConstructor
@Data
@EqualsAndHashCode
@ToString
public class Activities implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue
    @Basic(optional = false)
    private Integer id;

    @Column(name = "id_tribe")
    private Integer idTribe;

    @Lob
    @Size(max = 65535)
    private String description;

    @OneToMany(mappedBy = "idActivity")
    private List<Evidences> evidencesList;
}
