package com.annconia.security.repository.crowd;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.client.support.RestGatewaySupport;

import com.annconia.security.entity.SecurityUser;
import com.annconia.security.repository.SecurityUserRepository;
import com.atlassian.crowd.integration.rest.service.factory.RestCrowdHttpAuthenticationFactory;
import com.atlassian.crowd.model.user.User;

public class CrowdSecurityUserRepository extends RestGatewaySupport implements SecurityUserRepository {

	@Override
	public SecurityUser findByUsername(String username) {
		HttpServletRequest request = new MockHttpServletRequest();
		HttpServletResponse response = new MockHttpServletResponse();
		try {
			User user = RestCrowdHttpAuthenticationFactory.getAuthenticator().authenticateWithoutValidatingPassword(request, response, username);
			if (user == null) {
				throw new BadCredentialsException("User not found");
			}

			return convertUser(user, username);

		} catch (Throwable e) {
			throw new BadCredentialsException("User not found");
		}
	}

	public SecurityUser authenticate(String username, String password) {
		HttpServletRequest request = new MockHttpServletRequest();
		HttpServletResponse response = new MockHttpServletResponse();
		try {
			User user = RestCrowdHttpAuthenticationFactory.getAuthenticator().authenticate(request, response, username, password);
			if (user == null) {
				throw new BadCredentialsException("User not found");
			}

			return convertUser(user, username);

		} catch (Throwable e) {
			throw new BadCredentialsException("Bad credentials");
		}
	}

	public long count() {
		// TODO Auto-generated method stub
		return 0;
	}

	public void delete(String arg0) {
		// TODO Auto-generated method stub

	}

	public void delete(SecurityUser arg0) {
		// TODO Auto-generated method stub

	}

	public void delete(Iterable<? extends SecurityUser> arg0) {
		// TODO Auto-generated method stub

	}

	public void deleteAll() {
		// TODO Auto-generated method stub

	}

	public boolean exists(String arg0) {
		// TODO Auto-generated method stub
		return false;
	}

	public Iterable<SecurityUser> findAll() {
		// TODO Auto-generated method stub
		return null;
	}

	public SecurityUser findOne(String arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Iterable<SecurityUser> findAll(Iterable<String> arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <S extends SecurityUser> S save(S arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <S extends SecurityUser> Iterable<S> save(Iterable<S> arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	protected SecurityUser convertUser(User user, String username) {
		SecurityUser securityUser = new SecurityUser(username, user.getEmailAddress(), null);
		securityUser.setId(username);
		securityUser.setFirstName(user.getFirstName());
		securityUser.setLastName(user.getLastName());
		return securityUser;
	}

}
