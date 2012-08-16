package com.annconia.api.controller;

import java.util.Date;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.mock.web.MockServletConfig;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.web.servlet.DispatcherServlet;

import com.annconia.api.json.JsonUtils;
import com.annconia.security.entity.PasswordUtility;
import com.annconia.security.entity.SecurityUser;
import com.annconia.security.entity.Session;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration
public abstract class AbstractControllerIntegrationTest {

	private static DispatcherServlet dispatcher;

	@Autowired
	MongoOperations operations;

	@BeforeClass
	public static void setDispatchServlet() throws Exception {
		MockServletConfig config = new MockServletConfig("integration");
		config.addInitParameter("contextConfigLocation", "classpath:web-context.xml, classpath:com/annconia/api/controller/test-web-context.xml");

		dispatcher = new DispatcherServlet();
		dispatcher.init(config);
	}

	@Before
	public void loadData() throws Exception {
		operations.dropCollection("securityUser");
		operations.dropCollection("session");

		SecurityUser user = new SecurityUser("adam@test.com", PasswordUtility.encodePassword("password"));
		user.setFirstName("Adam");
		user.setLastName("Scherer");
		operations.insert(user);

		Session session = new Session("SESSION_TOKEN_VALUE");
		session.setCreationTime(new Date());
		session.setUserId(user.getId().toString());
		operations.insert(session);
	}

	protected MockHttpServletResponse handleRequest(HttpServletRequest request) {
		try {
			MockHttpServletResponse response = new MockHttpServletResponse();
			dispatcher.service(request, response);

			return response;
		} catch (Throwable ex) {
			throw new RuntimeException("Handle not successful: " + ex);
		}
	}

	protected MockHttpServletRequest createJsonRequest() {
		MockHttpServletRequest request = new MockHttpServletRequest();
		request.setContentType(MediaType.APPLICATION_JSON_VALUE);
		request.addHeader("Accept", MediaType.APPLICATION_JSON_VALUE);

		return request;
	}
	
	protected Map<String, Object> toJson(MockHttpServletResponse response) throws Exception {
		Map<String, Object> json = JsonUtils.fromJsonToMap(response.getContentAsString());
		System.out.println("Json: " + json);
		
		return json;
	}

}
