package org.bilan.co.application.dashboards;

import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.bilan.co.domain.dtos.dashboard.CollegeDashboardDto;
import org.bilan.co.domain.dtos.dashboard.GovernmentDashboardDto;
import org.bilan.co.domain.dtos.dashboard.ModuleDashboardDto;
import org.bilan.co.domain.enums.Tribe;
import org.bilan.co.domain.projections.ICollege;
import org.bilan.co.domain.projections.IPerformanceActivity;
import org.bilan.co.domain.projections.IPerformanceGame;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import static org.bilan.co.domain.dtos.dashboard.GovernmentDashboardDto.StateDashboardDto;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
final class Factories {

    private static final Function<Tribe, Predicate<IPerformanceActivity>> BY_TRIBE_ACTIVITIES = tribe -> (entity) -> entity.getId() == tribe.getId();
    private static final Function<Tribe, Predicate<IPerformanceGame>> BY_TRIBE_GAMES = tribe -> (entity) -> entity.getId() == tribe.getId();
    private static final Function<String, Function<Tribe, Predicate<IPerformanceActivity>>> BY_TRIBE_AND_STATE_ACTIVITIES = state -> tribe -> BY_TRIBE_ACTIVITIES.apply(tribe).and((entity) -> state.equals(entity.getState()));
    private static final Function<String, Function<Tribe, Predicate<IPerformanceGame>>> BY_TRIBE_AND_STATE_GAMES = state -> tribe -> BY_TRIBE_GAMES.apply(tribe).and((entity) -> state.equals(entity.getState()));
    private static final Function<String, Function<Tribe, Predicate<IPerformanceActivity>>> BY_CODE_DANE_ACTIVITIES = codeDane -> tribe -> BY_TRIBE_ACTIVITIES.apply(tribe).and((entity) -> codeDane.equals(entity.getCodeDane()));
    private static final Function<String, Function<Tribe, Predicate<IPerformanceGame>>> BY_CODE_DANE_GAMES = codeDane -> tribe -> BY_TRIBE_GAMES.apply(tribe).and((entity) -> codeDane.equals(entity.getCodeDane()));


    public static CollegeDashboardDto newCollegeDashboard(CollegeDashboard info) {
        DataFilter dataFilter = new DataFilter();
        dataFilter.setActivities(info.getPerformanceActivities());
        dataFilter.setPerformanceGames(info.getPerformanceGames());
        dataFilter.setActivitiesPredicate(BY_TRIBE_ACTIVITIES);
        dataFilter.setGamesPredicate(BY_TRIBE_GAMES);

        CollegeDashboardDto collegeDashboardDto = new CollegeDashboardDto();
        collegeDashboardDto.setData(buildModulesByTribe(dataFilter));
        collegeDashboardDto.setTotalForumAnswers(0);
        collegeDashboardDto.setStudents(info.getNumberOfStudents());
        collegeDashboardDto.setTimeInApp(10);
        return collegeDashboardDto;
    }

    public static GovernmentDashboardDto newGovernmentDashboard(GovernmentDashboard info) {
        DataFilter dataFilter = new DataFilter();
        dataFilter.setActivities(info.getPerformanceActivities());
        dataFilter.setPerformanceGames(info.getPerformanceGames());
        List<StateDashboardDto> stateDashboard = new ArrayList<>();
        for (String state : info.getStates()) {
            dataFilter.setActivitiesPredicate(BY_TRIBE_AND_STATE_ACTIVITIES.apply(state));
            dataFilter.setGamesPredicate(BY_TRIBE_AND_STATE_GAMES.apply(state));
            StateDashboardDto dashboardDto = new StateDashboardDto();
            dashboardDto.setName(state);
            dashboardDto.setModules(buildModulesByTribe(dataFilter));
            stateDashboard.add(dashboardDto);
        }
        GovernmentDashboardDto dashboardDto = new GovernmentDashboardDto();
        dashboardDto.setTimeInApp(0);
        dashboardDto.setStudents(0);
        dashboardDto.setData(stateDashboard);
        return dashboardDto;
    }

