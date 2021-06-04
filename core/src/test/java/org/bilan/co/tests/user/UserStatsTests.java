package org.bilan.co.tests.user;

import org.bilan.co.application.IUserService;
import org.bilan.co.domain.dtos.AuthDto;
import org.bilan.co.domain.dtos.ResponseDto;
import org.bilan.co.domain.dtos.UserStatsDto;
import org.bilan.co.domain.enums.DocumentType;
import org.bilan.co.domain.enums.UserType;
import org.bilan.co.utils.JwtTokenUtil;
import org.eclipse.persistence.jpa.jpql.Assert;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class UserStatsTests {

    @Autowired
    private IUserService userService;
    @Autowired
    private JwtTokenUtil jwtTokenUtil;


    @Test
    public void getUserStatsDtoTest(){
        AuthDto authDto = new AuthDto();
        authDto.setPassword("$2y$10$TsLKZtRXkymAbDNQ");
        authDto.setDocument("1234567894");
        authDto.setDocumentType(DocumentType.CC);
        authDto.setUserType(UserType.Student);

        String jwt = jwtTokenUtil.generateToken(authDto);

        ResponseDto<UserStatsDto> userStatsDto = userService.getUserStats(jwt);
        Assert.isNotNull(userStatsDto, "Failed to find the user stats");
    }
}
