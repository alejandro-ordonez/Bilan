package org.bilan.co.utils;

import lombok.extern.slf4j.Slf4j;
import org.bilan.co.infraestructure.persistance.TribesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class DatabaseSeeder {

    @Autowired
    private TribesRepository tribesRepository;

    @EventListener
    public void seedDatabase(ContextRefreshedEvent contextRefreshedEvent){
        log.info("Seed database triggered");
    }
}
