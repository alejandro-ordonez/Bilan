package org.bilan.co.domain.dtos.general;

import lombok.Data;
import org.bilan.co.domain.projections.IMunicipality;

@Data
public class CityDto {
    int cityId;
    String name;

    public CityDto(IMunicipality municipality) {
        cityId = municipality.getId();
        name = municipality.getName();
    }
}
