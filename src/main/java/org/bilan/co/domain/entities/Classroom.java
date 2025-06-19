package org.bilan.co.domain.entities;

import jakarta.persistence.*;
import lombok.Data;

@Table(name = "classrooms")
@Entity
@Data
public class Classroom {
    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "teacher_id")
    private Teachers teacher;

    @ManyToOne
    @JoinColumn(name = "course_id")
    private Courses course;

    @ManyToOne
    @JoinColumn(name = "tribe_id")
    private Tribes tribe;

    @ManyToOne
    @JoinColumn(name = "college_id")
    private Colleges college;

    @Column(name = "grade")
    private String grade;
}