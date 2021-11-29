package org.bilan.co.application.college;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.bilan.co.domain.dtos.college.CollegeDashboardDto;
import org.bilan.co.domain.dtos.college.CollegeDashboardDto.ModuleDashboard;
import org.bilan.co.domain.dtos.college.IModuleDashboard;
import org.bilan.co.domain.enums.PerformanceScale;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
final class Factories {

    private static final List<Integer> DEFAULT_MODULES = Arrays.asList(2, 3, 4, 5);

    public static CollegeDashboardDto newCollegeDashboard(List<IModuleDashboard> modules) {
        CollegeDashboardDto collegeDashboardDto = new CollegeDashboardDto();
        collegeDashboardDto.setModules(modules.stream().map(Factories::assignScale).collect(Collectors.toList()));
        collegeDashboardDto.setTotalForumAnswers(0); //TODO: Temporal
        collegeDashboardDto.setStudents(10); //TODO: Get students by colleges
        collegeDashboardDto.setTimeInApp(10); //TODO: Pending to determine how to get this value
        return collegeDashboardDto;
    }

    private static ModuleDashboard assignScale(IModuleDashboard module) {
        ModuleDashboard moduleDashboard = new ModuleDashboard();
        moduleDashboard.setId(module.getId());
        moduleDashboard.setName(module.getName());
        moduleDashboard.setPoints(module.getPoints());
        moduleDashboard.setScore(module.getScore());
        if (DEFAULT_MODULES.contains(module.getId())) {
            moduleDashboard.setScale(defaultScale(module.getScore().intValue()));
        } else {
            moduleDashboard.setScale(scaleSocialEmotional(module.getScore().intValue()));
        }
        return moduleDashboard;
    }

    private static PerformanceScale scaleSocialEmotional(Integer score) {
        if (score <= 10) {
            return PerformanceScale.BAJA;
        }
        if (score <= 15) {
            return PerformanceScale.MINIMO;
        }
        if (score <= 20) {
            return PerformanceScale.SATISFACTORIO;
        }
        return PerformanceScale.ALTO;
    }

    private static PerformanceScale defaultScale(Integer score) {
        if (score <= 35) {
            return PerformanceScale.BAJA;
        }
        if (score <= 50) {
            return PerformanceScale.MINIMO;
        }
        if (score <= 65) {
            return PerformanceScale.SATISFACTORIO;
        }
        return PerformanceScale.ALTO;
    }
}
