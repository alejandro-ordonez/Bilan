package org.bilan.co.application.dashboards;

import lombok.extern.slf4j.Slf4j;
import org.bilan.co.domain.dtos.ResponseDto;
import org.bilan.co.domain.dtos.dashboard.CollegeDashboardDto;
import org.bilan.co.domain.dtos.dashboard.GovernmentDashboardDto;
import org.bilan.co.domain.projections.ICollege;
import org.bilan.co.domain.projections.IPerformanceActivity;
import org.bilan.co.domain.projections.IPerformanceGame;
import org.bilan.co.domain.dtos.user.AuthenticatedUserDto;
import org.bilan.co.domain.entities.Colleges;
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
    public ResponseDto<CollegeDashboardDto> collegeStatistics(AuthenticatedUserDto user) {
        return this.teachersRepository.findById(user.getDocument())
                .map(teachers -> this.collegesRepository.collegeByCampusCodeDane(teachers.getCodDaneSede()))
                .flatMap(this::getCollegeStatistics)
                .map(dashboard -> new ResponseDto<>("Dashboard", 200, dashboard))
                .orElse(new ResponseDto<>("Dashboard Not Found", 404, null));
    }

    @Override
    public ResponseDto<GovernmentDashboardDto> governmentStatistics() {
        return this.getGovernmentStatistics()
                .map(dashboard -> new ResponseDto<>("Dashboard", 200, dashboard))
                .orElse(new ResponseDto<>("Dashboard Not Found", 404, null));
    }

    @Override
    public ResponseDto<GovernmentDashboardDto> stateStatistics(String state) {
        return this.getStateStatistics(state)
                .map(dashboard -> new ResponseDto<>("Dashboard", 200, dashboard))
                .orElse(new ResponseDto<>("Dashboard Not Found", 404, null));
    }

    private Optional<CollegeDashboardDto> getCollegeStatistics(Colleges college) {

        CompletableFuture<List<IPerformanceActivity>> performanceActivities =
                supplyAsync(() -> this.dashboardRepository.statistics(college.getId()));

        CompletableFuture<List<IPerformanceGame>> performanceGames =
                supplyAsync(() -> this.dashboardRepository.statisticsPerformance(college.getId()));

        try {
            CompletableFuture.allOf(performanceActivities, performanceGames).get();

            Factories.CollegeDashboard info = new Factories.CollegeDashboard();
            info.setPerformanceActivities(performanceActivities.get());
            info.setPerformanceGames(performanceGames.get());
            info.setNumberOfStudents(college.getStudents().size());

            return Optional.of(Factories.newCollegeDashboard(info));
        } catch (Exception e) {
            e.printStackTrace();
            log.error("Error fetching data needed to generate college statistics {}", e.getMessage());
        }
        return Optional.empty();
    }

    private Optional<GovernmentDashboardDto> getGovernmentStatistics() {

        CompletableFuture<List<String>> states =
                supplyAsync(this.stateMunicipalityRepository::states);

        CompletableFuture<List<IPerformanceActivity>> performanceActivities =
                supplyAsync(this.dashboardRepository::statistics);

        CompletableFuture<List<IPerformanceGame>> performanceGames =
                supplyAsync(this.dashboardRepository::statisticsPerformance);

        try {
            CompletableFuture.allOf(performanceActivities, performanceGames, states).get();

            Factories.GovernmentDashboard info = new Factories.GovernmentDashboard();
            info.setPerformanceActivities(performanceActivities.get());
            info.setPerformanceGames(performanceGames.get());
            info.setStates(states.get());

            return Optional.of(Factories.newGovernmentDashboard(info));
        } catch (Exception e) {
            e.printStackTrace();
            log.error("Error fetching data needed to generate government statistics {}", e.getMessage());
        }
        return Optional.empty();
    }

    private Optional<GovernmentDashboardDto> getStateStatistics(String state) {

        CompletableFuture<List<ICollege>> collegesByState =
                supplyAsync(() -> this.collegesRepository.findByState(state));

        CompletableFuture<List<IPerformanceActivity>> performanceActivities =
                supplyAsync(() -> this.dashboardRepository.statistics(state));

        CompletableFuture<List<IPerformanceGame>> performanceGames =
                supplyAsync(() -> this.dashboardRepository.statisticsPerformance(state));

        try {
            CompletableFuture.allOf(performanceActivities, performanceGames, collegesByState).get();

            Factories.GovernmentStateDashboard info = new Factories.GovernmentStateDashboard();
            info.setPerformanceActivities(performanceActivities.get());
            info.setPerformanceGames(performanceGames.get());
            info.setColleges(collegesByState.get());

            return Optional.of(Factories.newGovernmentStateDashboard(info));
        } catch (Exception e) {
            e.printStackTrace();
            log.error("Error fetching data needed to generate government statistics {}", e.getMessage());
        }
        return Optional.empty();
    }
}
