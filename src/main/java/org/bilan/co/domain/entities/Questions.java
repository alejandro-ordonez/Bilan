/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.bilan.co.domain.entities;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;
import java.util.List;

@Entity
@XmlRootElement
@NoArgsConstructor
@Data
@EqualsAndHashCode
@ToString
public class Questions implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue
    @Basic(optional = false)
    private Integer id;

    @Size(max = 255)
    private String title;

    @Lob
    @Size(max = 65535)
    private String statement;

    @Lob
    @Size(max = 65535)
    @Column(name = "short_statements")
    private String shortStatement;

    @Column
    private Integer difficulty;

    @Size(max = 255)
    @Column(name = "clue_chaman")
    private String clueChaman;

    @Column
    private String grade;

    @Lob
    @Column
    private String justification;

    @Lob
    @Column
    private String statements;

    @Lob
    @Column(name = "error_message")
    private String errorMessage;

    @Column(name = "is_multi")
    private Boolean isMulti;

    @OneToMany(mappedBy = "idQuestion", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<Answers> answersList;

    @JoinColumn(name = "id_tribe", referencedColumnName = "id")
    @ManyToOne
    private Tribes idTribe;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "context_id")
    private Contexts contexts;

    @OneToMany(mappedBy = "idQuestion", fetch = FetchType.LAZY)
    private List<ResolvedAnswerBy> resolvedAnswerByList;
}
