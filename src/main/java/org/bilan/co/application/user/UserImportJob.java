package org.bilan.co.application.user;

import com.fasterxml.jackson.core.type.TypeReference;
import jakarta.annotation.PostConstruct;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lombok.extern.slf4j.Slf4j;
import org.bilan.co.application.files.IFileManager;
import org.bilan.co.domain.dtos.user.ImportRecordDto;
import org.bilan.co.domain.dtos.user.StagedImportRequestDto;
import org.bilan.co.domain.dtos.user.StudentImportDto;
import org.bilan.co.domain.dtos.user.TeacherEnrollDto;
import org.bilan.co.domain.dtos.user.enums.ImportStatus;
import org.bilan.co.domain.dtos.user.enums.ImportType;
import org.bilan.co.domain.entities.*;
import org.bilan.co.infraestructure.persistance.*;
import org.jetbrains.annotations.NotNull;
import org.jobrunr.jobs.context.JobRunrDashboardLogger;
import org.jobrunr.scheduling.JobScheduler;
import org.jobrunr.scheduling.cron.Cron;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Service
public class UserImportJob {

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
    private TribesRepository tribes;
    @Autowired
    private ClassroomRepository classrooms;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @PersistenceContext
    private EntityManager entityManager;

    public UserImportJob() {
        jobLogger = new JobRunrDashboardLogger(LoggerFactory.getLogger(UserImportJob.class));
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
        jobScheduler.scheduleRecurrently(Cron.every15seconds(), this::processQueuedImport);
    }

    public void processQueuedImport() {
        var pendingRequests = importRequestRepository.getPendingRequests().stream()
                .collect(Collectors.groupingBy(
                        ImportRequests::getType,
                        Collectors.mapping(
                                ImportRequests::getImportId,
                                Collectors.toList()
                        )
                ))
                .entrySet()
                .stream()
                .map(entry -> new ImportRecordDto(
                        entry.getKey(),
                        entry.getValue()
                ))
                .toList();

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
            case TeacherImport -> processTeacherImport(requests);
            case TeacherEnrollment -> processTeacherEnrollmentImports(requests);
        }
    }

    public void processStudentImports(List<String> requests) {
        jobLogger.info("Processing Student imports: {}", requests.size());

        for (String importId : requests) {
            var importRequest = importRequestRepository.findById(importId);

            if (importRequest.isEmpty())
                throw new IllegalArgumentException("Invalid request");

            var payloadPath = importRequest.get().getAcceptedFilePath();
            var payload = fileManager.getFromJsonFile(payloadPath, new TypeReference<StagedImportRequestDto<StudentImportDto>>() {
            });

            // Failed to read the file, marking as failed
            if (payload == null) {
                importRequest.get().setStatus(ImportStatus.Failed);
                importRequest.get().setAcceptedFilePath(null);
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
                jobLogger.error("Something went wrong when processing the import {}", importRequest.get().getImportId());
                importRequest.get().setStatus(ImportStatus.Failed);
            }

            importRequestRepository.save(importRequest.get());
        }
    }

    public void processStudentImport(StagedImportRequestDto<StudentImportDto> request) {
        List<Students> approvedStudents = new ArrayList<>();

        Optional<Colleges> college = colleges.findById(request.getCollegeId());

        if (college.isEmpty())
            throw new IllegalArgumentException("College not valid");

        for (var student : request.getProcessed()) {

            Optional<Courses> course = courses.findFirstByCourseName(student.getCourse());

            if (course.isEmpty())
                throw new IllegalArgumentException("Course not valid");

            Students studentEntity = getStudents(student, course.get(), college.get());
            studentEntity.setPassword(passwordEncoder.encode(studentEntity.getDocument()));

            approvedStudents.add(studentEntity);
        }

        studentsRepository.saveAll(approvedStudents);
    }

    public void processTeacherImport(List<String> requests) {

    }

    public void processTeacherEnrollmentImports(List<String> requests) {
        jobLogger.info("Processing Teacher enrollment imports: {}", requests.size());

        for (var importId : requests) {
            var importRequest = importRequestRepository.findById(importId);

            if (importRequest.isEmpty())
                throw new IllegalArgumentException("Invalid request");

            StagedImportRequestDto<TeacherEnrollDto> payload = fileManager
                    .getFromJsonFile(importRequest.get().getAcceptedFilePath(), new TypeReference<>() {
                    });

            if (payload == null) {
                importRequest.get().setStatus(ImportStatus.Failed);
                importRequest.get().setAcceptedFilePath(null);
                importRequest.get().setModified(LocalDateTime.now());
                importRequestRepository.save(importRequest.get());
                continue;
            }

            importRequest.get().setStatus(ImportStatus.Processing);
            importRequest.get().setModified(LocalDateTime.now());
            importRequestRepository.save(importRequest.get());

            try {
                processTeacherEnroll(payload);
                importRequest.get().setStatus(ImportStatus.Ok);
            } catch (Exception e) {
                jobLogger.error("Something went wrong when processing the import {}", importRequest.get().getImportId());
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

            Teachers teacherEntity = new Teachers();
            teacherEntity.setDocument(teacher.getDocument());
            teacherEntity.setDocumentType(teacher.getDocumentType());

            Optional<Tribes> tribe = tribes.getByName(teacher.getTribe());
            if (tribe.isEmpty())
                throw new IllegalArgumentException("Invalid tribe");

            Optional<Courses> course = courses.findFirstByCourseName(teacher.getCourse());
            if (course.isEmpty())
                throw new IllegalArgumentException("Invalid course");

            Classroom classroom = new Classroom();
            classroom.setTeacher(teacherEntity);
            classroom.setCollege(college.get());
            classroom.setCourse(course.get());
            classroom.setTribe(tribe.get());

            teacherEntity.setEmail("");
            teacherEntity.setPositionName("Profesor");

            approvedTeachers.add(classroom);
        }

        classrooms.saveAll(approvedTeachers);
    }
}
