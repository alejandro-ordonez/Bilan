package org.bilan.co.tests.db;

import lombok.extern.slf4j.Slf4j;
import org.bilan.co.domain.entities.Students;
import org.bilan.co.domain.enums.DocumentType;
import org.bilan.co.infraestructure.persistance.StudentsRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;

@Slf4j
@SpringBootTest
public class InsertTestUsers {

    @Autowired
    private StudentsRepository studentsRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Test
    public void insertTestStudents() throws IOException {
        File csv = new File("D:\\ESTUDIANTES PRUEBA ING2.csv");

        // Note:  Double backquote is to avoid compiler
        // interpret words
        // like \test as \t (ie. as a escape sequence)

        // Creating an object of BuffferedReader class
        BufferedReader br
                = new BufferedReader(new FileReader(csv));

        // Declaring a string variable
        String st;
        String[] studentString;
        Students student;
        ArrayList<Students> studentsArrayList = new ArrayList<>();
        // Consition holds true till
        // there is character in a string
        int i = 0;
        while ((st = br.readLine()) != null)
        {
            studentString = st.split(",");

            student = new Students();
            String document = studentString[0];

            if(!document.isEmpty()){
                student.setDocument(studentString[0]);

                student.setEmail(studentString[2]);
                student.setLastName(studentString[3]);
                student.setName(studentString[4]);
                student.setPassword(passwordEncoder.encode(studentString[0]));
                student.setGrade(studentString[6]);
                student.setCreatedAt(new Date());
                try{
                    student.setDocumentType(DocumentType.valueOf(studentString[1]));
                }
                catch (IllegalArgumentException e){
                    student.setDocumentType(DocumentType.TI);
                }

                i++;
                studentsArrayList.add(student);
                System.out.println(i);
            }

        }
        studentsRepository.saveAll(studentsArrayList);



    }
}