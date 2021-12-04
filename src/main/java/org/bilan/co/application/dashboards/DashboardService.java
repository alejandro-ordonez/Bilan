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
    public ResponseDto<GeneralDashboardDto> govMunicipalityStatistics(Integer munId) {
        return this.buildGovMunicipalityStatistics(munId)
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

        CompletableFuture<List<IPerformanceActivity>> performanceActivities =
                supplyAsync(this.dashboardRepository::statistics);

        CompletableFuture<List<IPerformanceGame>> performanceGames =
                supplyAsync(this.dashboardRepository::statisticsPerformance);

        try {
            CompletableFuture.allOf(performanceActivities, performanceGames, states).get();

            MainDashboard info = new MainDashboard();
            info.setPerformanceActivities(performanceActivities.get());
            info.setPerformanceGames(performanceGames.get());
            info.setStates(states.get());

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

        CompletableFuture<List<IPerformanceActivity>> performanceActivities =
                supplyAsync(() -> this.dashboardRepository.statistics(state));

        CompletableFuture<List<IPerformanceGame>> performanceGames =
                supplyAsync(() -> this.dashboardRepository.statisticsPerformance(state));

        try {
            CompletableFuture.allOf(performanceActivities, performanceGames, municipalitiesByState).get();

            StateDashboard info = new StateDashboard();
            info.setPerformanceActivities(performanceActivities.get());
            info.setPerformanceGames(performanceGames.get());
            info.setMunicipalities(municipalitiesByState.get());

            return Optional.of(Factories.createStateDashboard(info));
        } catch (Exception e) {
            e.printStackTrace();
            log.error("Error fetching data needed to generate government statistics {}", e.getMessage());
        }
        return Optional.empty();
    }

    private Optional<GeneralDashboardDto> buildGovMunicipalityStatistics(Integer munId) {
        CompletableFuture<List<ICollege>> collegesByMun =
                supplyAsync(() -> this.collegesRepository.findByMunId(munId));

        CompletableFuture<List<IPerformanceActivity>> performanceActivities =
                supplyAsync(() -> this.dashboardRepository.statistics(munId));

        CompletableFuture<List<IPerformanceGame>> performanceGames =
                supplyAsync(() -> this.dashboardRepository.statisticsPerformance(munId));

        try {
            CompletableFuture.allOf(performanceActivities, performanceGames, collegesByMun).get();

            MunicipalityDashboard info = new MunicipalityDashboard();
            info.setPerformanceActivities(performanceActivities.get());
            info.setPerformanceGames(performanceGames.get());
            info.setColleges(collegesByMun.get());

            return Optional.of(Factories.createMunicipalityDashboard(info));
        } catch (Exception e) {
            e.printStackTrace();
            log.error("Error fetching data needed to generate government statistics {}", e.getMessage());
        }
        return Optional.empty();
    }

    private Optional<CollegeDashboardDto> buildGovCollegeStatistics(Integer collegeId) {

        CompletableFuture<ICollege> college =
                supplyAsync(() -> this.collegesRepository.singleById(collegeId));

        CompletableFuture<List<IPerformanceActivity>> performanceActivities =
                supplyAsync(() -> this.dashboardRepository.statisticsCollege(collegeId));

        CompletableFuture<List<IPerformanceGame>> performanceGames =
                supplyAsync(() -> this.dashboardRepository.statisticsCollegePerformance(collegeId));

        try {
            CompletableFuture.allOf(performanceActivities, performanceGames).get();

            CollegeDashboard info = new CollegeDashboard();
            info.setPerformanceActivities(performanceActivities.get());
            info.setPerformanceGames(performanceGames.get());
            info.setCollege(college.get());

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

        CompletableFuture<List<IPerformanceActivity>> performanceActivities =
                supplyAsync(() -> this.dashboardRepository.statistics(collegeId, grade, courseId));

        CompletableFuture<List<IPerformanceGame>> performanceGames =
                supplyAsync(() -> this.dashboardRepository.statisticsPerformance(collegeId, grade, courseId));

        try {
            CompletableFuture.allOf(performanceActivities, performanceGames).get();
            CourseGradeDashboard info = new CourseGradeDashboard();
            info.setPerformanceActivities(performanceActivities.get());
            info.setPerformanceGames(performanceGames.get());
            info.setStudents(students.get());
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

        CompletableFuture<List<IPerformanceActivity>> performanceActivities =
                supplyAsync(() -> this.dashboardRepository.statisticsStudent(document));

        CompletableFuture<List<IPerformanceGame>> performanceGames =
                supplyAsync(() -> this.dashboardRepository.statisticsStudentPerformance(document));
        try {
            CompletableFuture.allOf(performanceActivities, performanceGames).get();
            StudentDashboard info = new StudentDashboard();
            info.setPerformanceActivities(performanceActivities.get());
            info.setPerformanceGames(performanceGames.get());
            info.setStudent(students.get());
            return Optional.of(Factories.createStudentDashboard(info));
        } catch (Exception e) {
            e.printStackTrace();
            log.error("Error fetching data needed to generate college statistics {}", e.getMessage());
        }
        return Optional.empty();
    }
}
