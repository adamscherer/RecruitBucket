package com.roundarch.repository;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.annconia.security.repository.SecurityUserRepository;
import com.roundarch.entity.RecruitEntity;

/**
 * Test case to show the usage of {@link SecurityUserRepository} and thus the Mongo
 * repository support in general.
 * 
 * @author Adam Scherer
 */

public class RecruitRepositoryIntegrationTest extends AbstractIntegrationTest {

	@Autowired
	RecruitRepository repository;
	
	@Test
	public void saveRecruit() throws Exception {
		RecruitEntity recruit = new RecruitEntity();
		recruit.setFirstName("Sam");
		recruit.setLastName("Tester");

		repository.save(recruit);
	}

}
