package com.annconia.security;

import junit.framework.Assert;

import org.apache.commons.lang.time.StopWatch;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.AuthenticationException;

import com.annconia.security.core.context.SessionContext;
import com.annconia.security.entity.InvalidSessionException;

public class DefaultSessionManagerIntegrationTest extends AbstractIntegrationTest {

	@Autowired
	SessionManager sessionManager;

	private static final StopWatch watch = new StopWatch();

	@Test
	public void authenticate() {
		SessionContext sessionContext = sessionManager.authenticate("adscherer@hotmail.com", "password");

		Assert.assertEquals(sessionContext.getUser().getEmail(), "adscherer@hotmail.com");
	}

	@Test(expected = AuthenticationException.class)
	public void authenticateFailBadUsername() {
		sessionManager.authenticate("adscherer@hotmail2.com", "password");
	}

	@Test(expected = AuthenticationException.class)
	public void authenticateFailBadPassword() {
		sessionManager.authenticate("adscherer@hotmail.com", "password2");
	}

	@Test
	public void createSession() {
		SessionContext sessionContext = sessionManager.authenticate("adscherer@hotmail.com", "password");

		Assert.assertNotNull(sessionContext);
		Assert.assertNotNull(sessionContext.getSession().getId());
		Assert.assertTrue(sessionContext.getSession().getId().length() > 40);
	}

	@Test(expected = InvalidSessionException.class)
	public void getSessionFail() {
		sessionManager.authenticate("BAD KEY");
	}

	@Test(expected = InvalidSessionException.class)
	public void destroySession() {
		SessionContext sessionContext = sessionManager.authenticate("adscherer@hotmail.com", "password");

		sessionManager.destroySession(sessionContext.getSession().getId());
		sessionManager.authenticate(sessionContext.getSession().getId());
	}

	@Test(timeout = 15000)
	public void authenticatePerformanceTest() {
		watch.start();

		for (int i = 0; i < 500; i++) {
			sessionManager.authenticate("adscherer@hotmail.com", "password");
		}

		watch.stop();
		System.out.println("500 authentications took: " + watch.getTime() + "ms");
	}

	@Test(timeout = 20000)
	public void getSessionPerformanceTest() throws Exception {
		SessionContext sessionContext = sessionManager.authenticate("adscherer@hotmail.com", "password");
		watch.reset();
		watch.start();

		for (int i = 0; i < 500; i++) {
			sessionManager.authenticate(sessionContext.getSession().getId());
		}

		watch.stop();
		System.out.println("500 session lookups took: " + watch.getTime() + "ms");
	}
}
