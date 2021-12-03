package org.bilan.co.application.dashboards;

import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.bilan.co.application.dashboards.DataModel.*;
import org.bilan.co.domain.dtos.dashboard.GovernmentDashboardDto;
import org.bilan.co.domain.dtos.dashboard.StudentDashboardDto;
import org.bilan.co.domain.dtos.dashboard.StudentDashboardDto.TribeSummaryStudentDto;
import org.bilan.co.domain.dtos.dashboard.TribeSummaryDto;
import org.bilan.co.domain.entities.Students;
import org.bilan.co.domain.enums.Tribe;
import org.bilan.co.domain.projections.*;
import org.bilan.co.utils.Constants;
import org.jetbrains.annotations.NotNull;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

import static org.bilan.co.domain.dtos.dashboard.GovernmentDashboardDto.StateDashboardDto;

@Slf4j
@NoArgsConstructor(access = AccessLevel.PRIVATE)
final class Factories {

    public static GovernmentDashboardDto newGovernmentDashboard(GovernmentDashboard info) {

        Map<String, List<IPerformanceActivity>> activities = info.getPerformanceActivities()
                .stream().collect(Collectors.groupingBy(IPerformanceActivity::getState));

        Map<String, List<IPerformanceGame>> games = info.getPerformanceGames()
                .stream().collect(Collectors.groupingBy(IPerformanceGame::getState));

        List<StateDashboardDto> modules = info.getStates()
                .parallelStream()
                .map(state -> getStateDashboardDto(activities, games, state))
                .collect(Collectors.toList());

        return new GovernmentDashboardDto(0, 0, modules);
    }

    private static StateDashboardDto getStateDashboardDto(Map<String, List<IPerformanceActivity>> activities,
                                                          Map<String, List<IPerformanceGame>> games, String state) {
        List<TribeSummaryDto> tribeSummary = new ArrayList<>();
        StateDashboardDto dashboardDto = new StateDashboardDto();
        dashboardDto.setName(state);
        dashboardDto.setModules(tribeSummary);

        for (Tribe tribe : Tribe.values()) {
            Optional<IPerformanceActivity> activity = byTribe(activities.get(state), tribe);
            Optional<IPerformanceGame> game = byTribe(games.get(state), tribe);
            int gameScore = game.map(IPerformanceGame::getScorePerformance).orElse(0);
            int activityScore = activity.map(IPerformanceActivity::getScorePerformanceActivity).orElse(0);

            TribeSummaryDto tribeSummaryDto = new TribeSummaryDto();
            tribeSummaryDto.setId(tribe.getId());
            tribeSummaryDto.setName(tribe.getName());
            tribeSummaryDto.setTitle(tribe.getAbbreviation());
            tribeSummaryDto.setPerformanceGameScore(gameScore);
            tribeSummaryDto.setPerformanceActivityScore(activityScore);
            tribeSummary.add(tribeSummaryDto);
        }

        Integer scoreActivityCP = Optional.ofNullable(activities.get(state))
                .flatMap(list -> list.stream().findFirst())
                .map(IPerformanceActivity::getScoreBasicCompetence)
                .orElse(0);

        TribeSummaryDto tribeSummaryDto = createTribeSummaryDto(scoreActivityCP);
        tribeSummary.add(tribeSummaryDto);

        return dashboardDto;
    }

    public static GovernmentDashboardDto newGovernmentStateDashboard(GovernmentStateDashboard info) {

        Map<Integer, List<IPerformanceActivity>> activities = info.getPerformanceActivities()
                .stream().collect(Collectors.groupingBy(IPerformanceActivity::getMunId));

        Map<Integer, List<IPerformanceGame>> games = info.getPerformanceGames()
                .stream().collect(Collectors.groupingBy(IPerformanceGame::getMunId));

        List<StateDashboardDto> modules = info.getMunicipalities()
                .parallelStream()
                .map(municipality -> getMunDashboardDto(activities, games, municipality))
                .collect(Collectors.toList());

        return new GovernmentDashboardDto(0, 0, modules);
    }

