package org.bilan.co.domain.utils;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Tuple<T, R> {
    T value1;
    R value2;

    public static <T, R> Tuple<T, R> of(T left, R right) {
        return new Tuple<>(left, right);
    }
}
