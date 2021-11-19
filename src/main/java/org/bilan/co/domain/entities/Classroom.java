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

    @ManyToOne
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