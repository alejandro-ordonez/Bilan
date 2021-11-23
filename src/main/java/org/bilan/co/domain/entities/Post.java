package org.bilan.co.domain.entities;


import lombok.Data;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
@Entity
@Data
@EntityListeners(AuditingEntityListener.class)
public class Post {

    @Id
    @GeneratedValue
    private Integer id;

    private String title;

    private String content;

    @CreatedDate
    private Date createdAt;

    @ManyToOne
    private UserInfo author;

    @OneToMany(mappedBy = "post")
    private List<Comment> comments;
}
