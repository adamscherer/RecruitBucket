package com.annconia.api.controller;

import java.util.Map;

import javax.servlet.http.Cookie;

import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;

@Ignore
public class SecurityControllerIntegrationTest extends AbstractControllerIntegrationTest {

	@Test
	public void badUserRequest() throws Exception {
		MockHttpServletRequest request = createJsonRequest();
		request.setMethod("GET");
		request.setRequestURI("/authenticate");
		request.addParameter("SID", "!@#$");
		MockHttpServletResponse response = handleRequest(request);

		Assert.assertEquals(401, response.getStatus());

		Cookie cookie = response.getCookie("SID");
		Assert.assertNotNull(cookie);
		Assert.assertEquals(0, cookie.getMaxAge());
	}

	@Test
	public void authenticateUser() throws Exception {
		MockHttpServletRequest request = createJsonRequest();
		request.setMethod("GET");
		request.setRequestURI("/authenticate");
		request.setParameter("username", "adam@test.com");
		request.setParameter("password", "password");
		request.addParameter("SID", "!@#$");

		MockHttpServletResponse response = handleRequest(request);
		Assert.assertEquals("application/json;charset=UTF-8", response.getContentType());

		Map<String, Object> json = toJson(response);
		Assert.assertEquals("adam@test.com", json.get("username"));
		
		Cookie cookie = response.getCookie("SID");
		Assert.assertNotNull(cookie);
		Assert.assertEquals(-1, cookie.getMaxAge());
	}

	@Test
	public void authenticateWithJsonp() throws Exception {
		MockHttpServletRequest request = createJsonRequest();
		request.setMethod("GET");
		request.setRequestURI("/authenticate");
		request.setParameter("username", "adam@test.com");
		request.setParameter("password", "password");
		request.setParameter("callback", "testing");

		MockHttpServletResponse response = handleRequest(request);
		Assert.assertEquals("text/javascript;charset=UTF-8", response.getContentType());

		System.out.println("Jsonp: " + response.getContentAsString());
	}

}
