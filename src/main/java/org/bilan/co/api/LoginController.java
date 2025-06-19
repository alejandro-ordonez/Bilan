package org.bilan.co.api;

import jakarta.validation.Valid;
import org.bilan.co.application.game.IGameCycleService;
import org.bilan.co.application.user.ILoginService;
import org.bilan.co.domain.dtos.ResponseDto;
import org.bilan.co.domain.dtos.user.AuthDto;
import org.bilan.co.domain.enums.GameCycleStatus;
import org.bilan.co.domain.enums.UserType;
import org.bilan.co.ws.simat.client.SimatEstudianteClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth/login")
public class LoginController {
    private final ILoginService loginService;
    private final IGameCycleService gameCycleService;

    public LoginController(ILoginService loginService, IGameCycleService gameCycleService) {
        this.loginService = loginService;
        this.gameCycleService = gameCycleService;
    }

    @PostMapping
    public ResponseEntity<ResponseDto<String>> login(@Valid @RequestBody AuthDto login) {
        var currentCycle = gameCycleService.getCurrentCycle();

        if(login.getUserType() != UserType.Admin &&
                currentCycle.getResult().getGameStatus() == GameCycleStatus.ProcessingClosing){

            return ResponseEntity
                    .status(503)
                    .build();
        }


        return loginService.doLogin(login);
    }
}
