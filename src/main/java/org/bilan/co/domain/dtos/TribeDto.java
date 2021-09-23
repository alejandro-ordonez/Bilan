package org.bilan.co.domain.dtos;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.bilan.co.domain.entities.Tribes;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@JsonIgnoreProperties(ignoreUnknown = true)
@EqualsAndHashCode
@Data
public final class TribeDto {

    private String name;
    private String culture;
    private String element;
    private Integer adjacentTribe;
    private Integer oppositeTribe;
    private List<ActionDto> actions;
}
