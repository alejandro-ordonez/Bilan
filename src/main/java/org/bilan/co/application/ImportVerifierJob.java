package org.bilan.co.application;

import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.bilan.co.application.files.IFileManager;
import org.bilan.co.domain.dtos.ImportIdentifier;
import org.bilan.co.domain.dtos.college.CollegeImportDto;
import org.bilan.co.domain.dtos.college.enums.CollegeImportIndexes;
import org.bilan.co.domain.dtos.user.*;
import org.bilan.co.domain.dtos.user.enums.*;
import org.bilan.co.domain.entities.Courses;
import org.bilan.co.domain.entities.ImportRequests;
import org.bilan.co.domain.entities.Tribes;
import org.bilan.co.domain.enums.BucketName;
import org.bilan.co.domain.utils.TransformersUtil;
import org.bilan.co.domain.utils.Tuple;
import org.bilan.co.infraestructure.persistance.*;
import org.bilan.co.utils.Constants;
import org.jobrunr.jobs.context.JobRunrDashboardLogger;
import org.jobrunr.scheduling.JobScheduler;
import org.jobrunr.scheduling.cron.Cron;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.stereotype.Service;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import java.io.IOException;
import java.nio.file.Files;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Slf4j
@Service
public class ImportVerifierJob {
    private final Logger jobLogger;

    @Autowired
    private ImportRequestRepository importRequestRepository;

    @Autowired
    private JobScheduler jobScheduler;

    @Autowired
    private IFileManager fileManager;

    @Autowired
    private Validator validator;

    @Autowired
    private TeachersRepository teachersRepository;

    @Autowired
    private ClassroomRepository classroomRepository;

    @Autowired
    private TribesRepository tribes;

    @Autowired
    private CoursesRepository courses;

    @Autowired
    private StateMunicipalityRepository municipalityRepository;

    private Map<String, Courses> availableCourses;

    public ImportVerifierJob(){
        jobLogger = new JobRunrDashboardLogger(LoggerFactory.getLogger(ImportProcessorJob.class));
    }

    @PostConstruct
    public void scheduleImportJob() {
        availableCourses = courses.findAll()
                        .stream()
                        .collect(Collectors.toMap(Courses::getName, course -> course));

        jobScheduler.scheduleRecurrently(
                "process-ready-for-verification",
                Cron.every30seconds(),
                this::processReadyForVerification);
    }

    /**
     * All requests that has been received and need validation. This will filter thr right rows and
     * create a file with the rejected lines.
     */
    public void processReadyForVerification(){
        var readyForVerification = TransformersUtil.getRequestsDto(importRequestRepository.getReadyForVerification());
        if (readyForVerification.isEmpty())
            return;

        jobLogger.info("Processing ready for verification requests: {}", readyForVerification.size());

        jobScheduler.enqueue(readyForVerification.stream(),
                (entry) -> selectVerifier(entry.getImportType(), entry.getImportIds()));
    }

    public void selectVerifier(ImportType importType, List<String> requests){
        switch (importType){
            case TeacherImport -> verifyTeacherImports(requests);
            case TeacherEnrollment -> verifyTeacherEnrollmentImports(requests);
            case StudentImport -> verifyStudentImports(requests);
            case CollegesImport -> verifyCollegeImport(requests);
        }
    }

    public void verifyTeacherImports(List<String> importIds){
        for(var requestId : importIds){
            var requestQuery = importRequestRepository.findById(requestId);

            if(requestQuery.isEmpty())
                continue;

            ImportRequests request = requestQuery.get();
            request.setStatus(ImportStatus.Verifying);
            importRequestRepository.save(request);

            ImportRequest<TeacherInfoImportDto> importRequest = ImportRequest.
                    <TeacherInfoImportDto>builder()
                    .requestId(requestId)
                    .collegeId(request.getCollegeId())
                    .requestorId(request.getRequestor().getDocument())
                    .importType(request.getType())
                    .expectedColumns(TeacherImportIndexes.values().length)
                    .converter(TeacherInfoImportDto::readFromStringArray)
                    .headers(Constants.TeacherImportHeaders)
                    .bucket(BucketName.BILAN_TEACHER)
                    .build();

            var result = processImport(importRequest);
            request.setStatus(result.getStatus());
            request.setProcessed(result.getAcceptedCount());
            request.setRejected(result.getRejectedCount());

            importRequestRepository.save(request);
        }
    }

