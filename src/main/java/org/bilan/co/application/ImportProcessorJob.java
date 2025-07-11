package org.bilan.co.application;

import com.fasterxml.jackson.core.type.TypeReference;
import jakarta.annotation.PostConstruct;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.bilan.co.application.files.IFileManager;
import org.bilan.co.domain.dtos.college.CollegeImportDto;
import org.bilan.co.domain.dtos.user.StagedImportRequestDto;
import org.bilan.co.domain.dtos.user.StudentImportDto;
import org.bilan.co.domain.dtos.user.TeacherEnrollDto;
import org.bilan.co.domain.dtos.user.TeacherInfoImportDto;
import org.bilan.co.domain.dtos.user.enums.ImportStatus;
import org.bilan.co.domain.dtos.user.enums.ImportType;
import org.bilan.co.domain.entities.*;
import org.bilan.co.domain.enums.BucketName;
import org.bilan.co.domain.utils.TransformersUtil;
import org.bilan.co.infraestructure.persistance.*;
import org.bilan.co.utils.Constants;
import org.jetbrains.annotations.NotNull;
import org.jobrunr.jobs.context.JobRunrDashboardLogger;
import org.jobrunr.scheduling.JobScheduler;
import org.jobrunr.scheduling.cron.Cron;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.support.TransactionTemplate;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@Service
public class ImportProcessorJob {

    private final Logger jobLogger;
    @Autowired
    private ImportRequestRepository importRequestRepository;
    @Autowired
    private JobScheduler jobScheduler;
    @Autowired
    private IFileManager fileManager;
    @Autowired
    private CoursesRepository courses;
    @Autowired
    private CollegesRepository colleges;
    @Autowired
    private StudentsRepository studentsRepository;
    @Autowired
    private TeachersRepository teachersRepository;
    @Autowired
    private UserInfoRepository usersRepository;
    @Autowired
    private TribesRepository tribes;
    @Autowired
    private ClassroomRepository classrooms;
    @Autowired
    private CollegesRepository collegesRepository;
    @Autowired
    private StateMunicipalityRepository statesRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private PlatformTransactionManager transactionManager;

    private Map<String, Courses> availableCourses;

    public ImportProcessorJob() {
        jobLogger = new JobRunrDashboardLogger(LoggerFactory.getLogger(ImportProcessorJob.class));
    }

    @NotNull
    public static Students getStudents(StudentImportDto student, Courses course, Colleges college) {
        Roles role = new Roles();
        role.setId(1);

        Students studentEntity = new Students();
        studentEntity.setDocument(student.getDocument());
        studentEntity.setDocumentType(student.getDocumentType());
        studentEntity.setName(student.getName());
        studentEntity.setLastName(student.getLastName());
        studentEntity.setGrade(student.getGrade());
        studentEntity.setCourses(course);
        studentEntity.setRole(role);
        studentEntity.setColleges(college);
        studentEntity.setIsEnabled(true);
        studentEntity.setConfirmed(false);
        studentEntity.setEmail("");
        studentEntity.setPositionName("Estudiante");
        return studentEntity;
    }

    @PostConstruct
    public void scheduleImportJob() {
        availableCourses = courses.findAll()
                .stream()
                .collect(Collectors.toMap(Courses::getName, course -> course));

        jobScheduler.scheduleRecurrently(
                "process-queued-import",
                Cron.minutely(),
                this::processQueuedImport);
    }

    public void processQueuedImport() {
        var pendingRequests = TransformersUtil.getRequestsDto(importRequestRepository.getPendingRequests());

        if (pendingRequests.isEmpty())
            return;

        jobLogger.info("Processing pending requests: {}", pendingRequests.size());

        // Group them to launch multiple jobs to process the batch in the given frame;

        jobScheduler.enqueue(pendingRequests.stream(),
                (entry) -> selectProcess(entry.getImportType(), entry.getImportIds()));
    }

    public void selectProcess(ImportType type, List<String> requests) {
        switch (type) {
            case StudentImport -> processStudentImports(requests);
            case TeacherImport -> processTeacherImports(requests);
            case TeacherEnrollment -> processTeacherEnrollmentImports(requests);
            case CollegesImport -> processCollegeImports(requests);
        }
    }

