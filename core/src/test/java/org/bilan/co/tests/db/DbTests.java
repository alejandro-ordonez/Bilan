package org.bilan.co.tests.db;

import io.jsonwebtoken.lang.Assert;
import org.bilan.co.domain.entities.StudentStats;
import org.bilan.co.domain.entities.builders.StudentsBuilder;
import org.bilan.co.infraestructure.persistance.StudentsRepository;
import org.bilan.co.domain.enums.DocumentType;
import org.bilan.co.domain.entities.Students;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Date;

@SpringBootTest
public class DbTests {

    @Autowired
    private StudentsRepository studentsRepository;

    @Test
    public void insertData(){
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
        StudentStats stats =

        Students student = new StudentsBuilder()
                .setDocument("123456789")
                .setDocumentType(DocumentType.CC)
                .setName("User")
                .setLastName("Test")
                .setEmail("user.test@test.com")
                .setPassword("$2y$10$TsLKZtRXkymAbDNQ.YZUke0y0CQqBo05ltziqR8LJIvv6jj0DGROi")
                .setStudentStatsList();
    }

    @Test
    public void deleteAll() {
        studentsRepository.deleteAll();
        long records = studentsRepository.count();
        Assert.isTrue(records == 0);
    }
}
