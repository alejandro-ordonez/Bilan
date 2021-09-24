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

import java.io.Serializable;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 * @author Manuel Alejandro
 */
@Entity
@XmlRootElement
@NoArgsConstructor
@Data
@EqualsAndHashCode
@ToString
public class Questions implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    private Integer id;

    @Size(max = 255)
    private String title;

    @Lob
    @Size(max = 65535)
    private String statments;

    @Lob
    @Size(max = 65535)
    @Column(name = "short_statments")
    private String shortStatments;
    private Integer difficulty;

    @Size(max = 255)
    @Column(name = "clue_chaman")
    private String clueChaman;

    @OneToMany(mappedBy = "idQuestion")
    private List<Answers> answersList;

    @JoinColumn(name = "id_challenge", referencedColumnName = "id")
    @ManyToOne
    private Tribes idTribe;

    @OneToMany(mappedBy = "idQuestion")
    private List<ResolvedAnswerBy> resolvedAnswerByList;
}
