package org.bilan.co.bilanbackend.api;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import org.bilan.co.bilanbackend.domain.entities.Greeting;

@RestController
public class GreetingsController {
	private static final String template = "Hello, %s!";

	@GetMapping("/greetings")
	public Greeting greeting(@RequestParam(value = "name", defaultValue = "World!") String name){
		return new Greeting(String.format(template, name));
	}
}
