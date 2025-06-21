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
import org.bilan.co.domain.entities.UserInfo;
import org.bilan.co.domain.enums.BucketName;
import org.bilan.co.domain.enums.GameCycleStatus;
import org.bilan.co.domain.utils.Tuple;
import org.bilan.co.infraestructure.persistance.*;
import org.bilan.co.infraestructure.persistance.evidence.EvidenceRepository;
import org.bilan.co.utils.Constants;
import org.bilan.co.utils.JwtTokenUtil;
import org.jobrunr.scheduling.JobScheduler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardOpenOption;
import java.util.Date;
import java.util.List;
import java.util.Optional;

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

    @Autowired
    private JwtTokenUtil jwt;

    @Override
    public ResponseDto<GameCycleDto> resetGame(String jwtToken) {
        var user = jwt.getInfoFromToken(jwtToken);
        log.info("Reset requested by: {}", user.getDocument());

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
        jobScheduler.enqueue(() -> resetGameJob(gameCycle.get().getGameId(), user.getDocument()));

        var mapper = new ObjectMapper();
        var dto = mapper.convertValue(gameCycle.get(), GameCycleDto.class);

        return new ResponseDtoBuilder<GameCycleDto>()
                .setCode(200)
                .setResult(dto)
                .setDescription("El reset del juego está en proceso")
                .createResponseDto();
    }


    public void resetGameJob(String cycleId, String document) {

        var cycle = gameCycleRepository.findById(cycleId);
        if (cycle.isEmpty())
            return;

        // Save the files
        var generalStatisticsSuccess = writeGeneralStatisticsReport(cycleId);
        var stateStatisticsSuccess = writeStateStatisticsReport(cycleId);

        // Failed to get the statistics
        if (!generalStatisticsSuccess || !stateStatisticsSuccess) {
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

        UserInfo user = new UserInfo();
        user.setDocument(document);

        cycle.get().setClosingRequestedBy(user);
        cycle.get().setEndDate(new Date());
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

        var parentPath = statesStatisticsPath.getParent();
        if (parentPath != null && !Files.exists(parentPath)) {
            try {
                Files.createDirectories(parentPath);
            } catch (IOException e) {
                log.error("Failed to created directories for state statistics");
                return false;
            }
        }


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
        var generalStatisticsPath = fileManager.buildPath(
                BucketName.BILAN_GAME_CYCLES,
                cycleId,
                Constants.GENERAL_STATISTICS,
                Constants.CSV);

        // Get statistics for each type
        var statistics = dashboardService.statistics().getResult();

        // No statistics available for this state
        if(statistics == null)
            return false;

        var parentPath = generalStatisticsPath.getParent();
        if (parentPath != null && !Files.exists(parentPath)) {
            try {
                Files.createDirectories(parentPath);
            } catch (IOException e) {
                log.error("Failed to created directories for ");
                return false;
            }
        }

        try (var writer = Files.newBufferedWriter(generalStatisticsPath, StandardOpenOption.CREATE,
                StandardOpenOption.WRITE,
                StandardOpenOption.TRUNCATE_EXISTING)) {

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
                    if(dto.getGameStatus() == GameCycleStatus.Closed)
                        dto.setFileNames(List.of(Constants.GENERAL_STATISTICS + Constants.CSV, Constants.STATE_STATISTICS + Constants.CSV));
                    return dto;
                }).toList();

        pagedResponse.setData(dtos);

        return new ResponseDtoBuilder<PagedResponse<GameCycleDto>>()
                .setCode(200)
                .setResult(pagedResponse)
                .setDescription("Cycles retrieved")
                .createResponseDto();
    }

    @Override
    public Optional<Tuple<byte[], String>> getReportFile(String cycleId, String fileName) {

        var bytes = fileManager.downloadReportFile(cycleId, fileName);

        if (bytes == null)
            return Optional.empty();

        return Optional.of(new Tuple<>(bytes, "text/csv"));
    }
}
