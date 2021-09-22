package org.bilan.co;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;

@Slf4j
@SpringBootApplication
public class BilanBackendApplication {

	public static void main(String[] args) {
		SpringApplication.run(BilanBackendApplication.class, args);
	}
}
