package com.annconia.security.repository;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;

import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.annconia.security.AbstractIntegrationTest;
import com.annconia.security.entity.SecurityUser;

/**
 * Test case to show the usage of {@link SecurityUserRepository} and thus the Mongo
 * repository support in general.
 * 
 * @author Adam Scherer
 */
public class SecurityUserRepositoryIntegrationTest extends AbstractIntegrationTest {

	@Autowired
	SecurityUserRepository repository;

	@Before
	public void purgeRepository() {
		repository.deleteAll();
		super.setUp();
		repository.save(users);
	}

	@Test
	public void createUsers() throws Exception {

		assertEquals(repository.count(), 2L);
	}

	@Test
	public void findByEmail() throws Exception {

		SecurityUser user = repository.findByUsername("adscherer@hotmail.com");

		assertThat(user.getEmail(), is("adscherer@hotmail.com"));
	}
	
	@Test
	public void addDuplicate() {

		//repository.save(new User("adscherer@hotmail.com", "pass"));
		assertEquals(repository.count(), 2L);
	}

}
