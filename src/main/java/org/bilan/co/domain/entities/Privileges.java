package org.bilan.co.domain.entities;

import lombok.Data;

import javax.persistence.*;

@Table(name = "privileges")
@Entity
@Data
public class Privileges {
    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "roles_id")
    private Roles roles;
}