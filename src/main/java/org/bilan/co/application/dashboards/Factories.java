package org.bilan.co.application.dashboards;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.tuple.Pair;
import org.bilan.co.application.dashboards.DataModel.*;
import org.bilan.co.domain.dtos.dashboard.*;
import org.bilan.co.domain.entities.Students;
import org.bilan.co.domain.enums.Tribe;
import org.bilan.co.domain.projections.IPerformanceActivity;
import org.bilan.co.domain.projections.IPerformanceGame;
import org.bilan.co.domain.projections.ISummary;
import org.bilan.co.utils.Constants;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.support.PagedListHolder;
import org.springframework.data.domain.PageRequest;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

@Slf4j
@NoArgsConstructor(access = AccessLevel.PRIVATE)
final class Factories {

    private static final List<Object[]> EMPTY_LIST = Collections.singletonList(new Object[]{0, 0, 0});

    /**
     * @param info
     * @return
     */
    public static GeneralDashboardDto createMainDashboard(MainDashboard info) {

        Map<String, List<Object[]>> logins = info.getLogins().stream()
                .collect(Collectors.groupingBy(objects -> objects[0].toString()));

        Map<String, List<IPerformanceActivity>> activities = info.getPerformanceActivities()
                .stream().collect(Collectors.groupingBy(IPerformanceActivity::getState));

        Map<String, List<IPerformanceGame>> games = info.getPerformanceGames()
                .stream().collect(Collectors.groupingBy(IPerformanceGame::getState));

        List<RowSummary<TribeSummaryDto>> summaries = info.getStates().parallelStream()
                .map(state -> createRowSummary(activities, games, logins, state, state, state))
                .collect(Collectors.toList());

        return GeneralDashboardDto.builder().data(summaries).timeInApp(0).students(0).build();
    }

    /**
     * @param info
     * @return
     */
    public static GeneralDashboardDto createStateDashboard(StateDashboard info) {

        Map<Integer, List<Object[]>> logins = info.getLogins().stream()
                .collect(Collectors.groupingBy(objects -> (Integer) objects[0]));

        Map<Integer, List<IPerformanceActivity>> activities = info.getPerformanceActivities()
                .stream().collect(Collectors.groupingBy(IPerformanceActivity::getMunId));

        Map<Integer, List<IPerformanceGame>> games = info.getPerformanceGames()
                .stream().collect(Collectors.groupingBy(IPerformanceGame::getMunId));

        List<RowSummary<TribeSummaryDto>> summaries = info.getMunicipalities().parallelStream()
                .map(municipality -> createRowSummary(activities, games, logins, municipality.getName(),
                        String.valueOf(municipality.getId()), municipality.getId()))
                .collect(Collectors.toList());

        return GeneralDashboardDto.builder().data(summaries).timeInApp(0).students(0).build();
    }

    /**
     * @param info
     * @param pageRequest
     * @return
     */
    public static GeneralDashboardDto createMunicipalityDashboard(MunicipalityDashboard info,
                                                                  PageRequest pageRequest) {

        Map<Integer, List<Object[]>> logins = info.getLogins().stream()
                .collect(Collectors.groupingBy(objects -> (Integer) objects[0]));

        Map<Integer, List<IPerformanceActivity>> activities = info.getPerformanceActivities()
                .stream().collect(Collectors.groupingBy(IPerformanceActivity::getCollegeId));

        Map<Integer, List<IPerformanceGame>> games = info.getPerformanceGames()
                .stream().collect(Collectors.groupingBy(IPerformanceGame::getCollegeId));

        List<RowSummary<TribeSummaryDto>> summaries = info.getColleges().parallelStream()
                .map(college -> createRowSummary(activities, games, logins, college.getName(),
                        String.valueOf(college.getId()), college.getId()))
                .collect(Collectors.toList());

        PagedListHolder<RowSummary<TribeSummaryDto>> holder = new PagedListHolder<>(summaries);
        holder.setPageSize(pageRequest.getPageSize());
        holder.setPage(pageRequest.getPageNumber());

        return GeneralDashboardDto.builder().data(summaries)
                .timeInApp(0).students(0).page(holder.getPage()).pageCount(holder.getPageCount()).build();
    }

