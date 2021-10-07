package org.bilan.co.domain.dtos;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ActionsPoints{
    private Integer actionId;
    private Long score;
    @JsonIgnore
    private Integer tribeId;
}