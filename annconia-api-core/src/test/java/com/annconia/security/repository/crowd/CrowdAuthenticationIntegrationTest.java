package com.annconia.security.repository.crowd;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.junit.Ignore;
import org.junit.Test;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;

import com.atlassian.crowd.exception.ApplicationAccessDeniedException;
import com.atlassian.crowd.exception.ApplicationPermissionException;
import com.atlassian.crowd.exception.ExpiredCredentialException;
import com.atlassian.crowd.exception.InactiveAccountException;
import com.atlassian.crowd.exception.InvalidAuthenticationException;
import com.atlassian.crowd.exception.InvalidTokenException;
import com.atlassian.crowd.exception.OperationFailedException;
import com.atlassian.crowd.integration.rest.service.factory.RestCrowdHttpAuthenticationFactory;

/**
 * Test case to show the usage of authenticating with Crowd.
 * 
 * @author Adam Scherer
 */
@Ignore
public class CrowdAuthenticationIntegrationTest {

	@Test
	public void authenticate() throws ExpiredCredentialException, InactiveAccountException, ApplicationPermissionException, InvalidAuthenticationException, OperationFailedException, ApplicationAccessDeniedException, InvalidTokenException {
		HttpServletRequest request = new MockHttpServletRequest();
		HttpServletResponse response = new MockHttpServletResponse();
		RestCrowdHttpAuthenticationFactory.getAuthenticator().authenticate(request, response, "ascherer", "#EDCvfr4");
	}

	/*
	@Test
	public void search() throws ExpiredCredentialException, InactiveAccountException, ApplicationPermissionException, InvalidAuthenticationException, OperationFailedException, ApplicationAccessDeniedException, InvalidTokenException, UserNotFoundException {
		ClientResourceLocator clientResourceLocator = new ClientResourceLocator("crowd.properties");
		com.atlassian.crowd.service.client.ClientProperties clientProperties = ClientPropertiesImpl.newInstanceFromResourceLocator(clientResourceLocator);
		final CrowdClient crowdClient = new RestCrowdClientFactory().newInstance(clientProperties);

		SearchRestriction searchrestriction = Combine.allOf(Restriction.on(UserTermKeys.FIRST_NAME).exactlyMatching("Adam"));

		List<String> results = crowdClient.searchUserNames(searchrestriction, 0, 10);
		System.out.println("results: " + results);
		
		User user = crowdClient.getUser("alevine");
		System.out.println("user: " + user);
		
		crowdClient.authenticateUser("ascherer", "#EDCvfr4");
		
		//crowdClient.authenticateSSOUser(userauthenticationcontext).authenticateUser("ascherer", "#EDCvfr4");
		
		System.out.println("groups: " + crowdClient.getGroupsForUser("alevine", 0, 10));
	}
	*/
}
