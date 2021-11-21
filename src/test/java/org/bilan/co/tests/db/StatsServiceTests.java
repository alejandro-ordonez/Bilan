package org.bilan.co.tests.db;

import org.bilan.co.application.game.IStudentStatsService;
import org.bilan.co.domain.dtos.StudentChallengesDto;
import org.bilan.co.domain.dtos.user.AuthDto;
import org.bilan.co.domain.dtos.user.UserStatsDto;
import org.bilan.co.domain.enums.DocumentType;
import org.bilan.co.utils.JwtTokenUtil;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class StatsServiceTests {

    @Autowired
    private IStudentStatsService statsService;
    @Autowired
    private JwtTokenUtil jwtTokenUtil;


    @Test
    public void InsertStatsTest()
    {
        //Generates a token to process the request
        AuthDto authDto = new AuthDto();
        authDto.setPassword("TestPassword");
        authDto.setDocumentType(DocumentType.CC);
        authDto.setDocument("1000111110");
        String jwt = jwtTokenUtil.generateToken(authDto);

        //Update to be applied
        UserStatsDto userStatsDto = new UserStatsDto();
        StudentChallengesDto studentChallenges = new StudentChallengesDto();
        studentChallenges.setCurrentPoints(115);
        studentChallenges.setChallengeId(1);
        studentChallenges.setTribeId(1);
        userStatsDto.getStudentChallenges().add(studentChallenges);
    }


    @Test
    public void UpdateStatsTest()
    {

    }

}
