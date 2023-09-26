package org.bilan.co.domain.dtos.general;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class StateDto {
    String stateName;
    List<CityDto> cities;
}
