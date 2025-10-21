package org.bilan.co.application.student;

import lombok.extern.slf4j.Slf4j;
import org.bilan.co.application.user.UserService;
import org.bilan.co.domain.dtos.ResponseDto;
import org.bilan.co.domain.dtos.common.PagedResponse;
import org.bilan.co.domain.dtos.student.StudentDashboardDto;
import org.bilan.co.domain.dtos.student.StudentDto;
import org.bilan.co.domain.dtos.user.AuthenticatedUserDto;
import org.bilan.co.domain.entities.Courses;
import org.bilan.co.domain.entities.Evaluation;
import org.bilan.co.domain.entities.Evidences;
import org.bilan.co.domain.entities.Students;
import org.bilan.co.infraestructure.persistance.*;
import org.bilan.co.infraestructure.persistance.evidence.EvidenceRepository;
import org.bilan.co.utils.JwtTokenUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Component
public class StudentService implements IStudentService {

    @Autowired
    private EvidenceRepository evidenceRepository;
    @Autowired
    private QuestionsRepository questionsRepository;
    @Autowired
    private StudentsRepository studentsRepository;
    @Autowired
    private TeachersRepository teachersRepository;
    @Autowired
    private CollegesRepository collegesRepository;
    @Autowired
    private CoursesRepository coursesRepository;
    @Autowired
    private JwtTokenUtil jwtTokenUtil;
    @Autowired
    private StatsRepository statsRepository;
    @Autowired
    private ResolvedAnswerByRepository resolvedAnswerByRepository;
    @Autowired
    private TribesRepository tribesRepository;

    @Override
    public StudentDashboardDto getStudentStatsRecord(String document) {

        StudentDashboardDto studentStatsRecord = new StudentDashboardDto();
        studentStatsRecord.setDocument(document);

        Optional<Students> studentQuery = studentsRepository.findById(document);
        if (!studentQuery.isPresent())
            return new StudentDashboardDto();

        studentStatsRecord.setName(studentQuery.get().getName());
        studentStatsRecord.setLastName(studentQuery.get().getLastName());

        long totalQuestions = questionsRepository.count();
        Integer totalCheckedQuestions = resolvedAnswerByRepository.getQuestionsCompleted(document);
        totalCheckedQuestions = totalCheckedQuestions == null ? 0 : totalCheckedQuestions;

        long totalActivities = tribesRepository.count() * 3;
        Integer resolved = evidenceRepository.findUploadedAndEvaluated(document);
        resolved = resolved == null ? 0 : resolved;

        float progress = (float) (totalCheckedQuestions + resolved) / (float) (totalQuestions + totalActivities);
        studentStatsRecord.setProgressActivities(progress);

        studentStatsRecord.setTimeInApp(statsRepository.getTimeInApp(document));


        List<Evidences> evidences = evidenceRepository.getEvidencesEvaluated(document);

        Map<Integer, List<Evidences>> evidencesMap = evidences.stream()
                .collect(Collectors.groupingBy(e -> e.getTribe().getId()));

        HashMap<String, Integer> activityScores = getActivityScores(evidences);
        //HashMap<String, Integer> gameScore = getGameScore();

        studentStatsRecord.setActivityScore(activityScores);
        //studentStatsRecord.setGameScore(gameScore);

        return studentStatsRecord;
    }


    private HashMap<String, Integer> getActivityScores(List<Evidences> evidences) {

        HashMap<String, Integer> activityScores = new HashMap<>();

        Integer cbScore = 0;
        Integer ccScore = 0;
        Integer csScore = 0;
        Integer tribeScore = 0;
        Integer l, m, cn = 0;

        int counter = 0;

        for (Evidences evidence : evidences) {
            for (Evaluation evaluation : evidence.getEvaluations()) {
                ccScore += evaluation.getCcScore();
                cbScore += evaluation.getCbScore();
                csScore += evaluation.getCsScore();
                tribeScore += evaluation.getTribeScore();
                counter++;
            }
        }

        if (counter == 0)
            return new HashMap<>();

        activityScores.put("CC", ccScore / counter);
        activityScores.put("CP", cbScore / counter);
        activityScores.put("CS", csScore / counter);
        activityScores.put("CE", tribeScore / counter);

        return activityScores;
    }


    @Override
    public ResponseDto<StudentDashboardDto> getStudentStatsDashboard(String document) {
        return new ResponseDto<>("Dashboard retrieved", 200, getStudentStatsRecord(document));
    }


    private StudentDto parseStudent(Students student){
        StudentDto studentDto = new StudentDto();
        studentDto.setCourse(student.getCourses().getName());
        studentDto.setGrade(student.getGrade());
        studentDto.setName(student.getName());
        studentDto.setLastName(student.getLastName());
        studentDto.setDocument(student.getDocument());
        studentDto.setEmail(student.getEmail());
        studentDto.setIsEnabled(student.getIsEnabled());
        studentDto.setDocumentType(student.getDocumentType());
        studentDto.setUserType(student.getUserType());

        return studentDto;
    }

    @Override
    public ResponseDto<String> updateStudent(StudentDto studentDto){
        Optional<Students> studentToUpdate = studentsRepository.findById(studentDto.getDocument());

        if(!studentToUpdate.isPresent()){
            return new ResponseDto<>("Failed to update, not found", 404, "Error");
        }

        Students student = studentToUpdate.get();
        UserService.updateUserEntityFromDto(student, studentDto);

        Optional<Courses> courses = coursesRepository.findFirstByCourseName(studentDto.getCourse());
        if(!courses.isPresent()) return new ResponseDto<>("Invalid Course", 400, "Error");

        student.setCourses(courses.get());
        student.setGrade(studentDto.getGrade());

        return new ResponseDto<>("Student Updated successfully", 200, "Ok");
    }

    @Override
    public ResponseDto<PagedResponse<StudentDto>> getStudents(int nPage, String partialDocument, String jwt) {
        AuthenticatedUserDto authenticatedUserDto = jwtTokenUtil.getInfoFromToken(jwt);
        String codDaneSede = this.teachersRepository.getCodDaneSede(authenticatedUserDto.getDocument());

        Optional<Integer> collegeId = this.collegesRepository.getIdByCodDaneSede(codDaneSede);

        if (collegeId.isEmpty())
            throw new IllegalArgumentException("The college couldn't be found");

        Page<Students> query;

        String purgedDocument;

        if (partialDocument == null)
            purgedDocument = "";

        else
            purgedDocument = partialDocument.trim();

        if(purgedDocument.isEmpty())
            query = studentsRepository.getStudents(PageRequest.of(nPage, 10), authenticatedUserDto.getDocument(), collegeId.get());

        else{
            query = studentsRepository.searchStudentsWithDocument(
                    PageRequest.of(nPage, 10), partialDocument, authenticatedUserDto.getDocument(), collegeId.get());
        }

        PagedResponse<StudentDto> studentsResponse = new PagedResponse<>();
        studentsResponse.setNPages(query.getTotalPages());

        List<StudentDto> students = query.getContent()
                .stream()
                .map(this::parseStudent)
                .collect(Collectors.toList());

        studentsResponse.setData(students);

        return new ResponseDto<>("Users retrieved successfully", 200, studentsResponse);
    }
}
