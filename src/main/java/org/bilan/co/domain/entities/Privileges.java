package org.bilan.co.domain.entities;

import lombok.Data;

import javax.persistence.*;
import java.util.Collection;

@Entity
@Data
public class Privileges {
    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue
    private Integer id;

    private String name;

    @ManyToMany(mappedBy = "privileges")
    private Collection<Roles> roles;
}