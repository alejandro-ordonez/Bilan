package org.bilan.co.domain.dtos;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.bilan.co.domain.entities.Actions;

@NoArgsConstructor
@EqualsAndHashCode
@Data
public class ActionDto {

    private Integer id;
    private String name;
    private String imagePath;

    public ActionDto(Actions actions) {
        this.id = actions.getId();
        this.name = actions.getName();
        this.imagePath = actions.getImagePath();
    }
}
