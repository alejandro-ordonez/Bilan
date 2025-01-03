/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.bilan.co.domain.entities;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;
import java.util.Date;

/**
 * @author Manuel Alejandro
 */
@Entity
@Table(name = "resolved_answer_by")
@XmlRootElement
@NoArgsConstructor
@Data
@EqualsAndHashCode
@ToString
@EntityListeners(AuditingEntityListener.class)
public class ResolvedAnswerBy implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "sessions_id")
    private Sessions sessions;

    @CreatedDate
    private Date createdAt;

    @JoinColumn(name = "id_question", referencedColumnName = "id")
    @ManyToOne
    private Questions idQuestion;

    @JoinColumn(name = "id_answer", referencedColumnName = "id")
    @ManyToOne
    private Answers idAnswer;

    @OneToOne
    @JoinColumn(name = "tribe_id_id")
    private Tribes tribeId;
    @ManyToOne
    @JoinColumn(name = "student_id_document")
    private Students studentId;
}