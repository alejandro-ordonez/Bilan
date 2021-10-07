package org.bilan.co.domain.dtos;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;

@JsonIgnoreProperties(ignoreUnknown = true)
@EqualsAndHashCode
@Data
@AllArgsConstructor
public final class TribeDto {

    private Integer id;
    private String name;
    private String culture;
    private String element;
    private Integer adjacentTribe;
    private Integer oppositeTribe;
}