    /**
     * @param info
     * @return
     */
    public static CollegeDashboardDto createCollegeDashboard(CollegeDashboard info) {

        Map<Integer, List<Object[]>> logins = info.getLogins().stream()
                .collect(Collectors.groupingBy(objects -> (Integer) objects[0]));

        Map<Integer, IPerformanceActivity> activities = info.getPerformanceActivities()
                .stream().collect(Collectors.toMap(IPerformanceActivity::getId, Function.identity()));

        Map<Integer, IPerformanceGame> games = info.getPerformanceGames()
                .stream().collect(Collectors.toMap(IPerformanceGame::getId, Function.identity()));

        List<TribeSummaryDto> summary = new ArrayList<>();
        Object[] loginStudentsByTribe;
        for (Tribe tribe : Tribe.values()) {
            IPerformanceActivity activity = activities.get(tribe.getId());
            IPerformanceGame game = games.get(tribe.getId());
            TribeSummaryDto tribeSummaryDto = new TribeSummaryDto();
            tribeSummaryDto.setId(String.valueOf(tribe.getId()));
            tribeSummaryDto.setName(tribe.getName());
            tribeSummaryDto.setTitle(tribe.getAbbreviation());
            tribeSummaryDto.setPerformanceActivityScore(Objects.isNull(activity) ? 0 : activity.getScore());
            tribeSummaryDto.setPerformanceGameScore(Objects.isNull(game) ? 0 : game.getScore());
            loginStudentsByTribe = logins.getOrDefault(tribe.getId(), EMPTY_LIST).get(0);
            tribeSummaryDto.setLogins(String.format("%s/%s", loginStudentsByTribe[1], loginStudentsByTribe[2]));
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

        Map<String, List<Object[]>> logins = info.getLogins().stream()
                .collect(Collectors.groupingBy(objects -> objects[0].toString()));

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
            rowSummary.setLogins(logins.getOrDefault(student.getDocument(), EMPTY_LIST).get(0)[1]);

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

        return new StudentDashboardDto((int) (perCompletionAllTribes * 100),
                String.format("%s %s", info.getStudent().getName(), info.getStudent().getLastName()),
                (int) (perCompletionGame * 100),
                0,
                summary);
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
        List<IPerformanceActivity> activity = activities.get(student.getDocument());
        Integer scoreActivityCP = Objects.isNull(activity) ? 0 : activity.get(0).getScoreBasicCompetence();

        List<IPerformanceGame> game = games.get(student.getDocument());
        Integer scoreGameCP = Objects.isNull(game) ? 0 : game.get(0).getScoreBasicCompetence();

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
        return new StudentDashboard(activities.getOrDefault(student.getDocument(), Collections.emptyList()),
                games.getOrDefault(student.getDocument(), Collections.emptyList()), student);
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
     *
     * @param activities
     * @param games
     * @param logins
     * @param name
     * @param id
     * @param state
     * @param <T>
     * @return
     */
    private static <T> RowSummary<TribeSummaryDto> createRowSummary(Map<T, List<IPerformanceActivity>> activities,
                                                                    Map<T, List<IPerformanceGame>> games,
                                                                    Map<T, List<Object[]>> logins,
                                                                    String name, String id, T state) {

        Object[] login = logins.getOrDefault(state, EMPTY_LIST).get(0);

        RowSummary<TribeSummaryDto> rowSummary = new RowSummary<>();
        rowSummary.setId(id);
        rowSummary.setName(name);
        rowSummary.setLogins(String.format("%s/%s", login[1], login[2]));
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