    private static StateDashboardDto getMunDashboardDto(Map<Integer, List<IPerformanceActivity>> activities,
                                                        Map<Integer, List<IPerformanceGame>> games,
                                                        IMunicipality municipality) {
        List<TribeSummaryDto> tribeSummary = new ArrayList<>();
        StateDashboardDto dashboardDto = new StateDashboardDto();
        dashboardDto.setId(municipality.getId().toString());
        dashboardDto.setName(municipality.getName());
        dashboardDto.setModules(tribeSummary);

        for (Tribe tribe : Tribe.values()) {
            Optional<IPerformanceActivity> activity = byTribe(activities.get(municipality.getId()), tribe);
            Optional<IPerformanceGame> game = byTribe(games.get(municipality.getId()), tribe);
            int gameScore = game.map(IPerformanceGame::getScorePerformance).orElse(0);
            int activityScore = activity.map(IPerformanceActivity::getScorePerformanceActivity).orElse(0);

            TribeSummaryDto tribeSummaryDto = new TribeSummaryDto();
            tribeSummaryDto.setId(tribe.getId());
            tribeSummaryDto.setName(tribe.getName());
            tribeSummaryDto.setTitle(tribe.getAbbreviation());
            tribeSummaryDto.setPerformanceGameScore(gameScore);
            tribeSummaryDto.setPerformanceActivityScore(activityScore);
            tribeSummary.add(tribeSummaryDto);
        }

        Integer scoreActivityCP = Optional.ofNullable(activities.get(municipality.getId()))
                .flatMap(list -> list.stream().findFirst())
                .map(IPerformanceActivity::getScoreBasicCompetence)
                .orElse(0);

        TribeSummaryDto tribeSummaryDto = createTribeSummaryDto(scoreActivityCP);
        tribeSummary.add(tribeSummaryDto);

        return dashboardDto;
    }

    @NotNull
    private static TribeSummaryDto createTribeSummaryDto(Integer scoreActivityCP) {
        TribeSummaryDto tribeSummaryDto = new TribeSummaryDto();
        tribeSummaryDto.setId(6);
        tribeSummaryDto.setName("Competencias Basicas");
        tribeSummaryDto.setTitle("CP");
        tribeSummaryDto.setPerformanceActivityScore(scoreActivityCP);
        tribeSummaryDto.setPerformanceGameScore(0);
        return tribeSummaryDto;
    }

    public static GovernmentDashboardDto newGovernmentMunDashboard(GovernmentMunDashboard info) {

        Map<Integer, List<IPerformanceActivity>> activities = info.getPerformanceActivities()
                .stream().collect(Collectors.groupingBy(IPerformanceActivity::getCollegeId));

        Map<Integer, List<IPerformanceGame>> games = info.getPerformanceGames()
                .stream().collect(Collectors.groupingBy(IPerformanceGame::getCollegeId));

        List<StateDashboardDto> modules = info.getColleges().parallelStream()
                .map(college -> getCollegeDashboardDto(activities, games, college))
                .collect(Collectors.toList());

        return new GovernmentDashboardDto(0, 0, modules);
    }

