package org.bilan.co.application.dashboards;

import lombok.extern.slf4j.Slf4j;
import org.bilan.co.domain.dtos.ResponseDto;
import org.bilan.co.domain.dtos.dashboard.CollegeDashboardDto;
import org.bilan.co.domain.dtos.dashboard.GeneralDashboardDto;
import org.bilan.co.domain.dtos.dashboard.GradeDashboardDto;
import org.bilan.co.domain.dtos.dashboard.StudentDashboardDto;
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

import static java.util.concurrent.CompletableFuture.supplyAsync;

@Slf4j
@Service
public class DashboardService implements IDashboardService {

    private final DashboardRepository dashboardRepository;
    private final TeachersRepository teachersRepository;
    private final StudentsRepository studentsRepository;
    private final CollegesRepository collegesRepository;
    private final StateMunicipalityRepository stateMunicipalityRepository;

    public DashboardService(DashboardRepository dashboardRepository, TeachersRepository teachersRepository,
                            StudentsRepository studentsRepository, CollegesRepository collegesRepository,
                            StateMunicipalityRepository stateMunicipalityRepository) {
        this.dashboardRepository = dashboardRepository;
        this.teachersRepository = teachersRepository;
        this.studentsRepository = studentsRepository;
        this.collegesRepository = collegesRepository;
        this.stateMunicipalityRepository = stateMunicipalityRepository;
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
    public ResponseDto<CollegeDashboardDto> collegeStatistics(Integer collegeId) {
        return this.buildCollege(collegeId)
                .map(dashboard -> new ResponseDto<>("Dashboard", 200, dashboard))
                .orElse(new ResponseDto<>("Dashboard Not Found", 404, null));
    }

    @Override
    public ResponseDto<GradeDashboardDto> courseGradeStatistics(Integer collegeId, String grade, Integer courseId) {
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
                supplyAsync(this.stateMunicipalityRepository::states);

        CompletableFuture<List<Object[]>> logins =
                supplyAsync(this.dashboardRepository::login);

        CompletableFuture<List<IPerformanceActivity>> activities =
                supplyAsync(this.dashboardRepository::statistics);

        CompletableFuture<List<IPerformanceGame>> games =
                supplyAsync(this.dashboardRepository::statisticsPerformance);

        try {
            return CompletableFuture
                    .allOf(activities, games, states, logins)
                    .thenApplyAsync(ig -> DashboardFunction.buildMainDashboard(activities.join(), games.join(),
                            states.join(), logins.join()))
                    .thenApply(Factories::createMainDashboard)
                    .thenApply(Optional::of)
                    .join();
        } catch (Exception e) {
            log.error("Error fetching data needed to generate government statistics", e);
        }
        return Optional.empty();
    }

    private Optional<GeneralDashboardDto> buildState(String state) {

        CompletableFuture<List<IMunicipality>> municipalitiesByState =
                supplyAsync(() -> this.stateMunicipalityRepository.findMunicipalitiesByState(state));

        CompletableFuture<List<Object[]>> logins =
                supplyAsync(() -> this.dashboardRepository.loginState(state));

        CompletableFuture<List<IPerformanceActivity>> activities =
                supplyAsync(() -> this.dashboardRepository.statistics(state));

        CompletableFuture<List<IPerformanceGame>> games =
                supplyAsync(() -> this.dashboardRepository.statisticsPerformance(state));

        try {
            return CompletableFuture
                    .allOf(activities, games, municipalitiesByState, logins)
                    .thenApplyAsync(ig -> DashboardFunction.buildState(activities.join(), games.join(),
                            municipalitiesByState.join(), logins.join()))
                    .thenApply(Factories::createStateDashboard)
                    .thenApply(Optional::of)
                    .join();
        } catch (Exception e) {
            log.error("Error fetching data needed to generate government statistics", e);
        }
        return Optional.empty();
    }

    private Optional<GeneralDashboardDto> buildMunicipality(Integer munId, PageRequest pageRequest) {

        CompletableFuture<List<ICollege>> collegesByMun =
                supplyAsync(() -> this.collegesRepository.findByMunId(munId));

        CompletableFuture<List<Object[]>> logins =
                supplyAsync(() -> this.dashboardRepository.loginMunicipality(munId));

        CompletableFuture<List<IPerformanceActivity>> activities =
                supplyAsync(() -> this.dashboardRepository.statistics(munId));

        CompletableFuture<List<IPerformanceGame>> games =
                supplyAsync(() -> this.dashboardRepository.statisticsPerformance(munId));

        try {
            return CompletableFuture
                    .allOf(activities, games, collegesByMun, logins)
                    .thenApplyAsync(ig -> DashboardFunction.buildMun(activities.join(), games.join(),
                            collegesByMun.join(), logins.join()))
                    .thenApply(info -> Factories.createMunicipalityDashboard(info, pageRequest))
                    .thenApply(Optional::of)
                    .join();
        } catch (Exception e) {
            log.error("Error fetching data needed to generate government statistics", e);
        }
        return Optional.empty();
    }

    private Optional<CollegeDashboardDto> buildCollege(Integer collegeId) {

        CompletableFuture<ICollege> college =
                supplyAsync(() -> this.collegesRepository.singleById(collegeId));

        CompletableFuture<List<Object[]>> logins =
                supplyAsync(() -> this.dashboardRepository.loginCollege(collegeId));

        CompletableFuture<List<IPerformanceActivity>> activities =
                supplyAsync(() -> this.dashboardRepository.statisticsCollege(collegeId));

        CompletableFuture<List<IPerformanceGame>> games =
                supplyAsync(() -> this.dashboardRepository.statisticsCollegePerformance(collegeId));

        try {
            return CompletableFuture
                    .allOf(activities, games, college, logins)
                    .thenApplyAsync(ig -> DashboardFunction.build(activities.join(), games.join(), college.join(),
                            logins.join()))
                    .thenApply(Factories::createCollegeDashboard)
                    .thenApply(Optional::of)
                    .join();
        } catch (Exception e) {
            e.printStackTrace();
            log.error("Error fetching data needed to generate college statistics {}", e.getMessage());
        }
        return Optional.empty();
    }

    private Optional<GradeDashboardDto> buildCourseGrade(Integer collegeId, String grade, Integer courseId) {

        CompletableFuture<List<Students>> students =
                supplyAsync(() -> this.studentsRepository.findStudentsByCollegeAndGrade(collegeId, grade, courseId));

        CompletableFuture<List<Object[]>> logins =
                supplyAsync(() -> this.dashboardRepository.loginCourseGrade(collegeId, grade, courseId));

        CompletableFuture<List<IPerformanceActivity>> activities =
                supplyAsync(() -> this.dashboardRepository.statistics(collegeId, grade, courseId));

        CompletableFuture<List<IPerformanceGame>> games =
                supplyAsync(() -> this.dashboardRepository.statisticsPerformance(collegeId, grade, courseId));

        try {
            return CompletableFuture
                    .allOf(activities, games, students)
                    .thenApplyAsync(ig -> DashboardFunction.build(activities.join(), games.join(),
                            students.join(), logins.join()))
                    .thenApply(Factories::createCourseGradeDashboard)
                    .thenApply(Optional::of)
                    .join();
        } catch (Exception e) {
            log.error("Error fetching data needed to generate college statistics", e);
        }
        return Optional.empty();
    }

    private Optional<StudentDashboardDto> buildStudent(String document) {

        CompletableFuture<Students> students =
                supplyAsync(() -> this.studentsRepository.findById(document).get());

        CompletableFuture<List<IPerformanceActivity>> activities =
                supplyAsync(() -> this.dashboardRepository.statisticsStudent(document));

        CompletableFuture<List<IPerformanceGame>> games =
                supplyAsync(() -> this.dashboardRepository.statisticsStudentPerformance(document));

        try {
            return CompletableFuture
                    .allOf(activities, games, students)
                    .thenApplyAsync(ig -> DashboardFunction.build(activities.join(), games.join(), students.join()))
                    .thenApply(Factories::createStudentDashboard)
                    .thenApply(Optional::of)
                    .join();
        } catch (Exception e) {
            log.error("Error fetching data needed to generate college statistics", e);
        }
        return Optional.empty();
    }
}
