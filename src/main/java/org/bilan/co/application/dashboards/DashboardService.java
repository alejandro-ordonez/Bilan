package org.bilan.co.application.dashboards;

import lombok.extern.slf4j.Slf4j;
import org.bilan.co.domain.dtos.ResponseDto;
import org.bilan.co.domain.dtos.dashboard.CollegeDashboardDto;
import org.bilan.co.domain.dtos.dashboard.GeneralDashboardDto;
import org.bilan.co.domain.dtos.dashboard.GradeDashboardDto;
import org.bilan.co.domain.dtos.dashboard.StudentDashboardDto;
import org.bilan.co.domain.entities.Courses;
import org.bilan.co.domain.entities.Students;
import org.bilan.co.domain.projections.ICollege;
import org.bilan.co.domain.projections.IMunicipality;
import org.bilan.co.domain.projections.IPerformanceActivity;
import org.bilan.co.domain.projections.IPerformanceGame;
import org.bilan.co.infraestructure.persistance.*;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;

@Slf4j
@Service
public class DashboardService implements IDashboardService {

    private final DashboardRepository dashboardRepository;
    private final StudentsRepository studentsRepository;
    private final CollegesRepository collegesRepository;

    private final CoursesRepository coursesRepository;
    private final StateMunicipalityRepository stateMunicipalityRepository;
    private final ExecutorService executor;

    public DashboardService(DashboardRepository dashboardRepository, TeachersRepository teachersRepository,
                            StudentsRepository studentsRepository, CollegesRepository collegesRepository,
                            CoursesRepository coursesRepository,
                            StateMunicipalityRepository stateMunicipalityRepository,
                            ExecutorService executorService) {
        this.dashboardRepository = dashboardRepository;
        this.studentsRepository = studentsRepository;
        this.collegesRepository = collegesRepository;
        this.stateMunicipalityRepository = stateMunicipalityRepository;
        this.coursesRepository = coursesRepository;
        this.executor = executorService;
    }

    @Override
    public ResponseDto<GeneralDashboardDto> statistics() {
        return this.buildGeneral()
                .map(dashboard -> new ResponseDto<>("Dashboard", 200, dashboard))
                .orElse(new ResponseDto<>("Dashboard Not Found", 404, null));
    }

    @Override
    public ResponseDto<GeneralDashboardDto> stateStatistics(String state) {
        return this.buildState(state)
                .map(dashboard -> new ResponseDto<>("Dashboard", 200, dashboard))
                .orElse(new ResponseDto<>("Dashboard Not Found", 404, null));
    }

    @Override
    public ResponseDto<GeneralDashboardDto> municipalityStatistics(Integer munId, PageRequest pageRequest) {
        return this.buildMunicipality(munId, pageRequest)
                .map(dashboard -> new ResponseDto<>("Dashboard", 200, dashboard))
                .orElse(new ResponseDto<>("Dashboard Not Found", 404, null));
    }

    @Override
    public ResponseDto<CollegeDashboardDto> collegeStatistics(int collegeId) {
        return this.buildCollege(collegeId)
                .map(dashboard -> new ResponseDto<>("Dashboard", 200, dashboard))
                .orElse(new ResponseDto<>("Dashboard Not Found", 404, null));
    }

    @Override
    public ResponseDto<GradeDashboardDto> courseGradeStatistics(int collegeId, String grade, String courseId) {
        return this.buildCourseGrade(collegeId, grade, courseId)
                .map(dashboard -> new ResponseDto<>("Dashboard", 200, dashboard))
                .orElse(new ResponseDto<>("Dashboard Not Found", 404, null));
    }

    @Override
    public ResponseDto<StudentDashboardDto> studentStatistics(String document) {
        return this.buildStudent(document)
                .map(dashboard -> new ResponseDto<>("Dashboard", 200, dashboard))
                .orElse(new ResponseDto<>("Dashboard Not Found", 404, null));
    }

    private Optional<GeneralDashboardDto> buildGeneral() {
        CompletableFuture<List<String>> states =
                CompletableFuture.supplyAsync(stateMunicipalityRepository::states, executor);

        CompletableFuture<List<Object[]>> logins =
                CompletableFuture.supplyAsync(dashboardRepository::login, executor);

        CompletableFuture<List<IPerformanceActivity>> activities =
                CompletableFuture.supplyAsync(dashboardRepository::statistics, executor);

        CompletableFuture<List<IPerformanceGame>> games =
                CompletableFuture.supplyAsync(dashboardRepository::statisticsPerformance, executor);

        return CompletableFuture.allOf(states, logins, activities, games)
                .thenApply(v -> DashboardFunction.buildMainDashboard(
                        activities.join(), games.join(), states.join(), logins.join()))
                .thenApply(Factories::createMainDashboard)
                .thenApply(Optional::of)
                .exceptionally(e -> {
                    log.error("Error fetching dashboard data", e);
                    return Optional.empty();
                })
                .join();
    }


    private Optional<GeneralDashboardDto> buildState(String state) {
        CompletableFuture<List<IMunicipality>> municipalitiesByState =
                CompletableFuture.supplyAsync(() -> stateMunicipalityRepository.findMunicipalitiesByState(state), executor);

        CompletableFuture<List<Object[]>> logins =
                CompletableFuture.supplyAsync(() -> dashboardRepository.loginState(state), executor);

        CompletableFuture<List<IPerformanceActivity>> activities =
                CompletableFuture.supplyAsync(() -> dashboardRepository.statistics(state), executor);

        CompletableFuture<List<IPerformanceGame>> games =
                CompletableFuture.supplyAsync(() -> dashboardRepository.statisticsPerformance(state), executor);

        return CompletableFuture.allOf(municipalitiesByState, logins, activities, games)
                .thenApply(v -> DashboardFunction.buildState(
                        activities.join(),
                        games.join(),
                        municipalitiesByState.join(),
                        logins.join()))
                .thenApply(Factories::createStateDashboard)
                .thenApply(Optional::of)
                .exceptionally(e -> {
                    log.error("Error fetching data needed to generate state dashboard", e);
                    return Optional.empty();
                })
                .join();
    }

