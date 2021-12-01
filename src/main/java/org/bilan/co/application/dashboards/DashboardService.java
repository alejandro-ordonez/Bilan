package org.bilan.co.application.dashboards;

import lombok.extern.slf4j.Slf4j;
import org.bilan.co.domain.dtos.ResponseDto;
import org.bilan.co.domain.dtos.college.CollegeDashboardDto;
import org.bilan.co.domain.dtos.college.GovernmentDashboardDto;
import org.bilan.co.domain.dtos.college.IPerformanceActivities;
import org.bilan.co.domain.dtos.college.IPerformanceGame;
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
    public ResponseDto<CollegeDashboardDto> statistics(AuthenticatedUserDto user) {
        return this.teachersRepository.findById(user.getDocument())
                .map(teachers -> this.collegesRepository.collegeByCampusCodeDane(teachers.getCodDaneSede()))
                .flatMap(this::collectCollegeStatistics)
                .map(dashboard -> new ResponseDto<>("Dashboard", 200, dashboard))
                .orElse(new ResponseDto<>("Dashboard Not Found", 404, null));
    }

    @Override
    public ResponseDto<GovernmentDashboardDto> governmentStatistics() {
        return collectGovernmentStatistics()
                .map(dashboard -> new ResponseDto<>("Dashboard", 200, dashboard))
                .orElse(new ResponseDto<>("Dashboard Not Found", 404, null));
    }

    @Override
    public ResponseDto<GovernmentDashboardDto> stateStatistics(String state) {
        return collectStateStatistics(state)
                .map(dashboard -> new ResponseDto<>("Dashboard", 200, dashboard))
                .orElse(new ResponseDto<>("Dashboard Not Found", 404, null));
    }

    private Optional<CollegeDashboardDto> collectCollegeStatistics(final Colleges college) {
        CompletableFuture<List<IPerformanceActivities>> performanceActivities = supplyAsync(() -> this.dashboardRepository.statistics(college.getId()));
        CompletableFuture<List<IPerformanceGame>> performanceGames = supplyAsync(() -> this.dashboardRepository.statisticsPerformance(college.getId()));
        try {
            CompletableFuture.allOf(performanceActivities, performanceGames).get();
            return Optional.of(Factories.newCollegeDashboard(performanceActivities.get(),
                    performanceGames.get(), college.getStudents().size()));
        } catch (Exception e) {
            log.error("Error fetching data needed to generate college statistics {}", e.getMessage());
        }
        return Optional.empty();
    }

    private Optional<GovernmentDashboardDto> collectGovernmentStatistics() {
        CompletableFuture<List<String>> states = supplyAsync(this.stateMunicipalityRepository::states);
        CompletableFuture<List<IPerformanceActivities>> performanceActivities = supplyAsync(this.dashboardRepository::statistics);
        CompletableFuture<List<IPerformanceGame>> performanceGames = supplyAsync(this.dashboardRepository::statisticsPerformance);
        try {
            CompletableFuture.allOf(performanceActivities, performanceGames, states).get();
            return Optional.of(Factories.newGovernmentDashboard(performanceActivities.get(),
                    performanceGames.get(), states.get()));
        } catch (Exception e) {
            log.error("Error fetching data needed to generate government statistics {}", e.getMessage());
        }
        return Optional.empty();
    }

    private Optional<GovernmentDashboardDto> collectStateStatistics(String state) {
        CompletableFuture<List<IPerformanceActivities>> performanceActivities = supplyAsync(() -> this.dashboardRepository.statistics(state));
        CompletableFuture<List<IPerformanceGame>> performanceGames = supplyAsync(() -> this.dashboardRepository.statisticsPerformance(state));
        try {
            CompletableFuture.allOf(performanceActivities, performanceGames).get();
            return Optional.of(Factories.newGovernmentStateDashboard(performanceActivities.get(), performanceGames.get()));
        } catch (Exception e) {
            log.error("Error fetching data needed to generate government statistics {}", e.getMessage());
        }
        return Optional.empty();
    }


}
