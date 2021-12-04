package org.bilan.co.application.dashboards;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.tuple.Pair;
import org.bilan.co.application.dashboards.DataModel.CollegeDashboard;
import org.bilan.co.application.dashboards.DataModel.CourseGradeDashboard;
import org.bilan.co.application.dashboards.DataModel.MainDashboard;
import org.bilan.co.application.dashboards.DataModel.MunicipalityDashboard;
import org.bilan.co.application.dashboards.DataModel.StateDashboard;
import org.bilan.co.application.dashboards.DataModel.StudentDashboard;
import org.bilan.co.domain.dtos.dashboard.*;
import org.bilan.co.domain.entities.Students;
import org.bilan.co.domain.enums.Tribe;
import org.bilan.co.domain.projections.*;
import org.bilan.co.utils.Constants;
import org.jetbrains.annotations.NotNull;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

@Slf4j
@NoArgsConstructor(access = AccessLevel.PRIVATE)
final class Factories {

    /**
     * @param info
     * @return
     */
    public static GeneralDashboardDto createMainDashboard(MainDashboard info) {
        Map<String, List<IPerformanceActivity>> activities = info.getPerformanceActivities()
                .stream().collect(Collectors.groupingBy(IPerformanceActivity::getState));

        Map<String, List<IPerformanceGame>> games = info.getPerformanceGames()
                .stream().collect(Collectors.groupingBy(IPerformanceGame::getState));

        List<RowSummary<TribeSummaryDto>> summaries = info.getStates().parallelStream()
                .map(state -> createRowSummary(activities, games, state, null, state))
                .collect(Collectors.toList());

        return new GeneralDashboardDto(0, 0, summaries);
    }

    /**
     * @param info
     * @return
     */
    public static GeneralDashboardDto createStateDashboard(StateDashboard info) {
        Map<Integer, List<IPerformanceActivity>> activities = info.getPerformanceActivities()
                .stream().collect(Collectors.groupingBy(IPerformanceActivity::getMunId));

        Map<Integer, List<IPerformanceGame>> games = info.getPerformanceGames()
                .stream().collect(Collectors.groupingBy(IPerformanceGame::getMunId));

        List<RowSummary<TribeSummaryDto>> summaries = info.getMunicipalities().parallelStream()
                .map(municipality -> createRowSummary(activities, games, municipality.getName(),
                        String.valueOf(municipality.getId()), municipality.getId()))
                .collect(Collectors.toList());

        return new GeneralDashboardDto(0, 0, summaries);
    }

    /**
     * @param info
     * @return
     */
    public static GeneralDashboardDto createMunicipalityDashboard(MunicipalityDashboard info) {

        Map<Integer, List<IPerformanceActivity>> activities = info.getPerformanceActivities()
                .stream().collect(Collectors.groupingBy(IPerformanceActivity::getCollegeId));

        Map<Integer, List<IPerformanceGame>> games = info.getPerformanceGames()
                .stream().collect(Collectors.groupingBy(IPerformanceGame::getCollegeId));

        List<RowSummary<TribeSummaryDto>> summaries = info.getColleges().parallelStream()
                .map(college -> createRowSummary(activities, games, college.getName(),
                        String.valueOf(college.getId()), college.getId()))
                .collect(Collectors.toList());

        return new GeneralDashboardDto(0, 0, summaries);
    }

    /**
     * @param info
     * @return
     */
    public static CollegeDashboardDto createCollegeDashboard(CollegeDashboard info) {

        Map<Integer, IPerformanceActivity> activities = info.getPerformanceActivities()
                .stream().collect(Collectors.toMap(IPerformanceActivity::getId, Function.identity()));

        Map<Integer, IPerformanceGame> games = info.getPerformanceGames()
                .stream().collect(Collectors.toMap(IPerformanceGame::getId, Function.identity()));

        List<TribeSummaryDto> summary = new ArrayList<>();
        for (Tribe tribe : Tribe.values()) {
            IPerformanceActivity activity = activities.get(tribe.getId());
            IPerformanceGame game = games.get(tribe.getId());
            TribeSummaryDto tribeSummaryDto = new TribeSummaryDto();
            tribeSummaryDto.setId(String.valueOf(tribe.getId()));
            tribeSummaryDto.setName(tribe.getName());
            tribeSummaryDto.setTitle(tribe.getAbbreviation());
            tribeSummaryDto.setPerformanceActivityScore(Objects.isNull(activity) ? 0 : activity.getScore());
            tribeSummaryDto.setPerformanceGameScore(Objects.isNull(game) ? 0 : game.getScore());
            summary.add(tribeSummaryDto);
        }

        Integer scoreActivityCP = info.getPerformanceActivities().stream().findFirst()
                .map(IPerformanceActivity::getScoreBasicCompetence).
                orElse(0);

        TribeSummaryDto tribeSummaryDto = createTribeSummaryDto(Pair.of(scoreActivityCP, 0));
        summary.add(tribeSummaryDto);

        RowSummary<TribeSummaryDto> collegeSummary = new RowSummary<>();
        collegeSummary.setName(info.getCollege().getName());
        collegeSummary.setId(info.getCollege().getId().toString());
        collegeSummary.setModules(summary);

        return new CollegeDashboardDto(info.getCollege().getNumberStudents(), 0, 0,
                Collections.singletonList(collegeSummary));
    }

