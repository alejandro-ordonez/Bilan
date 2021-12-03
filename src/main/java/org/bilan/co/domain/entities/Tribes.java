/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.bilan.co.domain.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import org.hibernate.Hibernate;

import javax.persistence.*;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;
import java.util.List;
import java.util.Objects;

/**
 * @author Manuel Alejandro
 */
@Entity
@XmlRootElement
@NoArgsConstructor
@Getter
@Setter
@ToString
public class Tribes implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue
    @Basic(optional = false)
    private Integer id;

    @Size(max = 255)
    private String name;

    @Size(max = 255)
    private String culture;

    @Size(max = 255)
    private String element;

    @JoinColumn(name = "opposite_tribe_id", referencedColumnName = "id")
    @ManyToOne
    private Tribes oppositeTribeId;

    @JoinColumn(name = "adjacent_tribe_id", referencedColumnName = "id")
    @ManyToOne
    private Tribes adjacentTribeId;

    @JsonIgnore
    @OneToMany(mappedBy = "idTribe", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @ToString.Exclude
    private List<Questions> questions;

    @JsonIgnore
    @OneToMany(mappedBy = "tribeId", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @ToString.Exclude
    private List<ResolvedAnswerBy> resolvedAnswerByList;

    @JsonIgnore
    @OneToMany(mappedBy = "tribe", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @ToString.Exclude
    private List<Classroom> classrooms;

    @JsonIgnore
    @OneToMany(mappedBy = "tribe", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @ToString.Exclude
    private List<Evidences> evidences;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        Tribes tribes = (Tribes) o;
        return id != null && Objects.equals(id, tribes.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
