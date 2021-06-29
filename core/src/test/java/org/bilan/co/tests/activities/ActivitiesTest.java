package org.bilan.co.tests.activities;

import org.bilan.co.infraestructure.persistance.ActivitiesRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class ActivitiesTest {
	@Autowired
	private ActivitiesRepository activitiesRepository;

	@Test
	public void insertData() {
	}

}
