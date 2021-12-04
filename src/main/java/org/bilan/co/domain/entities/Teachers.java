package org.bilan.co.domain.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.bilan.co.domain.enums.UserType;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

import javax.persistence.*;
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
@EnableJpaAuditing
public class Teachers extends UserInfo implements Serializable {

    private static final long serialVersionUID = 1L;

    @Column(name = "cod_dane_mun_residencia")
    private String codDaneMinResidencia;

    @Column(name = "cod_dane")
    private String codDane;

    @Column(name = "cod_dane_sede")
    private String codDaneSede;

    @JsonIgnore
    @OneToMany(mappedBy = "teacher", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Classroom> classrooms;

    @Transient
    private UserType userType = UserType.Teacher;
}
