package org.bilan.co.domain.utils;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Tuple <T, R>{
    T value1;
    R value2;
}
