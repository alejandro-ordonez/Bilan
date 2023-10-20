package org.bilan.co.domain.entities;


import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Table(name = "departamento_municipio")
@Entity
@Data
@NoArgsConstructor
public class StateMunicipality {

    @Id
    private Integer id;

    @Column(name = "departamento")
    private String state;

    @Column(name = "cod_dane_municipio")
    private String codDaneMunicipality;

    @Column(name = "municipio")
    private String municipality;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "stateMunicipality")
    private List<Colleges> colleges;
}
