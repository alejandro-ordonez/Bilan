package org.bilan.co.domain.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class TribesPoints{
    private Integer id;
    private Long score;
    private List<ActionsPoints> actionsPoints;
}