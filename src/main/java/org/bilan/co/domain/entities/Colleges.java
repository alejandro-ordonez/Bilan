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

    @Column(name = "name")
    private String name;

    @Column(name = "state")
    private String state;

    @Column(name = "cod_dane")
    private String codDane;

    @OneToMany(mappedBy = "college")
    private List<Classroom> classrooms;

}