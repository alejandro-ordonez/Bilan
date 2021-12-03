package org.bilan.co.domain.entities;

import lombok.Data;
import org.bilan.co.domain.enums.DocumentType;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Date;
import java.util.List;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@Data
@EntityListeners(AuditingEntityListener.class)
public class UserInfo {

    @Id
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 255)
    @Column(unique = true)
    protected String document;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "document_type")
    protected DocumentType documentType;

    @Size(max = 255)
    protected String name;

    @Size(max = 255)
    @Column(name = "last_name")
    protected String lastName;

    @Column(name = "position_name")
    private String positionName;

    @Size(max = 255)
    protected String email;

    @Column(name = "is_enabled")
    private Boolean isEnabled;

    @Column(name = "confirmed")
    private Boolean confirmed;

    @Size(max = 255)
    protected String password;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "created_at")
    @CreatedDate
    protected Date createdAt = new Date();

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "modified_at")
    @LastModifiedDate
    protected Date modifiedAt = new Date();

    @ManyToOne
    @JoinColumn(name = "role_id")
    private Roles role;

    @OneToMany(mappedBy = "author", fetch = FetchType.LAZY)
    private List<Post> posts;
}
