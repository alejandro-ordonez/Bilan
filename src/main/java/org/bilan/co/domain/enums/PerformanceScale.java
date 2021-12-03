package org.bilan.co.domain.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;
import java.util.List;

@AllArgsConstructor
@Getter
public enum PerformanceScale {
    LOW(1, 10, 35),
    MINIMUM(2, 15, 50),
    SUCCESSFUL(3, 20, 65),
    HIGH(4, Integer.MAX_VALUE, Integer.MAX_VALUE);

    private final int scale;
    private final int boundSocialEmotionalScore;
    private final int boundDefaultScore;

    private static final List<Tribe> DEFAULT_TRIBES = Arrays.asList(Tribe.LANGUAGE, Tribe.NATURAL_SCIENCE,
            Tribe.CITIZEN_COMPETENCE, Tribe.MATH);

    public static int calculatePerformanceGameScale(Tribe tribe, Float performanceGameScore) {
        if (!DEFAULT_TRIBES.contains(tribe)) {
            return defaultScale(performanceGameScore.intValue()).getScale();
        }
        return scaleSocialEmotional(performanceGameScore.intValue()).getScale();
    }

    private static PerformanceScale scaleSocialEmotional(Integer score) {
        return Arrays.stream(PerformanceScale.values())
                .filter(scale -> score <= scale.boundSocialEmotionalScore)
                .findFirst()
                .orElse(PerformanceScale.HIGH);
    }

    private static PerformanceScale defaultScale(Integer score) {
        return Arrays.stream(PerformanceScale.values())
                .filter(scale -> score <= scale.boundDefaultScore)
                .findFirst()
                .orElse(PerformanceScale.HIGH);
    }
}
