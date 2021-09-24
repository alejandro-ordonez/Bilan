package org.bilan.co.utils;

import lombok.extern.slf4j.Slf4j;
import org.bilan.co.domain.entities.Actions;
import org.bilan.co.domain.entities.Tribes;
import org.bilan.co.infraestructure.persistance.ActionsRepository;
import org.bilan.co.infraestructure.persistance.TribesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Slf4j
@Component
public class DatabaseSeeder {

    @Autowired
    private TribesRepository tribesRepository;
    @Autowired
    private ActionsRepository actionsRepository;

    @EventListener
    public void seedDatabase(ContextRefreshedEvent contextRefreshedEvent) {
        log.info("Seed database triggered");
        actionsRepository.deleteAll();
        tribesRepository.deleteAll();
        seedTribes();
    }

    public void seedActions(Tribes tribe) {

        Actions spiritualAction = new Actions();
        spiritualAction.setTribe(tribe);
        spiritualAction.setName("Espiritual");
        spiritualAction.setRepresentative("No sé");

        Actions explorerAction = new Actions();
        explorerAction.setTribe(tribe);
        explorerAction.setName("Exploración");
        explorerAction.setRepresentative("?");

        Actions civilAction = new Actions();
        civilAction.setTribe(tribe);
        civilAction.setName("Civil");
        civilAction.setRepresentative("?");

        actionsRepository.saveAll(Arrays.asList(spiritualAction, explorerAction, civilAction));
    }

    public void seedTribes() {
        log.info("Seeding tribes");

        if(tribesRepository.count()>0)
            return;

        Tribes fireTribe = new Tribes();
        fireTribe.setElement("Fuego");
        fireTribe.setName("Fire Tribe");

        Tribes earthTribe = new Tribes();
        earthTribe.setElement("Tierra");
        earthTribe.setName("Earth tribe");

        Tribes waterTribe = new Tribes();
        waterTribe.setElement("Agua");
        waterTribe.setName("Water tribe");

        Tribes airTribe = new Tribes();
        airTribe.setElement("Aire");
        airTribe.setName("Air tribe");

        Tribes lightTribe = new Tribes();
        lightTribe.setElement("Light");
        lightTribe.setName("light tribe");

        tribesRepository.save(fireTribe);
        tribesRepository.save(earthTribe);
        tribesRepository.save(waterTribe);
        tribesRepository.save(airTribe);
        tribesRepository.save(lightTribe);

        lightTribe.setAdjacentTribeId(earthTribe);
        lightTribe.setOppositeTribeId(waterTribe);

        earthTribe.setAdjacentTribeId(waterTribe);
        earthTribe.setOppositeTribeId(airTribe);

        waterTribe.setAdjacentTribeId(airTribe);
        waterTribe.setOppositeTribeId(fireTribe);

        airTribe.setAdjacentTribeId(fireTribe);
        airTribe.setOppositeTribeId(lightTribe);

        fireTribe.setAdjacentTribeId(lightTribe);
        fireTribe.setOppositeTribeId(earthTribe);

        tribesRepository.save(fireTribe);
        tribesRepository.save(earthTribe);
        tribesRepository.save(waterTribe);
        tribesRepository.save(airTribe);
        tribesRepository.save(lightTribe);

        seedActions(fireTribe);
        seedActions(earthTribe);
        seedActions(waterTribe);
        seedActions(airTribe);
        seedActions(lightTribe);
    }
}