    public void verifyTeacherEnrollmentImports(List<String> importIds){
        for(var requestId : importIds){
            var requestQuery = importRequestRepository.findById(requestId);

            if(requestQuery.isEmpty())
                continue;

            ImportRequests request = requestQuery.get();
            request.setStatus(ImportStatus.Verifying);
            importRequestRepository.save(request);

            ImportRequest<TeacherEnrollDto> importRequest = ImportRequest.
                    <TeacherEnrollDto>builder()
                    .requestId(requestId)
                    .collegeId(request.getCollegeId())
                    .requestorId(request.getRequestor().getDocument())
                    .importType(request.getType())
                    .expectedColumns(TeacherEnrollmentIndexes.values().length)
                    .validation((enrollment) -> this.validateTeacherEnroll(enrollment, request.getCollegeId()))
                    .converter(TeacherEnrollDto::readFromStringArray)
                    .headers(Constants.TeacherEnrollmentHeaders)
                    .bucket(BucketName.BILAN_TEACHER_ENROLLMENT)
                    .build();

            var result = processImport(importRequest);
            request.setStatus(result.getStatus());
            request.setProcessed(result.getAcceptedCount());
            request.setRejected(result.getRejectedCount());

            importRequestRepository.save(request);
        }
    }

    public void verifyStudentImports(List<String> importIds){
        for(var requestId : importIds){
            var requestQuery = importRequestRepository.findById(requestId);

            if(requestQuery.isEmpty())
                continue;

            ImportRequests request = requestQuery.get();
            request.setStatus(ImportStatus.Verifying);
            importRequestRepository.save(request);

            ImportRequest<StudentImportDto> importRequest = ImportRequest.
                    <StudentImportDto>builder()
                    .requestId(requestId)
                    .collegeId(request.getCollegeId())
                    .requestorId(request.getRequestor().getDocument())
                    .importType(request.getType())
                    .expectedColumns(StudentImportIndexes.values().length)
                    .validation(this::validateStudent)
                    .converter(StudentImportDto::readFromStringArray)
                    .headers(Constants.StudentImportHeaders)
                    .bucket(BucketName.BILAN_STUDENT_IMPORT)
                    .build();

            var result = processImport(importRequest);
            request.setStatus(result.getStatus());
            request.setProcessed(result.getAcceptedCount());
            request.setRejected(result.getRejectedCount());

            importRequestRepository.save(request);
        }
    }

    public void verifyCollegeImport(List<String> importIds) {
        for (var requestId : importIds) {
            var requestQuery = importRequestRepository.findById(requestId);

            if (requestQuery.isEmpty())
                continue;

            ImportRequests request = requestQuery.get();
            request.setStatus(ImportStatus.Verifying);
            importRequestRepository.save(request);

            ImportRequest<CollegeImportDto> importRequest = ImportRequest.
                    <CollegeImportDto>builder()
                    .requestId(requestId)
                    .collegeId(request.getCollegeId())
                    .requestorId(request.getRequestor().getDocument())
                    .importType(request.getType())
                    .validation(this::validateCollege)
                    .expectedColumns(CollegeImportIndexes.values().length)
                    .converter(CollegeImportDto::readFromStringArray)
                    .headers(Constants.CollegeImportHeaders)
                    .bucket(BucketName.BILAN_COLLEGE_IMPORT)
                    .build();

            var result = processImport(importRequest);
            request.setStatus(result.getStatus());
            request.setProcessed(result.getAcceptedCount());
            request.setRejected(result.getRejectedCount());

            importRequestRepository.save(request);
        }
    }

    public List<String> validateTeacherEnroll(TeacherEnrollDto teacher, int collegeId) {

        List<String> errors = new ArrayList<>();

        Optional<Tribes> tribe = tribes.getByName(teacher.getTribe());

        if(tribe.isEmpty())
            errors.add("La asignatura es incorrecta");

        Courses course = availableCourses.getOrDefault(teacher.getCourse(), null);

        if(course == null)
            errors.add("El curso es incorrecto");

        if(!teachersRepository.existsById(teacher.getDocument()))
            errors.add("El profesor no existe");

        if (tribe.isPresent() && course != null) {
            var alreadyExists = classroomRepository.classRoomExists(
                    teacher.getDocument(),
                    collegeId,
                    tribe.get().getId(),
                    course.getId(),
                    teacher.getGrade()
            );

            if (alreadyExists == 1)
                errors.add("El profesor ya se encuentra asignado");
        }


        return errors;
    }

    public List<String> validateStudent(StudentImportDto student){
        List<String> errors = new ArrayList<>();

        Courses course = availableCourses.getOrDefault(student.getCourse(), null);

        if(course == null)
            errors.add("El curso es incorrecto");

        return  errors;
    }