    public void processStudentImports(List<String> requests) {
        jobLogger.info("Processing Student imports: {}", requests.size());

        for (String importId : requests) {
            var importRequest = importRequestRepository.findById(importId);

            if (importRequest.isEmpty())
                throw new IllegalArgumentException("Invalid request");

            var bucket = BucketName.getFromImportType(importRequest.get().getType());
            var payloadPath = fileManager.buildPath(bucket, Constants.QUEUED_PATH, importId, Constants.JSON);

            var payload = fileManager.getFromJsonFile(payloadPath.toString(),
                    new TypeReference<StagedImportRequestDto<StudentImportDto>>() {});

            // Failed to read the file, marking as failed
            if (payload == null) {
                importRequest.get().setStatus(ImportStatus.Failed);
                importRequest.get().setModified(LocalDateTime.now());
                importRequestRepository.save(importRequest.get());

                continue;
            }

            importRequest.get().setStatus(ImportStatus.Processing);
            importRequest.get().setModified(LocalDateTime.now());
            importRequestRepository.save(importRequest.get());

            try {
                processStudentImport(payload);
                importRequest.get().setStatus(ImportStatus.Ok);
            } catch (Exception e) {
                jobLogger.error("Something went wrong when processing the student import {}", importRequest.get().getImportId());
                importRequest.get().setStatus(ImportStatus.Failed);
            }

            importRequestRepository.save(importRequest.get());
        }
    }

    @Transactional
    public void processStudentImport(StagedImportRequestDto<StudentImportDto> request) {
        List<Students> approvedStudents = new ArrayList<>();

        Optional<Colleges> college = colleges.findById(request.getCollegeId());

        if (college.isEmpty())
            throw new IllegalArgumentException("College not valid");

        for (var student : request.getProcessed()) {

            Courses course = availableCourses.getOrDefault(student.getCourse(), null);

            if(course == null)
                throw new IllegalArgumentException("Course not valid");

            Students studentEntity = getStudents(student, course, college.get());
            studentEntity.setPassword(passwordEncoder.encode(studentEntity.getDocument()));

            approvedStudents.add(studentEntity);
        }

        studentsRepository.saveAll(approvedStudents);
    }


    public void processTeacherImports(List<String> requests) {
        jobLogger.info("Processing Teacher imports: {}", requests.size());

        for (String importId : requests) {
            var importRequest = importRequestRepository.findById(importId);

            if (importRequest.isEmpty())
                throw new IllegalArgumentException("Invalid request");

            var bucket = BucketName.getFromImportType(importRequest.get().getType());
            var payloadPath = fileManager.buildPath(bucket, Constants.QUEUED_PATH, importId, Constants.JSON);

            var payload = fileManager.getFromJsonFile(payloadPath.toString(),
                    new TypeReference<StagedImportRequestDto<TeacherInfoImportDto>>() {
                    });

            // Failed to read the file, marking as failed
            if (payload == null) {
                importRequest.get().setStatus(ImportStatus.Failed);
                importRequest.get().setModified(LocalDateTime.now());
                importRequestRepository.save(importRequest.get());

                continue;
            }

            importRequest.get().setStatus(ImportStatus.Processing);
            importRequest.get().setModified(LocalDateTime.now());
            importRequestRepository.save(importRequest.get());

            try {
                processTeacherImport(payload);
                importRequest.get().setStatus(ImportStatus.Ok);
            } catch (Exception e) {
                jobLogger.error("Something went wrong when processing the teacher import {}", importRequest.get().getImportId());
                importRequest.get().setStatus(ImportStatus.Failed);
            }

            importRequestRepository.save(importRequest.get());
        }
    }

    @Transactional
    public void processTeacherImport(StagedImportRequestDto<TeacherInfoImportDto> request) {
        List<Teachers> teachers = new ArrayList<>();

        for (var teacherInfo : request.getProcessed()) {
            Teachers teacher = new Teachers();

            teacher.setDocument(teacherInfo.getDocument());
            teacher.setDocumentType(teacherInfo.getDocumentType());
            teacher.setName(teacherInfo.getName());
            teacher.setLastName(teacherInfo.getLastName());
            teacher.setEmail(teacherInfo.getEmail());
            teacher.setIsEnabled(false);
            teacher.setPositionName(Constants.TEACHER);
            teacher.setCreatedAt(new Date());
            teacher.setModifiedAt(new Date());
            teacher.setConfirmed(false);
            teacher.setPassword(passwordEncoder.encode(teacher.getDocument()));

            Roles role = new Roles();
            role.setId(2);

            teacher.setRole(role);
            teachers.add(teacher);

            // These will be set on the enrollment
            teacher.setCollege(null);
        }

        teachersRepository.saveAll(teachers);
    }


