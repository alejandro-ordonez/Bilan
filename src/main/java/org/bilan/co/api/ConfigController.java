package org.bilan.co.api;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ConfigController {

    @GetMapping("/")
    public String home() {
        return "/swagger-ui.html";
    }
}