    private Optional<GeneralDashboardDto> buildMunicipality(Integer munId, PageRequest pageRequest) {
        CompletableFuture<List<ICollege>> collegesByMun =
                CompletableFuture.supplyAsync(() -> collegesRepository.findByMunId(munId), executor);

        CompletableFuture<List<Object[]>> logins =
                CompletableFuture.supplyAsync(() -> dashboardRepository.loginMunicipality(munId), executor);

        CompletableFuture<List<IPerformanceActivity>> activities =
                CompletableFuture.supplyAsync(() -> dashboardRepository.statistics(munId), executor);

        CompletableFuture<List<IPerformanceGame>> games =
                CompletableFuture.supplyAsync(() -> dashboardRepository.statisticsPerformance(munId), executor);

        return CompletableFuture.allOf(activities, games, collegesByMun, logins)
                .thenApply(v -> DashboardFunction.buildMun(
                        activities.join(),
                        games.join(),
                        collegesByMun.join(),
                        logins.join()))
                .thenApply(info -> Factories.createMunicipalityDashboard(info, pageRequest))
                .thenApply(Optional::of)
                .exceptionally(e -> {
                    log.error("Error fetching data needed to generate municipality dashboard", e);
                    return Optional.empty();
                })
                .join();
    }

    private Optional<CollegeDashboardDto> buildCollege(int collegeId) {
        CompletableFuture<ICollege> college =
                CompletableFuture.supplyAsync(() -> collegesRepository.singleById(collegeId), executor);

        CompletableFuture<List<Object[]>> logins =
                CompletableFuture.supplyAsync(() -> dashboardRepository.loginCollege(collegeId), executor);

        CompletableFuture<List<IPerformanceActivity>> activities =
                CompletableFuture.supplyAsync(() -> dashboardRepository.statisticsCollege(collegeId), executor);

        CompletableFuture<List<IPerformanceGame>> games =
                CompletableFuture.supplyAsync(() -> dashboardRepository.statisticsCollegePerformance(collegeId), executor);

        return CompletableFuture.allOf(activities, games, college, logins)
                .thenApply(v -> DashboardFunction.build(
                        activities.join(),
                        games.join(),
                        college.join(),
                        logins.join()))
                .thenApply(Factories::createCollegeDashboard)
                .thenApply(Optional::of)
                .exceptionally(e -> {
                    log.error("Error fetching data needed to generate college statistics", e);
                    return Optional.empty();
                })
                .join();
    }

    private Optional<GradeDashboardDto> buildCourseGrade(int collegeId, String grade, String courseId) {
        Optional<Courses> c = coursesRepository.findFirstByCourseName(courseId);
        if (c.isEmpty()) {
            return Optional.empty();
        }

        int courseDbId = c.get().getId();

        CompletableFuture<List<Students>> students =
                CompletableFuture.supplyAsync(() -> studentsRepository.findStudentsByCollegeAndGrade(collegeId, grade, courseDbId), executor);

        CompletableFuture<List<Object[]>> logins =
                CompletableFuture.supplyAsync(() -> dashboardRepository.loginCourseGrade(collegeId, grade, courseDbId), executor);

        CompletableFuture<List<IPerformanceActivity>> activities =
                CompletableFuture.supplyAsync(() -> dashboardRepository.statistics(collegeId, grade, courseDbId), executor);

        CompletableFuture<List<IPerformanceGame>> games =
                CompletableFuture.supplyAsync(() -> dashboardRepository.statisticsPerformance(collegeId, grade, courseDbId), executor);

        return CompletableFuture.allOf(activities, games, students, logins)
                .thenApply(v -> DashboardFunction.build(
                        activities.join(),
                        games.join(),
                        students.join(),
                        logins.join()))
                .thenApply(Factories::createCourseGradeDashboard)
                .thenApply(Optional::of)
                .exceptionally(e -> {
                    log.error("Error fetching data needed to generate course grade dashboard", e);
                    return Optional.empty();
                })
                .join();
    }

    public Optional<StudentDashboardDto> buildStudent(String document) {
        CompletableFuture<Optional<Students>> students =
                CompletableFuture.supplyAsync(() -> studentsRepository.findById(document), executor);

        CompletableFuture<List<IPerformanceActivity>> activities =
                CompletableFuture.supplyAsync(() -> dashboardRepository.statisticsStudent(document), executor);

        CompletableFuture<List<IPerformanceGame>> games =
                CompletableFuture.supplyAsync(() -> dashboardRepository.statisticsStudentPerformance(document), executor);

        return CompletableFuture.allOf(activities, games, students)
                .thenApply(v -> {
                    Optional<Students> studentOpt = students.join();
                    // or handle gracefully
                    return studentOpt.map(value -> DashboardFunction.build(
                            activities.join(),
                            games.join(),
                            value)).orElse(null);

                })
                .thenApply(result -> result == null ? null : Factories.createStudentDashboard(result))
                .thenApply(Optional::ofNullable)
                .exceptionally(e -> {
                    log.error("Error fetching data needed to generate student dashboard", e);
                    return Optional.empty();
                })
                .join();
    }
}