    @Transactional
    public void processCollegeImports(List<String> requests) {
        jobLogger.info("Processing College imports: {}", requests.size());

        for (String importId : requests) {
            var importRequest = importRequestRepository.findById(importId);

            if (importRequest.isEmpty())
                throw new IllegalArgumentException("Invalid request");

            var bucket = BucketName.getFromImportType(importRequest.get().getType());
            var payloadPath = fileManager.buildPath(bucket, Constants.QUEUED_PATH, importId, Constants.JSON);

            var payload = fileManager.getFromJsonFile(payloadPath.toString(),
                    new TypeReference<StagedImportRequestDto<CollegeImportDto>>() {
                    });

            // Failed to read the file, marking as failed
            if (payload == null) {
                importRequest.get().setStatus(ImportStatus.Failed);
                importRequest.get().setModified(LocalDateTime.now());
                importRequestRepository.save(importRequest.get());

                continue;
            }

            importRequest.get().setStatus(ImportStatus.Processing);
            importRequest.get().setModified(LocalDateTime.now());
            importRequestRepository.save(importRequest.get());

            try {
                processCollegeImport(payload);
                importRequest.get().setStatus(ImportStatus.Ok);
            } catch (Exception e) {
                jobLogger.error("Something went wrong when processing the college import {}", importRequest.get().getImportId());
                importRequest.get().setStatus(ImportStatus.Failed);
            }

            importRequestRepository.save(importRequest.get());
        }
    }

    public void processCollegeImport(StagedImportRequestDto<CollegeImportDto> request) {
        List<Colleges> colleges = new ArrayList<>();

        for (var collegeInfo : request.getProcessed()) {

            var existing = collegesRepository.findByCodDaneSede(collegeInfo.getCodDaneSede());

            if (existing.isPresent())
                continue;

            var municipality = statesRepository.findByCodDane(collegeInfo.getCodDaneMunicipality());
            if (municipality.isEmpty())
                continue;

            Colleges college = new Colleges();
            college.setName(collegeInfo.getName());
            college.setCampusCodeDane(collegeInfo.getCodDaneSede());
            college.setCampusName(collegeInfo.getCampusName());
            college.setStateMunicipality(municipality.get());

            colleges.add(college);
        }

        collegesRepository.saveAll(colleges);
    }


    public void processTeacherEnrollmentImports(List<String> requests) {
        jobLogger.info("Processing Teacher enrollment imports: {}", requests.size());

        for (var importId : requests) {
            var importRequest = importRequestRepository.findById(importId);

            if (importRequest.isEmpty())
                throw new IllegalArgumentException("Invalid request");

            var bucket = BucketName.getFromImportType(importRequest.get().getType());
            var payloadPath = fileManager.buildPath(bucket, Constants.QUEUED_PATH, importId, Constants.JSON);

            var payload = fileManager.getFromJsonFile(payloadPath.toString(),
                    new TypeReference<StagedImportRequestDto<TeacherEnrollDto>>() {});

            if (payload == null) {
                importRequest.get().setStatus(ImportStatus.Failed);
                importRequest.get().setModified(LocalDateTime.now());
                importRequestRepository.save(importRequest.get());
                continue;
            }

            importRequest.get().setStatus(ImportStatus.Processing);
            importRequest.get().setModified(LocalDateTime.now());
            importRequestRepository.save(importRequest.get());

            try {

                TransactionTemplate transactionTemplate = new TransactionTemplate(transactionManager);
                transactionTemplate.executeWithoutResult(status -> {
                    processTeacherEnroll(payload);
                });

                importRequest.get().setStatus(ImportStatus.Ok);
            } catch (Exception e) {
                jobLogger.error("Something went wrong when processing the teacher enroll import {}", importRequest.get().getImportId());
                importRequest.get().setStatus(ImportStatus.Failed);
            }

            importRequestRepository.save(importRequest.get());
        }
    }

    public void processTeacherEnroll(StagedImportRequestDto<TeacherEnrollDto> request) {
        List<Classroom> approvedTeachers = new ArrayList<>();

        Optional<Colleges> college = colleges.findById(request.getCollegeId());

        if (college.isEmpty())
            throw new IllegalArgumentException("Invalid college");

        for (var teacher : request.getProcessed()) {

            Teachers teacherEntity = teachersRepository.getReferenceById(teacher.getDocument());

            Optional<Tribes> tribe = tribes.getByName(teacher.getTribe());
            if (tribe.isEmpty())
                throw new IllegalArgumentException("Invalid tribe");

            Courses course = availableCourses.getOrDefault(teacher.getCourse(), null);

            if(course == null)
                throw new IllegalArgumentException("Invalid course");

            teachersRepository.updateTeacherCollege(teacherEntity.getDocument(), college.get());

            Classroom classroom = new Classroom();
            classroom.setTeacher(teacherEntity);
            classroom.setCollege(college.get());
            classroom.setCourse(course);
            classroom.setGrade(teacher.getGrade());
            classroom.setTribe(tribe.get());

            approvedTeachers.add(classroom);
        }

        classrooms.saveAll(approvedTeachers);
    }
}
