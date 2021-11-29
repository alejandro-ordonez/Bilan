package org.bilan.co.domain.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum PerformanceScale {
    BAJA(1), MINIMO(2), SATISFACTORIO(3), ALTO(4);
    private final int scale;
}
