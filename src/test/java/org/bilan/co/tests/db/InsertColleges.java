package org.bilan.co.tests.db;

import org.bilan.co.domain.entities.Colleges;
import org.bilan.co.infraestructure.persistance.CollegesRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

@SpringBootTest
public class InsertColleges {

    @Autowired
    private CollegesRepository collegesRepository;

    @Test
    public void insertColleges() throws IOException {
        File csv = new File("D:\\Listado colegios.csv");

        // Note:  Double backquote is to avoid compiler
        // interpret words
        // like \test as \t (ie. as a escape sequence)

        // Creating an object of BuffferedReader class
        BufferedReader br
                = new BufferedReader(new FileReader(csv));

        // Declaring a string variable
        String col;
        String[] collegeString;
        Colleges college;
        ArrayList<Colleges> colleges = new ArrayList<>();
        int id = 1;

        while ((col = br.readLine()) != null)
        {
            collegeString = col.split(",");

            college = new Colleges();
            college.setId(id);
            college.setName(collegeString[0]);
            college.setCodDane(collegeString[1]);
            college.setState(collegeString[2]);

            colleges.add(college);
            id++;
        }

        collegesRepository.saveAll(colleges);
    }
}
