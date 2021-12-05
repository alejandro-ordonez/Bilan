package org.bilan.co.domain.entities;

import lombok.Data;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.util.Date;

@Table(name = "comment")
@Entity
@Data
@EntityListeners(AuditingEntityListener.class)
public class Comment {

    @Id
    @GeneratedValue
    private Integer id;

    private String content;

    @CreatedDate
    private Date createdAt;

    @ManyToOne
    private UserInfo author;

    @ManyToOne
    private Post post;
}