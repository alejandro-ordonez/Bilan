package org.bilan.co.domain.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;
import java.util.List;

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
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    private Integer id;

    @Size(max = 255)
    private String name;

    @Size(max = 255)
    private String type;

    private Integer cost;
    private Integer timer;

    @JsonIgnore
    @OneToMany(mappedBy = "challenges", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    List<Sessions> sessionsList;
}
