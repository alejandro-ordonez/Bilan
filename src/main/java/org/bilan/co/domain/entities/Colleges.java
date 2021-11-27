package org.bilan.co.domain.entities;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Table(name = "colleges")
@Entity
@Data
@NoArgsConstructor
public class Colleges {

    @Id
    private Integer id;

    @Column(name = "secretaria")
    private String secretaria;

    @Column(name = "cod_dane")
    private String codDane;

    @Column(name = "nombre_establecimiento")
    private String name;

    @Column(name = "codigo_dane_sede")
    private String codDaneSede;

    @Column(name = "nombre_sede")
    private String nameSede;

    @ManyToOne()
    @JoinColumn(name = "dep_mun_id")
    private StateMunicipality stateMunicipality;

    @OneToMany(mappedBy = "college")
    private List<Classroom> classrooms;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "colleges")
    private List<Students> students;
}