    @NotNull
    private static StateDashboardDto getCollegeDashboardDto(Map<Integer, List<IPerformanceActivity>> activities,
                                                            Map<Integer, List<IPerformanceGame>> games, ICollege college) {

        List<TribeSummaryDto> tribeSummary = new ArrayList<>();
        StateDashboardDto dashboardDto = new StateDashboardDto();
        dashboardDto.setId(college.getId().toString());
        dashboardDto.setName(college.getName());
        dashboardDto.setModules(tribeSummary);

        for (Tribe tribe : Tribe.values()) {
            Optional<IPerformanceActivity> activity = byTribe(activities.get(college.getId()), tribe);
            Optional<IPerformanceGame> game = byTribe(games.get(college.getId()), tribe);
            int gameScore = game.map(IPerformanceGame::getScorePerformance).orElse(0);
            int activityScore = activity.map(IPerformanceActivity::getScorePerformanceActivity).orElse(0);

            TribeSummaryDto tribeSummaryDto = new TribeSummaryDto();
            tribeSummaryDto.setId(tribe.getId());
            tribeSummaryDto.setName(tribe.getName());
            tribeSummaryDto.setTitle(tribe.getAbbreviation());
            tribeSummaryDto.setPerformanceGameScore(gameScore);
            tribeSummaryDto.setPerformanceActivityScore(activityScore);
            tribeSummary.add(tribeSummaryDto);
        }

        Integer scoreActivityCP = Optional.ofNullable(activities.get(college.getId()))
                .flatMap(list -> list.stream().findFirst())
                .map(IPerformanceActivity::getScoreBasicCompetence)
                .orElse(0);

        TribeSummaryDto tribeSummaryDto = createTribeSummaryDto(scoreActivityCP);
        tribeSummary.add(tribeSummaryDto);

        return dashboardDto;
    }

    public static GovernmentDashboardDto newGovernmentCollegeDashboard(CollegeDashboard info) {

        Map<Integer, IPerformanceActivity> infoActivityByTribe = info.getPerformanceActivities()
                .stream().collect(Collectors.toMap(IPerformanceActivity::getId, Function.identity()));

        Map<Integer, IPerformanceGame> infoGameByTribe = info.getPerformanceGames()
                .stream().collect(Collectors.toMap(IPerformanceGame::getId, Function.identity()));

        List<TribeSummaryDto> summary = new ArrayList<>();
        for (Tribe tribe : Tribe.values()) {
            IPerformanceActivity activity = infoActivityByTribe.get(tribe.getId());
            IPerformanceGame game = infoGameByTribe.get(tribe.getId());
            TribeSummaryDto tribeSummaryDto = new TribeSummaryDto();
            tribeSummaryDto.setId(tribe.getId());
            tribeSummaryDto.setName(tribe.getName());
            tribeSummaryDto.setTitle(tribe.getAbbreviation());
            tribeSummaryDto.setPerformanceActivityScore(Objects.isNull(activity) ? 0 : activity.getScorePerformanceActivity());
            tribeSummaryDto.setPerformanceGameScore(Objects.isNull(game) ? 0 : game.getScorePerformance());
            summary.add(tribeSummaryDto);
        }

        Integer scoreActivityCP = info.getPerformanceActivities().stream().findFirst()
                .map(IPerformanceActivity::getScoreBasicCompetence).
                orElse(0);

        TribeSummaryDto tribeSummaryDto = createTribeSummaryDto(scoreActivityCP);
        summary.add(tribeSummaryDto);

        StateDashboardDto stateDashboardDto = new StateDashboardDto();
        stateDashboardDto.setName(info.getCollege().getName());
        stateDashboardDto.setId(info.getCollege().getId().toString());
        stateDashboardDto.setModules(summary);

        return new GovernmentDashboardDto(info.getCollege().getNumberStudents(), 0,
                Collections.singletonList(stateDashboardDto));
    }

    public static GovernmentDashboardDto newGovernmentCourseGradeDashboard(CourseGradeDashboard info) {

        Map<String, List<IPerformanceActivity>> activities = info.getPerformanceActivities()
                .stream().collect(Collectors.groupingBy(IPerformanceActivity::getDocument));

        Map<String, List<IPerformanceGame>> games = info.getPerformanceGames()
                .stream().collect(Collectors.groupingBy(IPerformanceGame::getDocument));

        List<StateDashboardDto> modules = info.getStudents()
                .parallelStream()
                .map(student -> getStateDashboardDto(activities, games, student))
                .collect(Collectors.toList());

        return new GovernmentDashboardDto(info.getStudents().size(), 0, modules);
    }

