package org.bilan.co.domain.entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.Basic;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;

/**
 * @author Manuel Alejandro
 */
@Entity
@XmlRootElement
@JsonIgnoreProperties(ignoreUnknown = true)
@NoArgsConstructor
@Data
@EqualsAndHashCode
@ToString
public class Challenges implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue
    @Basic(optional = false)
    private Integer id;

    @Size(max = 255)
    private String name;

    @Size(max = 255)
    private String type;

    private Integer cost;
    private Integer timer;
}
