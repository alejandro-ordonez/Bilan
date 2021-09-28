package org.bilan.co.domain.entities;

import lombok.Data;

import javax.persistence.*;
import java.util.List;

@Entity
@Data
public class Contexts {

    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue
    private Integer id;

    @Lob
    @Column(name = "content")
    private String content;

    @OneToMany(mappedBy = "contexts")
    private List<Questions> questions;
}
