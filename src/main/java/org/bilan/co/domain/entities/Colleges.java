package org.bilan.co.domain.entities;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Table(name = "colleges")
@Entity
@Data
@NoArgsConstructor
public class Colleges {

    @Id
    private Integer id;

    @Column(name = "nombre_establecimiento")
    private String name;

    @Column(name = "codigo_dane_sede")
    private String campusCodeDane;

    @Column(name = "nombre_sede")
    private String campusName;

    @ManyToOne()
    @JoinColumn(name = "dep_mun_id")
    private StateMunicipality stateMunicipality;

    @OneToMany(mappedBy = "college")
    private List<Classroom> classrooms;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "colleges")
    private List<Students> students;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "college")
    private List<Teachers> teachers;

    public String getCollegeCampusName() {
        return this.getName() + " - " + this.getCampusName();
    }
}