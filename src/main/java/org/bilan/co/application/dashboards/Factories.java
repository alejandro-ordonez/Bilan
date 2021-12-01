package org.bilan.co.application.dashboards;

import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.bilan.co.domain.dtos.college.*;
import org.bilan.co.domain.enums.PerformanceScale;
import org.bilan.co.domain.enums.Tribe;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import static org.bilan.co.domain.dtos.college.GovernmentDashboardDto.StateDashboardDto;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
final class Factories {

    private static final Function<Tribe, Predicate<IPerformanceActivities>> BY_TRIBE_ACTIVITIES = tribe -> (entity) -> entity.getId() == tribe.getId();
    private static final Function<Tribe, Predicate<IPerformanceGame>> BY_TRIBE_GAMES = tribe -> (entity) -> entity.getId() == tribe.getId();
    private static final Function<String, Function<Tribe, Predicate<IPerformanceActivities>>> BY_TRIBE_AND_STATE_ACTIVITIES = state -> tribe -> BY_TRIBE_ACTIVITIES.apply(tribe).and((entity) -> state.equals(entity.getState()));
    private static final Function<String, Function<Tribe, Predicate<IPerformanceGame>>> BY_TRIBE_AND_STATE_GAMES = state -> tribe -> BY_TRIBE_GAMES.apply(tribe).and((entity) -> state.equals(entity.getState()));

    public static CollegeDashboardDto newCollegeDashboard(List<IPerformanceActivities> performanceActivities,
                                                          List<IPerformanceGame> performanceGames,
                                                          Integer numberOfStudents) {
        DataFilter dataFilter = new DataFilter();
        dataFilter.setActivities(performanceActivities);
        dataFilter.setPerformanceGames(performanceGames);
        dataFilter.setActivitiesPredicate(BY_TRIBE_ACTIVITIES);
        dataFilter.setGamesPredicate(BY_TRIBE_GAMES);

        CollegeDashboardDto collegeDashboardDto = new CollegeDashboardDto();
        collegeDashboardDto.setModules(buildModulesByTribe(dataFilter));
        collegeDashboardDto.setTotalForumAnswers(0);
        collegeDashboardDto.setStudents(numberOfStudents);
        collegeDashboardDto.setTimeInApp(10);
        return collegeDashboardDto;
    }

    private static List<ModuleDashboardDto> buildModulesByTribe(DataFilter dataFilter) {
        return Arrays.stream(Tribe.values())
                .parallel()
                .map(tribe -> getModuleDashboardDto(dataFilter, tribe))
                .collect(Collectors.toList());
    }

    @NotNull
    private static ModuleDashboardDto getModuleDashboardDto(DataFilter dataFilter, Tribe tribe) {
        float performanceActivityScore = activityScore(dataFilter.getActivities(), dataFilter.getActivitiesPredicate().apply(tribe));
        float performanceGameScore = gameScore(dataFilter.getPerformanceGames(), dataFilter.getGamesPredicate().apply(tribe));
        return Factories.newModuleDashboard(tribe, performanceActivityScore, performanceGameScore);
    }

    private static ModuleDashboardDto newModuleDashboard(Tribe tribe, float performanceActivityScore, float performanceGameScore) {
        ModuleDashboardDto moduleDashboard = new ModuleDashboardDto();
        moduleDashboard.setId(tribe.getId());
        moduleDashboard.setName(tribe.getName());
        moduleDashboard.setPerformanceActivityScore(performanceActivityScore);
        moduleDashboard.setPerformanceGameScore(PerformanceScale.calculatePerformanceGameScale(tribe, performanceGameScore));
        return moduleDashboard;
    }

    private static float gameScore(List<IPerformanceGame> data, Predicate<IPerformanceGame> filter) {
        return data.stream().filter(filter).findFirst().map(IPerformanceGame::getScorePerformance)
                .orElse(0F);
    }

    private static float activityScore(List<IPerformanceActivities> data, Predicate<IPerformanceActivities> filter) {
        return data.stream().filter(filter).findFirst().map(IPerformanceActivities::getScorePerformanceActivity)
                .orElse(0F);
    }

    public static GovernmentDashboardDto newGovernmentDashboard(List<IPerformanceActivities> performanceActivities,
                                                                List<IPerformanceGame> performanceGames,
                                                                List<String> states) {
        List<StateDashboardDto> stateDashboardDtos = new ArrayList<>();
        for (String state : states) {
            DataFilter dataFilter = new DataFilter();
            dataFilter.setActivities(performanceActivities);
            dataFilter.setPerformanceGames(performanceGames);
            dataFilter.setActivitiesPredicate(BY_TRIBE_AND_STATE_ACTIVITIES.apply(state));
            dataFilter.setGamesPredicate(BY_TRIBE_AND_STATE_GAMES.apply(state));
            StateDashboardDto dashboardDto = new StateDashboardDto();
            dashboardDto.setName(state);
            dashboardDto.setModules(buildModulesByTribe(dataFilter));
            stateDashboardDtos.add(dashboardDto);
        }
        GovernmentDashboardDto dashboardDto = new GovernmentDashboardDto();
        dashboardDto.setTimeInApp(0);
        dashboardDto.setStudents(0);
        dashboardDto.setStates(stateDashboardDtos);
        return dashboardDto;
    }

    public static GovernmentDashboardDto newGovernmentStateDashboard(List<IPerformanceActivities> performanceActivities,
                                                                     List<IPerformanceGame> performanceGames) {
        return null;
    }

    @Data
    @NoArgsConstructor
    private static class DataFilter {
        private List<IPerformanceActivities> activities;
        private Function<Tribe, Predicate<IPerformanceActivities>> activitiesPredicate;
        private List<IPerformanceGame> performanceGames;
        private Function<Tribe, Predicate<IPerformanceGame>> gamesPredicate;
    }
}
