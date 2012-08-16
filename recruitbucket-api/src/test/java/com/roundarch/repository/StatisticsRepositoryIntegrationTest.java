package com.roundarch.repository;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.annconia.security.repository.SecurityUserRepository;

/**
 * Test case to show the usage of {@link SecurityUserRepository} and thus the Mongo
 * repository support in general.
 * 
 * @author Adam Scherer
 */

public class StatisticsRepositoryIntegrationTest extends AbstractIntegrationTest {

	@Autowired
	StatisticsRepository repository;

	@Test
	public void runRecruitMapReduce() throws Exception {

	}

}
