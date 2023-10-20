package org.bilan.co.domain.dtos.user;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.validation.constraints.Max;
import lombok.Data;
import org.bilan.co.domain.dtos.StudentChallengesDto;

import java.util.Date;
import java.util.List;
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class UserStatsDto {

    @Max(15)
    private int generalTotems;
    @Max(3)
    private int analyticalTotems;
    @Max(3)
    private int criticalTotems;
    @Max(5)
    private int currentCycle;
    @Max(3)
    private int currentSpirits;
    private String tribePoints;
    private Date lastTotemUpdate;
    private List<StudentChallengesDto> studentChallenges;


}
