package org.bilan.co.tests.db;

import lombok.extern.slf4j.Slf4j;
import org.bilan.co.domain.entities.Roles;
import org.bilan.co.domain.entities.Teachers;
import org.bilan.co.domain.enums.DocumentType;
import org.bilan.co.infraestructure.persistance.TeachersRepository;
import org.bilan.co.infraestructure.persistance.UserInfoRepository;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Date;
import java.util.Objects;
import java.util.Optional;

@Slf4j
@SpringBootTest
public class InsertTeachersTest {

    @Autowired
    private TeachersRepository teachersRepository;
    @Autowired
    private UserInfoRepository userInfoRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Test
    @Disabled
    public void insertTestStudents() throws IOException {
        Files.readAllLines(Paths.get("PATH"))
                .parallelStream()
                .skip(1)
                .map(this::parseLine)
                .filter(Optional::isPresent)
                .map(Optional::get)
                .forEachOrdered(teacher -> this.teachersRepository.save(teacher));
    }

    private Optional<Teachers> parseLine(String line) {
        String[] studentString = line.split(",");

        Roles roles = new Roles();
        if (studentString[22].equalsIgnoreCase("DOCENTE")) {
            roles.setId(2);
        } else if (studentString[22].equalsIgnoreCase("Directivo Docente")) {
            roles.setId(3);
        }

        if (Objects.isNull(roles.getId())) {
            return Optional.empty();
        }

        Teachers userInfo = new Teachers();
        userInfo.setDocument(studentString[6]);
        userInfo.setDocumentType(DocumentType.CC);
        userInfo.setName((studentString[10].toUpperCase() + " " + studentString[11].toUpperCase()).trim());
        userInfo.setLastName((studentString[8].toUpperCase() + " " + studentString[9].toUpperCase()).trim());
        userInfo.setEmail(studentString[15]);
        userInfo.setPositionName(studentString[22]);
        userInfo.setIsEnabled(true);
        userInfo.setRole(roles);
        userInfo.setPassword(passwordEncoder.encode(studentString[6]));
        userInfo.setCreatedAt(new Date());
        userInfo.setModifiedAt(new Date());
        userInfo.setConfirmed(false);
        userInfo.setCodDaneMinResidencia(studentString[17]);
        userInfo.setCodDane(studentString[23]);
        userInfo.setCodDaneSede(studentString[24]);

        return Optional.of(userInfo);
    }
}
