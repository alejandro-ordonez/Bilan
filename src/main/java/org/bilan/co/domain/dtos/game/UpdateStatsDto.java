package org.bilan.co.domain.dtos.game;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
@EqualsAndHashCode(callSuper = false)
@Data
public class UpdateStatsDto extends BaseSatsDto {
    List<UpdateActionsPointsDto> actionsPoints;
}
