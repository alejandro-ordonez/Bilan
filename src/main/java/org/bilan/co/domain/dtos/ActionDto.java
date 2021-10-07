package org.bilan.co.domain.dtos;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import lombok.EqualsAndHashCode;

@JsonIgnoreProperties(ignoreUnknown = true)
@EqualsAndHashCode
@Data
public class ActionDto {

    private Integer id;
    private String name;
}
