package org.bilan.co.tests.db;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;

import java.io.File;

@SpringBootTest
public class ExportDbTest {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Test
    public void exportDbTest(){
        File dump = new File("bilan.sql");
        if (dump.exists()) {
            dump.delete();
        }
        //this.jdbcTemplate.execute("mysqldump -u root -p R00t " + dump.getAbsolutePath());
    }
}
