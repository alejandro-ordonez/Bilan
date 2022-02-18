package org.bilan.co.tests.db;

import lombok.extern.slf4j.Slf4j;
import org.bilan.co.application.user.IUserService;
import org.bilan.co.domain.entities.*;
import org.bilan.co.domain.enums.DocumentType;
import org.bilan.co.infraestructure.persistance.StudentsRepository;
import org.bilan.co.infraestructure.persistance.TeachersRepository;
import org.bilan.co.infraestructure.persistance.UserInfoRepository;
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
    private TeachersRepository teachersRepository;
    @Autowired
    private UserInfoRepository userInfoRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private IUserService userService;

    @Test
    public void insertTestStudents() throws IOException {
        File csv = new File("D:\\23-11-2021.csv");

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
                student.setIsEnabled(true);
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
        s.setGrade("11");

        Courses c = new Courses();
        c.setId(3);

        s.setCourses(c);
        s.setCreatedAt(new Date());

        Colleges colleges = new Colleges();
        colleges.setId(31989);

        Roles r = new Roles();
        r.setId(1);

        s.setName("Valentina");
        s.setLastName("Echeverry Ramirez");
        s.setDocument("1006382038");
        s.setPassword(passwordEncoder.encode("1006382038"));

    }

    @Test
    public void loadUserFromSplitLine() {
        String[] student = new String[]
                {
                        "14442838",
                        "CC",
                        "RAUL",
                        "SALGADO CIFUENTES",
                        "11",
                        "C",
                };

        Colleges colleges = new Colleges();
        colleges.setId(31989);

        try {
            userService.processStudent(student,1, colleges);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void loadTeacherFromSplitLine() {
        String[] teacher = new String[]
                {
                        "14442838",
                        "CC",
                        "11",
                        "C",
                        "Ciencias Naturales"
                };

        Colleges colleges = new Colleges();
        colleges.setId(31989);

        try {
            userService.processTeacher(teacher, 1, colleges);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void updatePassword(){
        Teachers teachers = teachersRepository.findById("10000409").get();
        teachers.setPassword(passwordEncoder.encode(teachers.getDocument()));
    }

    @Test
    public void generatePassword(){
        log.info("Password: "+ passwordEncoder.encode("123456789"));
    }

}
