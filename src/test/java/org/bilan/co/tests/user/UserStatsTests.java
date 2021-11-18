package org.bilan.co.tests.user;

import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.extern.slf4j.Slf4j;
import org.bilan.co.application.game.IStudentStatsService;
import org.bilan.co.application.user.IUserService;
import org.bilan.co.domain.dtos.user.AuthDto;
import org.bilan.co.domain.enums.DocumentType;
import org.bilan.co.domain.enums.UserType;
import org.bilan.co.utils.JwtTokenUtil;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@Slf4j
@SpringBootTest
public class UserStatsTests {

    @Autowired
    private IUserService userService;
    @Autowired
    private IStudentStatsService statsService;
    @Autowired
    private JwtTokenUtil jwtTokenUtil;


    @Test
    public void getUserStatsDtoTest() throws JsonProcessingException {
        AuthDto authDto = new AuthDto();
        authDto.setPassword("$2y$10$TsLKZtRXkymAbDNQ");
        authDto.setDocument("1234567895");
        authDto.setDocumentType(DocumentType.CC);
        authDto.setUserType(UserType.Student);

        String jwt = jwtTokenUtil.generateToken(authDto);
        /*
        ResponseDto<UserStatsDto> userStatsDto = statsService.getUserStats(jwt);

        ObjectMapper objectMapper = new ObjectMapper();
        String json  = objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(userStatsDto);
        log.info("Result: "+ json);
        Assert.isNotNull(userStatsDto, "Failed to find the user stats");*/
    }
}
