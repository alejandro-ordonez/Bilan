package org.bilan.co.domain.dtos.game;

import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode
@Data
public class ChallengesDto {
    private Integer id;
    private String name;
    private Integer cost;
}
