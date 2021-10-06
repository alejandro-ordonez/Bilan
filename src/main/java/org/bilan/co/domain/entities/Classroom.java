package org.bilan.co.domain.entities;

import lombok.Data;

import javax.persistence.*;

@Table(name = "classrooms")
@Entity
@Data
public class Classroom {
    @Id
    @Column(name = "id", nullable = false)
    private Integer id;

    @Column(name = "groups")
    private String groups;

    @Column(name = "grade")
    private String grade;

    @ManyToOne(optional = false)
    @JoinColumn(name = "id_college", nullable = false)
    private Colleges idCollege;
}