    private static StateDashboardDto getStateDashboardDto(Map<String, List<IPerformanceActivity>> activities,
                                                          Map<String, List<IPerformanceGame>> games, Students student) {

        List<TribeSummaryDto> tribeSummary = new ArrayList<>();
        StateDashboardDto dashboardDto = new StateDashboardDto();
        dashboardDto.setId(student.getDocument());
        dashboardDto.setName(student.getName() + " " + student.getLastName());
        dashboardDto.setModules(tribeSummary);

        for (Tribe tribe : Tribe.values()) {
            Optional<IPerformanceActivity> activity = byTribe(activities.get(student.getDocument()), tribe);
            Optional<IPerformanceGame> game = byTribe(games.get(student.getDocument()), tribe);
            int gameScore = game.map(IPerformanceGame::getScorePerformance).orElse(0);
            int activityScore = activity.map(IPerformanceActivity::getScorePerformanceActivity).orElse(0);

            TribeSummaryDto tribeSummaryDto = new TribeSummaryDto();
            tribeSummaryDto.setId(tribe.getId());
            tribeSummaryDto.setName(tribe.getName());
            tribeSummaryDto.setTitle(tribe.getAbbreviation());
            tribeSummaryDto.setPerformanceGameScore(gameScore);
            tribeSummaryDto.setPerformanceActivityScore(activityScore);
            tribeSummary.add(tribeSummaryDto);
        }

        Integer scoreActivityCP = Optional.ofNullable(activities.get(student.getDocument()))
                .flatMap(list -> list.stream().findFirst())
                .map(IPerformanceActivity::getScoreBasicCompetence)
                .orElse(0);

        TribeSummaryDto tribeSummaryDto = createTribeSummaryDto(scoreActivityCP);
        tribeSummary.add(tribeSummaryDto);

        return dashboardDto;
    }

    public static StudentDashboardDto newGovernmentStudentDashboard(StudentDashboard info) {
        List<TribeSummaryStudentDto> summary = new ArrayList<>();

        Map<Integer, IPerformanceActivity> infoActivityByTribe = info.getPerformanceActivities()
                .stream().collect(Collectors.toMap(IPerformanceActivity::getId, Function.identity()));

        Map<Integer, IPerformanceGame> infoGameByTribe = info.getPerformanceGames()
                .stream().collect(Collectors.toMap(IPerformanceGame::getId, Function.identity()));

        for (Tribe tribe : Tribe.values()) {
            IPerformanceActivity activity = infoActivityByTribe.get(tribe.getId());
            IPerformanceGame game = infoGameByTribe.get(tribe.getId());
            TribeSummaryStudentDto tribeStudentSummary = new TribeSummaryStudentDto();
            tribeStudentSummary.setId(tribe.getId());
            tribeStudentSummary.setName(tribe.getName());
            tribeStudentSummary.setTitle(tribe.getAbbreviation());
            tribeStudentSummary.setPerformanceGameScore(Objects.isNull(game) ? 0 : game.getScorePerformance());
            tribeStudentSummary.setPerformanceActivityScore(Objects.isNull(activity) ? 0 :
                    activity.getScorePerformanceActivity());
            tribeStudentSummary.setInteractivePhase(Objects.isNull(activity) ? 0 : activity.getInteractive());
            tribeStudentSummary.setPostActivePhase(Objects.isNull(activity) ? 0 : activity.getPostActive());
            tribeStudentSummary.setPreActivePhase(Objects.isNull(activity) ? 0 : activity.getPreActive());
            summary.add(tribeStudentSummary);
        }

        double val = info.getPerformanceActivities()
                .stream()
                .mapToInt(activity -> activity.getInteractive() + activity.getPostActive() + activity.getPreActive())
                .sum() / (double) Constants.TOTAL_PHASES;

        return new StudentDashboardDto((int) (val * 100), 0, summary);
    }

    private static <T extends ISummary> Optional<T> byTribe(List<T> activities, Tribe tribe) {
        if (Objects.isNull(activities)) {
            return Optional.empty();
        }
        return activities.stream().filter(t -> t.getId() == tribe.getId()).findFirst();
    }
}
