package org.bilan.co.application.game;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.bilan.co.application.dashboards.DashboardService;
import org.bilan.co.application.files.IFileManager;
import org.bilan.co.application.general.GeneralInfoService;
import org.bilan.co.domain.dtos.ResponseDto;
import org.bilan.co.domain.dtos.ResponseDtoBuilder;
import org.bilan.co.domain.dtos.common.PagedResponse;
import org.bilan.co.domain.dtos.game.GameCycleDto;
import org.bilan.co.domain.entities.GameCycles;
import org.bilan.co.domain.enums.BucketName;
import org.bilan.co.domain.enums.GameCycleStatus;
import org.bilan.co.infraestructure.persistance.*;
import org.bilan.co.infraestructure.persistance.evidence.EvidenceRepository;
import org.bilan.co.utils.Constants;
import org.jobrunr.scheduling.JobScheduler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardOpenOption;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

@Service
@Slf4j
public class GameCycleService implements IGameCycleService{

    @Autowired
    private JobScheduler jobScheduler;

    @Autowired
    private GameCycleRepository gameCycleRepository;

    @Autowired
    private DashboardService dashboardService;

    @Autowired
    private UserInfoRepository users;

    @Autowired
    private GeneralInfoService generalInfoService;

    @Autowired
    private IFileManager fileManager;

    @Autowired
    private ClassroomRepository classroomRepository;

    @Autowired
    private StudentsRepository studentsRepository;

    @Autowired
    private StatsRepository statsRepository;

    @Autowired
    private EvidenceRepository evidences;

    @Autowired
    private EvaluationRepository evaluationRepository;

    @Autowired
    private SessionsRepository sessionsRepository;

    @Autowired
    private ResolvedAnswerByRepository resolvedAnswerByRepository;

    @Override
    public ResponseDto<GameCycleDto> resetGame() {
        var gameCycle = gameCycleRepository.getActiveCycle();

        if (gameCycle.isEmpty())
            return new ResponseDtoBuilder<GameCycleDto>()
                    .setCode(400)
                    .setResult(null)
                    .setDescription("No se encontró un juego activo")
                    .createResponseDto();

        // mark the current cycle as closing
        gameCycle.get().setGameStatus(GameCycleStatus.ProcessingClosing);
        gameCycleRepository.save((gameCycle.get()));

        // launch the job
        jobScheduler.enqueue(() -> resetGameJob(gameCycle.get().getGameId()));

        var mapper = new ObjectMapper();
        var dto = mapper.convertValue(gameCycle, GameCycleDto.class);

        return new ResponseDtoBuilder<GameCycleDto>()
                .setCode(200)
                .setResult(dto)
                .setDescription("El reset del juego está en proceso")
                .createResponseDto();
    }



    public void resetGameJob(String cycleId){

        var cycle = gameCycleRepository.findById(cycleId);
        if(cycle.isEmpty())
            return;

        // Save the files
        var generalStatisticsSuccess = writeGeneralStatisticsReport(cycleId);
        var stateStatisticsSuccess = writeStateStatisticsReport(cycleId);

        // Failed to get the statistics
        if(!generalStatisticsSuccess || !stateStatisticsSuccess){
            cycle.get().setGameStatus(GameCycleStatus.Active);
            gameCycleRepository.save(cycle.get());
            return;
        }

        // Remove all enrollments
        statsRepository.deleteAll();
        resolvedAnswerByRepository.deleteAll();
        sessionsRepository.deleteAll();
        evidences.deleteAll();
        evaluationRepository.deleteAll();
        classroomRepository.deleteAll();
        studentsRepository.deleteAll();

        // Set the cycle as closed
        cycle.get().setGameStatus(GameCycleStatus.Closed);
        gameCycleRepository.save(cycle.get());
    }

