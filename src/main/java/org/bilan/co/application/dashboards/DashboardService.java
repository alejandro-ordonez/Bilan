package org.bilan.co.application.dashboards;

import lombok.extern.slf4j.Slf4j;
import org.bilan.co.application.dashboards.DataModel.*;
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
    public ResponseDto<GeneralDashboardDto> govStatistics() {
        return this.buildGovStatistics()
                .map(dashboard -> new ResponseDto<>("Dashboard", 200, dashboard))
                .orElse(new ResponseDto<>("Dashboard Not Found", 404, null));
    }

    @Override
    public ResponseDto<GeneralDashboardDto> govStateStatistics(String state) {
        return this.buildGovStateStatistics(state)
                .map(dashboard -> new ResponseDto<>("Dashboard", 200, dashboard))
                .orElse(new ResponseDto<>("Dashboard Not Found", 404, null));
    }

    @Override
    public ResponseDto<GeneralDashboardDto> govMunicipalityStatistics(Integer munId, PageRequest pageRequest) {
        return this.buildGovMunicipalityStatistics(munId, pageRequest)
                .map(dashboard -> new ResponseDto<>("Dashboard", 200, dashboard))
                .orElse(new ResponseDto<>("Dashboard Not Found", 404, null));
    }

    @Override
    public ResponseDto<CollegeDashboardDto> govCollegeStatistics(Integer collegeId) {
        return this.buildGovCollegeStatistics(collegeId)
                .map(dashboard -> new ResponseDto<>("Dashboard", 200, dashboard))
                .orElse(new ResponseDto<>("Dashboard Not Found", 404, null));
    }

    @Override
    public ResponseDto<GradeDashboardDto> govCourseGradeStatistics(Integer collegeId, String grade, Integer courseId) {
        return this.buildGovCourseGradeStatistics(collegeId, grade, courseId)
                .map(dashboard -> new ResponseDto<>("Dashboard", 200, dashboard))
                .orElse(new ResponseDto<>("Dashboard Not Found", 404, null));
    }

    @Override
    public ResponseDto<StudentDashboardDto> govStudentStatistics(String document) {
        return this.buildGovStudentStatistics(document)
                .map(dashboard -> new ResponseDto<>("Dashboard", 200, dashboard))
                .orElse(new ResponseDto<>("Dashboard Not Found", 404, null));
    }

    private Optional<GeneralDashboardDto> buildGovStatistics() {

        CompletableFuture<List<String>> states =
                supplyAsync(this.stateMunicipalityRepository::states);

        CompletableFuture<List<IPerformanceActivity>> activities =
                supplyAsync(this.dashboardRepository::statistics);

        CompletableFuture<List<IPerformanceGame>> games =
                supplyAsync(this.dashboardRepository::statisticsPerformance);

        try {
            CompletableFuture.allOf(activities, games, states).get();

            MainDashboard info = new MainDashboard(activities.get(), games.get(), states.get());
            return Optional.of(Factories.createMainDashboard(info));

        } catch (Exception e) {
            e.printStackTrace();
            log.error("Error fetching data needed to generate government statistics {}", e.getMessage());
        }
        return Optional.empty();
    }

    private Optional<GeneralDashboardDto> buildGovStateStatistics(String state) {

        CompletableFuture<List<IMunicipality>> municipalitiesByState =
                supplyAsync(() -> this.stateMunicipalityRepository.findMunicipalitiesByState(state));

        CompletableFuture<List<IPerformanceActivity>> activities =
                supplyAsync(() -> this.dashboardRepository.statistics(state));

        CompletableFuture<List<IPerformanceGame>> games =
                supplyAsync(() -> this.dashboardRepository.statisticsPerformance(state));

        try {
            CompletableFuture.allOf(activities, games, municipalitiesByState).get();

            StateDashboard info = new StateDashboard(activities.get(), games.get(), municipalitiesByState.get());
            return Optional.of(Factories.createStateDashboard(info));

        } catch (Exception e) {
            e.printStackTrace();
            log.error("Error fetching data needed to generate government statistics {}", e.getMessage());
        }
        return Optional.empty();
    }

    private Optional<GeneralDashboardDto> buildGovMunicipalityStatistics(Integer munId, PageRequest pageRequest) {
        CompletableFuture<List<ICollege>> collegesByMun =
                supplyAsync(() -> this.collegesRepository.findByMunId(munId));

        CompletableFuture<List<IPerformanceActivity>> activities =
                supplyAsync(() -> this.dashboardRepository.statistics(munId));

        CompletableFuture<List<IPerformanceGame>> games =
                supplyAsync(() -> this.dashboardRepository.statisticsPerformance(munId));

        try {
            CompletableFuture.allOf(activities, games, collegesByMun).get();

            MunicipalityDashboard info = new MunicipalityDashboard(activities.get(), games.get(), collegesByMun.get());
            return Optional.of(Factories.createMunicipalityDashboard(info,pageRequest));

        } catch (Exception e) {
            e.printStackTrace();
            log.error("Error fetching data needed to generate government statistics {}", e.getMessage());
        }
        return Optional.empty();
    }

    private Optional<CollegeDashboardDto> buildGovCollegeStatistics(Integer collegeId) {

        CompletableFuture<ICollege> college =
                supplyAsync(() -> this.collegesRepository.singleById(collegeId));

        CompletableFuture<List<IPerformanceActivity>> activities =
                supplyAsync(() -> this.dashboardRepository.statisticsCollege(collegeId));

        CompletableFuture<List<IPerformanceGame>> games =
                supplyAsync(() -> this.dashboardRepository.statisticsCollegePerformance(collegeId));

        try {
            CompletableFuture.allOf(activities, games).get();

            CollegeDashboard info = new CollegeDashboard(activities.get(), games.get(), college.get());
            return Optional.of(Factories.createCollegeDashboard(info));

        } catch (Exception e) {
            e.printStackTrace();
            log.error("Error fetching data needed to generate college statistics {}", e.getMessage());
        }
        return Optional.empty();
    }

    private Optional<GradeDashboardDto> buildGovCourseGradeStatistics(Integer collegeId,
                                                                      String grade, Integer courseId) {
        CompletableFuture<List<Students>> students =
                supplyAsync(() -> this.studentsRepository.findStudentsByCollegeAndGrade(collegeId, grade, courseId));

        CompletableFuture<List<IPerformanceActivity>> activities =
                supplyAsync(() -> this.dashboardRepository.statistics(collegeId, grade, courseId));

        CompletableFuture<List<IPerformanceGame>> games =
                supplyAsync(() -> this.dashboardRepository.statisticsPerformance(collegeId, grade, courseId));

        try {
            CompletableFuture.allOf(activities, games).get();

            CourseGradeDashboard info = new CourseGradeDashboard(activities.get(), games.get(), students.get());
            return Optional.of(Factories.createCourseGradeDashboard(info));

        } catch (Exception e) {
            e.printStackTrace();
            log.error("Error fetching data needed to generate college statistics {}", e.getMessage());
        }
        return Optional.empty();
    }

    private Optional<StudentDashboardDto> buildGovStudentStatistics(String document) {
        CompletableFuture<Students> students =
                supplyAsync(() -> this.studentsRepository.findById(document).get());

        CompletableFuture<List<IPerformanceActivity>> activities =
                supplyAsync(() -> this.dashboardRepository.statisticsStudent(document));

        CompletableFuture<List<IPerformanceGame>> games =
                supplyAsync(() -> this.dashboardRepository.statisticsStudentPerformance(document));
        try {
            CompletableFuture.allOf(activities, games).get();

            StudentDashboard info = new StudentDashboard(activities.get(), games.get(), students.get());
            return Optional.of(Factories.createStudentDashboard(info));

        } catch (Exception e) {
            e.printStackTrace();
            log.error("Error fetching data needed to generate college statistics {}", e.getMessage());
        }
        return Optional.empty();
    }
}