    public List<String> validateCollege(CollegeImportDto college) {
        var errors = new ArrayList<String>();

        var municipality = municipalityRepository.findByCodDane(college.getCodDaneMunicipality());

        if (municipality.isEmpty())
            errors.add("El municipio no pudo ser encontrado, verifique el código dane");

        return errors;
    }


    public <T extends ImportIdentifier> ImportResultDto processImport(ImportRequest<T> request){

        Tuple<ImportResultDto, List<T>> result = processFile(request);

        ImportResultDto importResult = result.getValue1();
        List<T> processed = result.getValue2();

        if (importResult.getStatus().equals(ImportStatus.Rejected)) {
            fileManager.saveRejectedImport(importResult);
            return importResult;
        }


        StagedImportRequestDto<T> importDto = new StagedImportRequestDto<>(
                request.getRequestId(),
                request.getImportType());

        importDto.setBucket(request.getBucket());

        if(request.getImportType() != ImportType.TeacherImport && request.getImportType() != ImportType.CollegesImport)
            importDto.setCollegeId(request.getCollegeId());

        importDto.addProcessed(processed);

        log.info("{}: Users to be processed: {}, rejected: {}",
                importDto.getImportType().name(),
                result.getValue2().size(),
                importResult.getRejectedRows().size());

        // Saves the file to be processed by the queue.
        fileManager.saveVerifiedUsers(importDto);

        // Save file to be corrected:
        if (!importResult.getRejectedRows().isEmpty())
            fileManager.saveRejectedImport(importResult);

        return importResult;
    }

    public <T extends ImportIdentifier> Tuple<ImportResultDto, List<T>> processFile(ImportRequest<T> request){

        ImportResultDto importResult = new ImportResultDto(ImportStatus.Verifying);
        importResult.setBucket(request.getBucket());
        importResult.setImportId(request.getRequestId());
        importResult.setHeaders(request.getHeaders());

        Set<T> results = new HashSet<>();

        var path = fileManager.buildPath(
                request.getBucket(),
                Constants.STAGED_PATH,
                request.getRequestId(),
                Constants.CSV
                );

        var index = new AtomicInteger();

        try (Stream<String> lines = Files.lines(path)) {
            lines.skip(1).forEach(line -> {
                // Process each line
                int lineNumber = index.getAndIncrement();
                String[] user;

                user = line.split(",");

                if(user.length != request.getExpectedColumns()){
                    RejectedRow rejectedRow = new RejectedRow("", lineNumber, line);
                    rejectedRow.addError("Error num. columnas", "No están todas las columnas esperadas");
                    importResult.addRejected(rejectedRow);
                    return;
                }

                T parsed;

                try{
                    parsed = request.getConverter().apply(user);
                }catch (IllegalArgumentException e) {
                    log.error("Failed to parse");
                    RejectedRow rejectedRow = new RejectedRow("", lineNumber, line);
                    rejectedRow.addError("Error", "La linea no pudo ser convertida");
                    importResult.addRejected(rejectedRow);
                    return;
                }

                Errors errors = validator.validateObject(parsed);

                if(errors.hasErrors()){
                    RejectedRow rejectedRow = new RejectedRow(parsed.getIdentifier(), lineNumber, line);
                    rejectedRow.setErrors(errors.getAllErrors()
                            .stream()
                            .map(DefaultMessageSourceResolvable::getDefaultMessage
                            ).toList());

                    importResult.addRejected(rejectedRow);

                    return;
                }

                Function<T, List<String>> specialValidation = request.getValidation();
                if(specialValidation != null ){

                    var validation = specialValidation.apply(parsed);

                    if(!validation.isEmpty()){
                        log.error("El registro no es válido");
                        RejectedRow rejectedRow = new RejectedRow(parsed.getIdentifier(), lineNumber, line);
                        rejectedRow.getErrors().addAll(validation);
                        importResult.addRejected(rejectedRow);
                        return;
                    }
                }

                if (!results.add(parsed)) {
                    RejectedRow rejectedRow = new RejectedRow((parsed.getIdentifier()), lineNumber, line);
                    rejectedRow.getErrors().add("El registro se encuentra duplicado en el archivo");
                    importResult.addRejected(rejectedRow);
                }
            });

            log.info("Finished reading the file");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }


        importResult.setAcceptedCount(results.size());

        if (!importResult.getRejectedRows().isEmpty() && !results.isEmpty())
            importResult.setStatus(ImportStatus.ApprovedWithErrors);

        else if (!importResult.getRejectedRows().isEmpty())
            importResult.setStatus(ImportStatus.Rejected);

        else
            importResult.setStatus(ImportStatus.Queued);

        return new Tuple<>(importResult, results.stream().toList());
    }
}
