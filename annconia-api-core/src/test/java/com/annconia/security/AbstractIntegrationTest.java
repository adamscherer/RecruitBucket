package com.annconia.security;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.annconia.security.entity.PasswordUtility;
import com.annconia.security.entity.SecurityUser;
import com.annconia.security.repository.SecurityUserRepository;

/**
 * Base class for test cases acting as samples for our Mongo API.
 * 
 * @author Adam Scherer
 */ 
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration
public abstract class AbstractIntegrationTest {

	static final String COLLECTION = "securityUser";

	@Autowired
	MongoOperations operations;

	@Autowired
	SecurityUserRepository repository;

	public List<SecurityUser> users;

	@Before
	public void setUp() {

		dropCollection();

		users = new ArrayList<SecurityUser>();

		users.add(new SecurityUser("adscherer@hotmail.com", PasswordUtility.encodePassword("password")));
		users.add(new SecurityUser("ascherer@roundarch.com", PasswordUtility.encodePassword("password")));

		repository.save(users);
	}

	public void dropCollection() {

		operations.dropCollection(COLLECTION);

	}

	public void dropCollection(String name) {

		operations.dropCollection(name);

	}

}