    /**
     * @param info
     * @return
     */
    public static GradeDashboardDto createCourseGradeDashboard(CourseGradeDashboard info) {

        Map<String, List<IPerformanceActivity>> activities = info.getPerformanceActivities()
                .stream().collect(Collectors.groupingBy(IPerformanceActivity::getDocument));

        Map<String, List<IPerformanceGame>> games = info.getPerformanceGames()
                .stream().collect(Collectors.groupingBy(IPerformanceGame::getDocument));

        List<RowSummary<TribeSummaryDto>> summary = new ArrayList<>();

        int percentageActivities = 0;
        int percentageGame = 0;

        for (Students student : info.getStudents()) {
            StudentDashboard infoStudent = getStudentDashboard(activities, games, student);
            StudentDashboardDto studentDashboard = Factories.createStudentDashboard(infoStudent);
            percentageActivities += studentDashboard.getPercentage();
            percentageGame += studentDashboard.getPercentageGame();

            RowSummary<TribeSummaryDto> rowSummary = new RowSummary<>();
            rowSummary.setId(student.getDocument());
            rowSummary.setName(student.fullName());

            List<TribeSummaryDto> tribeSummary = studentDashboard.getData().stream()
                    .map(TribeSummaryStudentDto::cleanPhases).collect(Collectors.toList());

            Pair<Integer, Integer> scoresBasicCompetence = getScoresBasicCompetence(activities, games, student);

            tribeSummary.add(createTribeSummaryDto(scoresBasicCompetence));
            rowSummary.setModules(tribeSummary);
            summary.add(rowSummary);
        }

        float percentageActivitiesByGroup = percentageActivities / (float) (Constants.TOTAL_PHASES * info.getStudents().size());
        float percentageActivitiesBy = percentageGame / (float) (Constants.TOTAL_QUESTIONS_BY_GRADE * info.getStudents().size());
        float percentageGroup = (percentageActivitiesByGroup + percentageActivitiesBy) / 2;

        return new GradeDashboardDto(((int) percentageGroup * 100), info.getStudents().size(), 0, summary);
    }

    /**
     * @param info
     * @return
     */
    public static StudentDashboardDto createStudentDashboard(StudentDashboard info) {
        List<TribeSummaryStudentDto> summary = new ArrayList<>();

        Map<Integer, IPerformanceActivity> infoActivityByTribe = info.getPerformanceActivities()
                .stream().collect(Collectors.toMap(IPerformanceActivity::getId, Function.identity()));

        Map<Integer, IPerformanceGame> infoGameByTribe = info.getPerformanceGames()
                .stream().collect(Collectors.toMap(IPerformanceGame::getId, Function.identity()));

        for (Tribe tribe : Tribe.values()) {
            IPerformanceActivity activity = infoActivityByTribe.get(tribe.getId());
            IPerformanceGame game = infoGameByTribe.get(tribe.getId());
            TribeSummaryStudentDto tribeStudentSummary = new TribeSummaryStudentDto();
            tribeStudentSummary.setId(String.valueOf(tribe.getId()));
            tribeStudentSummary.setName(tribe.getName());
            tribeStudentSummary.setTitle(tribe.getAbbreviation());
            tribeStudentSummary.setPerformanceGameScore(Objects.isNull(game) ? 0 : game.getScore());
            tribeStudentSummary.setPerformanceActivityScore(Objects.isNull(activity) ? 0 : activity.getScore());
            tribeStudentSummary.setInteractivePhase(Objects.isNull(activity) ? 0 : activity.getInteractive());
            tribeStudentSummary.setPostActivePhase(Objects.isNull(activity) ? 0 : activity.getPostActive());
            tribeStudentSummary.setPreActivePhase(Objects.isNull(activity) ? 0 : activity.getPreActive());
            summary.add(tribeStudentSummary);
        }

        float perCompletionAllTribes = info.getPerformanceActivities()
                .stream()
                .mapToInt(activity -> activity.getInteractive() + activity.getPostActive() + activity.getPreActive())
                .sum() / (float) Constants.TOTAL_PHASES;

        float perCompletionGame = info.getPerformanceGames()
                .stream()
                .mapToInt(IPerformanceGame::getNumberQuestionsAnswered)
                .sum() / (float) Constants.TOTAL_QUESTIONS_BY_GRADE;

        return new StudentDashboardDto((int) (perCompletionAllTribes * 100), (int) (perCompletionGame * 100), 0, summary);
    }

