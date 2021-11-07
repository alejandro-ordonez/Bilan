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
        File csv = new File("D:\\Base de datos Bilan.csv");

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

            if(!document.isEmpty() && !document.equals("sininformacion") && !document.equals("sininformación")){
                student.setDocument(studentString[0]);

                student.setEmail("");
                student.setLastName(studentString[2]);
                student.setName(studentString[3]);
                student.setPassword(passwordEncoder.encode(studentString[0]));
                student.setGrade(studentString[4]);
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

    @Test
    public void insertUsers()
    {

        Students s = new Students();
        s.setDocumentType(DocumentType.TI);
        s.setGrade("10");
        s.setCreatedAt(new Date());

        s.setName("Clara");
        s.setLastName("Cordoba");
        s.setDocument("41576854");
        s.setPassword(passwordEncoder.encode("41576854"));

        studentsRepository.save(s);

        Students s1 = new Students();
        s1.setDocumentType(DocumentType.TI);
        s1.setGrade("10");
        s1.setCreatedAt(new Date());

        s1.setName("Carolina");
        s1.setLastName("");
        s1.setDocument("36751671");
        s1.setPassword(passwordEncoder.encode("36751671"));

        studentsRepository.save(s1);

        Students s2 = new Students();
        s2.setDocumentType(DocumentType.TI);
        s2.setGrade("10");
        s2.setCreatedAt(new Date());

        s2.setName("José");
        s2.setLastName("");
        s2.setDocument("79736107");
        s2.setPassword(passwordEncoder.encode("79736107"));

        studentsRepository.save(s2);

        Students s3 = new Students();
        s3.setDocumentType(DocumentType.TI);
        s3.setGrade("10");
        s3.setCreatedAt(new Date());

        s3.setName("Juan");
        s3.setLastName("");
        s3.setDocument("80174793");
        s3.setPassword(passwordEncoder.encode("80174793"));

        studentsRepository.save(s3);

        Students s4 = new Students();
        s4.setDocumentType(DocumentType.TI);
        s4.setGrade("10");
        s4.setCreatedAt(new Date());

        s4.setName("Claudia");
        s4.setLastName("");
        s4.setDocument("52187403");
        s4.setPassword(passwordEncoder.encode("52187403"));

        studentsRepository.save(s4);
    }
}
