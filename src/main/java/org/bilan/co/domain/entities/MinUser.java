package org.bilan.co.domain.entities;

import org.bilan.co.domain.enums.DocumentType;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Date;

@Entity
public class MinUser {
    @Id
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 255)
    @Column(unique = true)
    protected String document;

    @Enumerated(EnumType.STRING)
    @Column(name = "document_type")
    protected DocumentType documentType;

    @Size(max = 255)
    protected String name;

    @Size(max = 255)
    protected String email;

    @Size(max = 255)
    protected String password;

    @Size(max = 255)
    @Column(name = "last_name")
    protected String lastName;

    @Column(name = "created_at")
    @CreatedDate
    protected Date createdAt;

    @Column(name = "modified_at")
    @LastModifiedDate
    protected Date modifiedAt;
}