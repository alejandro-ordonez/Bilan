package org.bilan.co.domain.entities;


import lombok.Data;

import javax.persistence.*;
import java.util.List;

@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
@Entity
@Data
public class Post {

    @Id
    @GeneratedValue
    private Integer id;

    private String title;

    private String content;

    @ManyToOne
    private UserInfo author;

    @OneToMany(mappedBy = "post")
    private List<Comment> comments;
}
