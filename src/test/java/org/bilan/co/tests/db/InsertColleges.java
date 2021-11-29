package org.bilan.co.tests.db;

import org.bilan.co.domain.entities.Colleges;
import org.bilan.co.infraestructure.persistance.CollegesRepository;
import org.junit.jupiter.api.Disabled;
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
    @Disabled
    public void insertColleges() throws IOException {
        File csv = new File("D:\\Listado colegios.csv");
        BufferedReader br  = new BufferedReader(new FileReader(csv));

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

            colleges.add(college);
            id++;
        }

        collegesRepository.saveAll(colleges);
    }
}
