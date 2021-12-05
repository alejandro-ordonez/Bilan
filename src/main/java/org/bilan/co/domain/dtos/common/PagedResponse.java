package org.bilan.co.domain.dtos.common;

import lombok.Data;
import org.bilan.co.domain.dtos.forums.PostDto;

import java.util.List;

@Data
public class PagedResponse<T> {
    private List<T> data;
    private Integer nPages;
}
