package org.bilan.co.tests.tribes;

import org.bilan.co.infraestructure.persistance.TribesRepository;
import org.bilan.co.domain.entities.Tribes;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;

@SpringBootTest
public class TribesTest {
  @Autowired
  private TribesRepository tribesRepository;

  @Test
  public void insertData() {
    List<Tribes> tribes = new ArrayList<>();

    tribes.add(createTribe("Competencias Socioemocionales", "Luz", "Talio"));
    tribes.add(createTribe("Lenguaje", "Tierra", "Geodin"));
    tribes.add(createTribe("Ciencias Naturales", "Agua", "Warten"));
    tribes.add(createTribe("Competencias Ciudadanas", "Aire", "Nebulan"));
    tribes.add(createTribe("Matemáticas", "Fuego", "Yarkin"));

    tribes = tribesRepository.saveAll(tribes);

    tribes.get(0).setAdjacentTribeId(tribes.get(1));
    tribes.get(0).setOppositeTribeId(tribes.get(2));

    tribes.get(1).setAdjacentTribeId(tribes.get(2));
    tribes.get(1).setOppositeTribeId(tribes.get(3));

    tribes.get(2).setAdjacentTribeId(tribes.get(3));
    tribes.get(2).setOppositeTribeId(tribes.get(4));

    tribes.get(3).setAdjacentTribeId(tribes.get(4));
    tribes.get(3).setOppositeTribeId(tribes.get(0));

    tribes.get(4).setAdjacentTribeId(tribes.get(0));
    tribes.get(4).setOppositeTribeId(tribes.get(1));

    tribesRepository.saveAll(tribes);

  }

  private Tribes createTribe(String name, String element, String culture) {
    Tribes tribe = new Tribes();
    tribe.setName(name);
    tribe.setElement(element);
    tribe.setCulture(culture);

    return tribe;
  }
}
