package org.bilan.co.domain.dtos.dashboard;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import static com.fasterxml.jackson.annotation.JsonInclude.Include.NON_NULL;

@Data
@JsonInclude(NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class TribeSummaryDto {
    private String id;
    private String title;
    private String name;
    private String logins;
    private Integer performanceActivityScore;
    private Integer performanceGameScore;

    public String toCommaSeparated(){
        return title + "," + name + "," + logins + "," + performanceActivityScore + "," + performanceGameScore;
    }
}
