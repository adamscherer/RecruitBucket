package com.annconia.security.repository.mongo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.security.authentication.BadCredentialsException;

import com.annconia.security.entity.PasswordUtility;
import com.annconia.security.entity.SecurityUser;
import com.annconia.security.repository.SecurityUserRepositoryExtension;

public class SecurityUserRepositoryImpl implements SecurityUserRepositoryExtension {

	@Autowired
	MongoOperations mongoOperations;

	public SecurityUser authenticate(String username, String password) {
		SecurityUser user = mongoOperations.findOne(new Query(Criteria.where("username").is(username)), SecurityUser.class);
		if (user == null) {
			throw new BadCredentialsException("User not found");
		}

		if (PasswordUtility.isValidPassword(password, user.getPassword())) {
			return user;
		}
		
		throw new BadCredentialsException("Bad credentials");
	}

}
