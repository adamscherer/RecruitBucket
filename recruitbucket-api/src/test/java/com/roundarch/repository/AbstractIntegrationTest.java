package com.roundarch.repository;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.roundarch.entity.RecruitEntity;

/**
 * Base class for test cases acting as samples for our Mongo API.
 * 
 * @author Adam Scherer
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration
public abstract class AbstractIntegrationTest {

	static final String COLLECTION = "recruitEntity";

	@Autowired
	MongoOperations operations;

	@Autowired
	RecruitRepository repository;

	public List<RecruitEntity> recruits;

	@Before
	public void setUp() {

		dropCollection();

		recruits = new ArrayList<RecruitEntity>();
		recruits.add(createRecruit("John", "Doe", 4.75));
		recruits.add(createRecruit("Jane", "Doe", 4.75));

		repository.save(recruits);
	}

	protected RecruitEntity createRecruit(String firstName, String lastName, double bucketScore) {
		RecruitEntity recruit = new RecruitEntity();
		recruit.setFirstName("John");
		recruit.setLastName("Doe");
		//recruit.setSchool("Harvard");
		recruit.setBucketScore(4.75);
		return recruit;
	}

	public void dropCollection() {

		operations.dropCollection(COLLECTION);

	}

	public void dropCollection(String name) {
		operations.dropCollection(name);
	}

}
