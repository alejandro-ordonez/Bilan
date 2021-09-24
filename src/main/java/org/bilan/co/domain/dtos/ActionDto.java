package org.bilan.co.domain.dtos;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.bilan.co.domain.entities.Actions;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
@EqualsAndHashCode
@Data
public class ActionDto {

    private Integer id;
    private String name;
    private String imagePath;
    private Integer currentPoints = 0;
    private List<ChallengesDto> challenge;
}
