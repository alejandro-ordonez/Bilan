package org.bilan.co.domain.dtos;

import com.fasterxml.jackson.annotation.JsonIgnore;

public abstract class ImportIdentifier {
    @JsonIgnore
    public abstract String getIdentifier();
}
