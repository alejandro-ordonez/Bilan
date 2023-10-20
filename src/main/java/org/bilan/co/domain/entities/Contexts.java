package org.bilan.co.domain.entities;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Entity
@Data
public class Contexts {

    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Lob
    @Column(name = "content")
    private String content;

    @OneToMany(mappedBy = "contexts", fetch = FetchType.LAZY)
    private List<Questions> questions;
}
