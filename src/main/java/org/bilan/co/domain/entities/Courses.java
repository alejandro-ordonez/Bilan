package org.bilan.co.domain.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import javax.persistence.*;
import java.util.List;

@Table(name = "courses")
@Entity
@Data
public class Courses {

    @Id
    @Column(name = "id", nullable = false)
    private Integer id;

    @Column(name = "name")
    private String name;

    @JsonIgnore
    @OneToMany(cascade = CascadeType.ALL)
    private List<Classroom> classRooms;

    @JsonIgnore
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "courses")
    private List<Students> students;

}