    public static GovernmentDashboardDto newGovernmentStateDashboard(GovernmentStateDashboard info) {
        DataFilter dataFilter = new DataFilter();
        dataFilter.setActivities(info.getPerformanceActivities());
        dataFilter.setPerformanceGames(info.getPerformanceGames());
        List<StateDashboardDto> stateDashboard = new ArrayList<>();
        for (ICollege college : info.getColleges()) {
            dataFilter.setActivitiesPredicate(BY_CODE_DANE_ACTIVITIES.apply(college.getCodeDane()));
            dataFilter.setGamesPredicate(BY_CODE_DANE_GAMES.apply(college.getCodeDane()));
            StateDashboardDto dashboardDto = new StateDashboardDto();
            dashboardDto.setId(college.getCodeDane());
            dashboardDto.setName(college.getName());
            dashboardDto.setModules(buildModulesByTribe(dataFilter));
            stateDashboard.add(dashboardDto);
        }
        GovernmentDashboardDto dashboardDto = new GovernmentDashboardDto();
        dashboardDto.setTimeInApp(0);
        dashboardDto.setStudents(0);
        dashboardDto.setData(stateDashboard);
        return dashboardDto;
    }

    private static List<ModuleDashboardDto> buildModulesByTribe(DataFilter dataFilter) {
        return Arrays.stream(Tribe.values())
                .parallel()
                .map(tribe -> getModuleDashboardDto(dataFilter, tribe))
                .collect(Collectors.toList());
    }

    @NotNull
    private static ModuleDashboardDto getModuleDashboardDto(DataFilter dataFilter, Tribe tribe) {
        Predicate<IPerformanceActivity> activityPredicate = dataFilter.getActivitiesPredicate().apply(tribe);
        Predicate<IPerformanceGame> gamePredicate = dataFilter.getGamesPredicate().apply(tribe);

        int performanceActivityScore = score(dataFilter.getActivities(), activityPredicate,
                IPerformanceActivity::getScorePerformanceActivity);

        int performanceGameScore = score(dataFilter.getPerformanceGames(), gamePredicate,
                IPerformanceGame::getScorePerformance);

        return newModuleDashboard(tribe, performanceActivityScore, performanceGameScore);
    }

    private static ModuleDashboardDto newModuleDashboard(Tribe tribe, int performanceActivityScore,
                                                         int performanceGameScore) {
        ModuleDashboardDto moduleDashboard = new ModuleDashboardDto();
        moduleDashboard.setTitle(tribe.getAbbreviation());
        moduleDashboard.setId(tribe.getId());
        moduleDashboard.setName(tribe.getName());
        moduleDashboard.setPerformanceActivityScore(performanceActivityScore);
        moduleDashboard.setPerformanceGameScore(performanceGameScore);
        return moduleDashboard;
    }

    private static <T> int score(List<T> data, Predicate<T> filter, Function<T, Integer> score) {
        for (T element : data) {
            if (filter.test(element)) {
                return score.apply(element);
            }
        }
        return 0;
    }

    @Data
    @NoArgsConstructor
    private static class DataFilter {
        private List<IPerformanceActivity> activities;
        private List<IPerformanceGame> performanceGames;
        private Function<Tribe, Predicate<IPerformanceActivity>> activitiesPredicate;
        private Function<Tribe, Predicate<IPerformanceGame>> gamesPredicate;
    }

    @Data
    private static class DefaultDataDashboard {
        private List<IPerformanceActivity> performanceActivities;
        private List<IPerformanceGame> performanceGames;
    }

    @Data
    public static class GovernmentStateDashboard extends DefaultDataDashboard {
        private List<ICollege> colleges;
    }

    @Data
    public static class GovernmentDashboard extends DefaultDataDashboard {
        private List<String> states;
    }

    @Data
    public static class CollegeDashboard extends DefaultDataDashboard {
        private Integer numberOfStudents;
    }
}
