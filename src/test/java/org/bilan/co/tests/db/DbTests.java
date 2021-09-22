package org.bilan.co.tests.db;

import io.jsonwebtoken.lang.Assert;
import org.bilan.co.domain.entities.*;
import org.bilan.co.domain.entities.builders.StudentStatsBuilder;
import org.bilan.co.domain.entities.builders.StudentsBuilder;
import org.bilan.co.infraestructure.persistance.ActionsRepository;
import org.bilan.co.infraestructure.persistance.StudentsRepository;
import org.bilan.co.domain.enums.DocumentType;
import org.bilan.co.infraestructure.persistance.TribesRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

@SpringBootTest
public class DbTests {

    @Autowired
    private StudentsRepository studentsRepository;
    @Autowired
    private TribesRepository tribesRepository;
    @Autowired
    private ActionsRepository actionsRepository;

    @Test
    public void seedDemoUsers(){
        String baseId = "100011111%d";
        for (int i = 0; i < 10; i++) {
            Students student = new StudentsBuilder().createStudents();
            student.setDocument(String.format(baseId, i));
            student.setCreatedAt(new Date());
            student.setModifiedAt(new Date());
            //TestPassword
            student.setPassword("$2y$10$TsLKZtRXkymAbDNQ.YZUke0y0CQqBo05ltziqR8LJIvv6jj0DGROi");
            student.setDocumentType(DocumentType.CC);
            studentsRepository.save(student);
        }
        long records = studentsRepository.count();
        Assert.isTrue(records > 0);

    }

    @Test
    public void insertUnregisteredData() {
        Students student = new StudentsBuilder().createStudents();
        student.setDocument("2000111112");
        student.setCreatedAt(new Date());
        student.setModifiedAt(new Date());
        student.setDocumentType(DocumentType.TI);
        studentsRepository.save(student);
    }

    @Test
    public void insertStatsWithStudent(){

        Random random = new Random();

        Tribes tribe = new Tribes();
        tribe.setName("TribeName");
        tribe.setCulture("Culture");

        tribesRepository.save(tribe);

        Actions actions = new Actions();
        actions.setName("ActionName");
        actions.setDescription("ActionDescription");
        actions.setRepresentative("Representative");
        actions.setIdTribe(tribe);

        actionsRepository.save(actions);

        StudentStats studentStats = new StudentStatsBuilder()
                .setGeneralTotems(5)
                .setAnalyticalTotems(3)
                .setCriticalTotems(3)
                .setCurrentCycle(1)
                .setCurrentCycleEnd(new Date())
                .createStudentStats();
        studentStats.setCurrentSpirits(3);

        List<StudentChallenges> studentChallengesList = new ArrayList<>();
        for (int i = 0; i < 10; i++) {

            Challenges challenges = new Challenges();
            challenges.setCost(random.nextInt(500));
            challenges.setName("Challenge"+i);
            challenges.setReward(random.nextInt(5));
            challenges.setQuestionsList(new ArrayList<>());
            challenges.setTimer(100);
            challenges.setType("Test");
            challenges.setIdAction(actions);
            actions.getChallengesList().add(challenges);

            StudentChallenges studentChallenges = new StudentChallenges(i);
            studentChallenges.setIdChallenge(challenges);
            studentChallenges.setCurrentPoints(random.nextInt(200));
            challenges.setStudentChallenges(studentChallenges);
            studentChallengesList.add(studentChallenges);
            studentChallenges.setIdStudentStat(studentStats);
        }
        studentStats.setStudentChallengesList(studentChallengesList);

        Students student = new StudentsBuilder()
                .setDocument("1234567895")
                .setDocumentType(DocumentType.CC)
                .setName("User")
                .setLastName("Test")
                .setEmail("user.test@test.com")
                .setPassword("$2y$10$TsLKZtRXkymAbDNQ.YZUke0y0CQqBo05ltziqR8LJIvv6jj0DGROi")
                .setStudentStats(studentStats)
                .createStudents();

        studentStats.setIdStudent(student);

        studentsRepository.save(student);
    }

    @Test
    public void deleteAll() {
        studentsRepository.deleteAll();
        long records = studentsRepository.count();
        Assert.isTrue(records == 0);
    }
}
