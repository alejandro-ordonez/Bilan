package org.bilan.co.domain.entities;

import lombok.Data;

import javax.persistence.*;

@Table(name = "comment")
@Entity
@Data
public class Comment {

    @Id
    @GeneratedValue
    private Integer id;

    private String content;

    @ManyToOne
    private UserInfo author;

    @ManyToOne
    private Post post;
}