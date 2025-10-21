package org.bilan.co.domain.dtos.game;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import lombok.EqualsAndHashCode;


@JsonIgnoreProperties(ignoreUnknown = true)
@EqualsAndHashCode
@Data
public class BaseSatsDto {

    private Integer generalTotems = 4;
    private Integer analyticalTotems = 0;
    private Integer criticalTotems = 0;
    private Integer currentCycle = 1;
    private Integer currentSpirits = 3;
    private String tribesBalance;
    private Float timeInGame = 0f;
}
