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
    private String id;

    @Column(name = "name")
    private String name;

    @OneToMany(mappedBy = "idCollege")
    private List<Classroom> classrooms;

}