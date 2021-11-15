package org.bilan.co.domain.entities;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Table(name = "comment")
@Entity
@Data
public class Comment extends Post{
    @ManyToOne
    private Post post;
}