    /**
     * @param activities
     * @param games
     * @param student
     * @return
     */
    @NotNull
    private static Pair<Integer, Integer> getScoresBasicCompetence(Map<String, List<IPerformanceActivity>> activities,
                                                                   Map<String, List<IPerformanceGame>> games,
                                                                   Students student) {
        Integer scoreActivityCP = Optional.ofNullable(activities.get(student.getDocument()))
                .flatMap(list -> list.stream().findFirst())
                .map(IPerformanceActivity::getScoreBasicCompetence)
                .orElse(0);

        Integer scoreGameCP = Optional.ofNullable(games.get(student.getDocument()))
                .flatMap(list -> list.stream().findFirst())
                .map(IPerformanceGame::getScoreBasicCompetence)
                .orElse(0);

        return Pair.of(scoreActivityCP, scoreGameCP);
    }


    /**
     * @param activities
     * @param games
     * @param student
     * @return
     */
    private static StudentDashboard getStudentDashboard(Map<String, List<IPerformanceActivity>> activities,
                                                        Map<String, List<IPerformanceGame>> games, Students student) {
        StudentDashboard studentDashboard = new StudentDashboard();
        studentDashboard.setPerformanceActivities(activities.getOrDefault(student.getDocument(), Collections.emptyList()));
        studentDashboard.setPerformanceGames(games.getOrDefault(student.getDocument(), Collections.emptyList()));
        studentDashboard.setStudent(student);
        return studentDashboard;
    }

    /**
     * @param scoresBasicCompetence
     * @return
     */
    private static TribeSummaryDto createTribeSummaryDto(Pair<Integer, Integer> scoresBasicCompetence) {
        TribeSummaryDto tribeSummaryDto = new TribeSummaryDto();
        tribeSummaryDto.setId("6");
        tribeSummaryDto.setName("Competencias Basicas");
        tribeSummaryDto.setTitle("CP");
        tribeSummaryDto.setPerformanceActivityScore(scoresBasicCompetence.getLeft());
        tribeSummaryDto.setPerformanceGameScore(scoresBasicCompetence.getRight());
        return tribeSummaryDto;
    }

    /**
     * @param activities
     * @param tribe
     * @param <T>
     * @return
     */
    private static <T extends ISummary> Optional<T> byTribe(List<T> activities, Tribe tribe) {
        if (Objects.isNull(activities)) {
            return Optional.empty();
        }
        return activities.stream().filter(t -> t.getId() == tribe.getId()).findFirst();
    }

    /**
     * @param activities
     * @param games
     * @param name
     * @param state
     * @param <T>
     * @return
     */
    private static <T> RowSummary<TribeSummaryDto> createRowSummary(Map<T, List<IPerformanceActivity>> activities,
                                                                    Map<T, List<IPerformanceGame>> games,
                                                                    String name, String id, T state) {
        RowSummary<TribeSummaryDto> rowSummary = new RowSummary<>();
        rowSummary.setId(id);
        rowSummary.setName(name);
        rowSummary.setModules(new ArrayList<>());

        for (Tribe tribe : Tribe.values()) {
            Optional<IPerformanceActivity> activity = byTribe(activities.get(state), tribe);
            Optional<IPerformanceGame> game = byTribe(games.get(state), tribe);
            int gameScore = game.map(IPerformanceGame::getScore).orElse(0);
            int activityScore = activity.map(IPerformanceActivity::getScore).orElse(0);

            TribeSummaryDto tribeSummaryDto = new TribeSummaryDto();
            tribeSummaryDto.setId(String.valueOf(tribe.getId()));
            tribeSummaryDto.setName(tribe.getName());
            tribeSummaryDto.setTitle(tribe.getAbbreviation());
            tribeSummaryDto.setPerformanceGameScore(gameScore);
            tribeSummaryDto.setPerformanceActivityScore(activityScore);
            rowSummary.getModules().add(tribeSummaryDto);
        }

        Integer scoreActivityCP = Optional.ofNullable(activities.get(state))
                .flatMap(list -> list.stream().findFirst())
                .map(IPerformanceActivity::getScoreBasicCompetence)
                .orElse(0);

        rowSummary.getModules().add(createTribeSummaryDto(Pair.of(scoreActivityCP, 0)));
        return rowSummary;
    }
}
