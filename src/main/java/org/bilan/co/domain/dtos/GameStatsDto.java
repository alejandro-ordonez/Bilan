package org.bilan.co.domain.dtos;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
@EqualsAndHashCode
@Data
public class GameStatsDto extends BaseSatsDto{
    private List<TribesPoints> tribesPoints;
}
