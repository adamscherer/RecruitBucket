package com.annconia.security;

import junit.framework.Assert;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.annconia.security.entity.SecurityUser;

public class DefaultSecurityManagerIntegrationTest extends AbstractIntegrationTest {

	@Autowired
	SecurityManager securityManager;

	@Test
	public void createUser() {
		SecurityUser user = new SecurityUser("adam.scherer@hotmail.com", "password");

		securityManager.createUser(user);

		Assert.assertNotNull(user.getId());
	}

}
