/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.bilan.co.domain.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;
import javax.persistence.*;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * @author Manuel Alejandro
 */
@Entity
@Table
@XmlRootElement
@NoArgsConstructor
@Data
@EqualsAndHashCode
@ToString
public class StudentActions implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    private Integer id;

    @Column(name = "current_points")
    private Integer currentPoints;

    @JoinColumn(name = "id_student_stat", referencedColumnName = "id")
    @ManyToOne(targetEntity = StudentStats.class)
    @JsonIgnore
    private StudentStats idStudentStat;

    @JoinColumn(name = "id_challenge", referencedColumnName = "id")
    @OneToOne(mappedBy = "studentChallenges", cascade = CascadeType.ALL)
    private Actions idAction;
}
