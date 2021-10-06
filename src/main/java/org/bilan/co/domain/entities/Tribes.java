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

/**
 * @author Manuel Alejandro
 */
@Entity
@XmlRootElement
@NoArgsConstructor
@Data
@EqualsAndHashCode
@ToString
public class Tribes implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue
    @Basic(optional = false)
    private Integer id;

    @Size(max = 255)
    private String name;

    @Size(max = 255)
    private String culture;

    @Size(max = 255)
    private String element;

    @JoinColumn(name = "opposite_tribe_id", referencedColumnName = "id")
    @ManyToOne
    private Tribes oppositeTribeId;

    @JoinColumn(name = "adjacent_tribe_id", referencedColumnName = "id")
    @ManyToOne
    private Tribes adjacentTribeId;

    @OneToMany(mappedBy = "tribe", cascade = CascadeType.ALL)
    private List<Actions> actions;

    @OneToMany(mappedBy = "idTribe", cascade = CascadeType.ALL)
    private List<Questions> questions;
}