    /**
     * Writes a report based on the list of states
     * @param cycleId to where this report belongs to
     */
    public boolean writeStateStatisticsReport(String cycleId){
        var states = generalInfoService.getStates();

        var statesStatisticsPath = fileManager.buildPath(
                BucketName.BILAN_GAME_CYCLES,
                cycleId,
                Constants.STATE_STATISTICS,
                Constants.CSV);

        try (var writer = Files.newBufferedWriter(statesStatisticsPath, StandardOpenOption.CREATE,
                StandardOpenOption.WRITE,
                StandardOpenOption.TRUNCATE_EXISTING)){

            //title + "," + name + "," + logins + "," + performanceActivityScore + "," + performanceGameScore
            var header = List.of("Estado",
                    "Num Estudiantes",
                    "Tiempo en juego",
                    "Titulo",
                    "Nombre Tribu",
                    "Logins",
                    "Puntaje por actividad",
                    "Puntaje de juego");

            writer.write(String.join(",", header));
            writer.newLine();

            for (var state  : states.getResult()){
                var stateStatistics = dashboardService.stateStatistics(state.getStateName()).getResult();

                // No statistics available for this state
                if(stateStatistics == null)
                    continue;

                var stateStatisticsString = stateStatistics.getStudents() + "," + stateStatistics.getTimeInApp();

                var tribeSummary = stateStatistics.getData()
                        .stream()
                        .flatMap(row -> row.getModules().stream())
                        .map(tribeSummaryDto -> state + ", " + stateStatisticsString + ", " +
                                tribeSummaryDto.toCommaSeparated())
                        .toList();

                for (var line : tribeSummary){
                    writer.write(line);
                    writer.newLine();
                }
            }

            writer.flush();
            return true;

        } catch (IOException e) {
            log.error("Failed to write the statistics file", e);
            return false;
        }
    }

    public boolean writeGeneralStatisticsReport(String cycleId){
        var generalStatisticspath = fileManager.buildPath(
                BucketName.BILAN_GAME_CYCLES,
                cycleId,
                Constants.STATE_STATISTICS,
                Constants.CSV);

        // Get statistics for each type
        var statistics = dashboardService.statistics().getResult();

        // No statistics available for this state
        if(statistics == null)
            return false;

        try (var writer = Files.newBufferedWriter(generalStatisticspath, StandardOpenOption.CREATE,
                StandardOpenOption.WRITE,
                StandardOpenOption.TRUNCATE_EXISTING)){

            //title + "," + name + "," + logins + "," + performanceActivityScore + "," + performanceGameScore
            var header = List.of("Id ciclo",
                    "Num Estudiantes",
                    "Tiempo en juego",
                    "Titulo",
                    "Nombre Tribu",
                    "Logins",
                    "Puntaje por actividad",
                    "Puntaje de juego");

            writer.write(String.join(",", header));
            writer.newLine();

            var generalStatisticsString = statistics.getStudents() + "," + statistics.getTimeInApp();

            var tribeSummary = statistics.getData()
                    .stream()
                    .flatMap(row -> row.getModules().stream())
                    .map(tribeSummaryDto -> cycleId + ", " + generalStatisticsString + ", " +
                            tribeSummaryDto.toCommaSeparated())
                    .toList();

            for (var line : tribeSummary){
                writer.write(line);
                writer.newLine();
            }
            writer.flush();
            return true;

        } catch (IOException e) {
            log.error("Failed to write the statistics file", e);
            return false;
        }

    }

    @Override
    public ResponseDto<GameCycleDto> getCurrentCycle() {
        var cycle = gameCycleRepository.getActiveCycle();

        // No active cycle, creating a new one
        if (cycle.isEmpty()){
            var newCycle = new GameCycles();
            newCycle.setGameStatus(GameCycleStatus.Active);
            newCycle.setStartDate(new Date());
            gameCycleRepository.save(newCycle);
            cycle = java.util.Optional.of(newCycle);
        }


        var mapper = new ObjectMapper();
        var dto = mapper.convertValue(cycle.get(), GameCycleDto.class);

        return new ResponseDtoBuilder<GameCycleDto>()
                .setCode(200)
                .setResult(dto)
                .setDescription("Current cycle retrieved")
                .createResponseDto();
    }

    @Override
    public ResponseDto<PagedResponse<GameCycleDto>> getCycles(int pageNumber) {

        var page = PageRequest.of(pageNumber, 10);
        var cycles = gameCycleRepository.getCycles(page);

        var pagedResponse = new PagedResponse<GameCycleDto>();
        pagedResponse.setNPages(cycles.getTotalPages());

        var mapper = new ObjectMapper();

        var dtos = cycles.getContent()
                .stream()
                .map(cycle -> {
                    var dto = mapper.convertValue(cycle, GameCycleDto.class);
                    dto.setFileNames(List.of(Constants.GENERAL_STATISTICS, Constants.STATE_STATISTICS));
                    return dto;
                }).toList();

        pagedResponse.setData(dtos);

        return new ResponseDtoBuilder<PagedResponse<GameCycleDto>>()
                .setCode(200)
                .setResult(pagedResponse)
                .setDescription("Current cycle retrieved")
                .createResponseDto();
    }
}
