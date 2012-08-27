package com.roundarch.controller;

import java.net.URLConnection;
import java.util.Map;

import org.apache.commons.io.IOUtils;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.core.io.ClassPathResource;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.mock.web.MockMultipartHttpServletRequest;

public class RecruitControllerIntegrationTest extends AbstractControllerIntegrationTest {

	@Test
	public void createRecruitWithJson() throws Exception {
		MockHttpServletRequest request = createJsonRequest();
		request.setMethod("POST");
		request.setContentType("application/json");
		request.setRequestURI("/recruit");
		request.setContent("{'firstName' : 'Adam', \"lastName\" : \"Test\", \"school\" : \"Duke University\"}".getBytes());

		MockHttpServletResponse response = handleRequest(request);

		Map<String, Object> json = toJson(response);
		Assert.assertEquals(200, response.getStatus());
		Assert.assertNotNull(json.get("id"));
	}

	@Test
	public void createRecruitWithSingleQuotedJson() throws Exception {
		MockHttpServletRequest request = createJsonRequest();
		request.setMethod("POST");
		request.setContentType("application/json");
		request.setRequestURI("/recruit");
		request.setContent("{'firstName' : 'Adam', 'lastName' : 'Test', 'school' : \"Duke University\"}".getBytes());

		MockHttpServletResponse response = handleRequest(request);

		Map<String, Object> json = toJson(response);
		Assert.assertEquals(200, response.getStatus());
		Assert.assertNotNull(json.get("id"));
	}

	@Test
	public void createRecruitWithForm() throws Exception {
		MockHttpServletRequest request = createJsonRequest();
		request.setMethod("POST");
		request.setContentType("application/x-www-form-urlencoded");
		request.setRequestURI("/recruit");
		request.addParameter("firstName", "Adam");
		request.addParameter("lastName", "Scherer");
		request.addParameter("school", "Drake University");

		MockHttpServletResponse response = handleRequest(request);

		Map<String, Object> json = toJson(response);
		Assert.assertEquals(200, response.getStatus());
		Assert.assertNotNull(json.get("id"));
	}

	@Test
	public void get() throws Exception {
		MockHttpServletRequest request = createJsonRequest();
		request.setMethod("GET");
		request.setRequestURI("/recruit/1234");

		MockHttpServletResponse response = handleRequest(request);

		Map<String, Object> json = toJson(response);
		Assert.assertEquals(200, response.getStatus());
		Assert.assertEquals(json.get("id"), "1234");
		Assert.assertNull(json.get("reviews"));
	}

	@Test
	public void getComposite() throws Exception {
		MockHttpServletRequest request = createJsonRequest();
		request.setMethod("GET");
		request.setRequestURI("/recruit/composite/1234");

		MockHttpServletResponse response = handleRequest(request);

		Map<String, Object> json = toJson(response);
		Assert.assertEquals(200, response.getStatus());
		Assert.assertEquals(json.get("id"), "1234");
		Assert.assertNotNull(json.get("reviews"));
	}
	
	@Test
	public void createOrUpdate() throws Exception {
		MockHttpServletRequest request = createJsonRequest();
		request.setMethod("POST");
		request.setContentType("application/x-www-form-urlencoded");
		request.setRequestURI("/recruit/1234");
		request.addParameter("firstName", "TESTUPDATE");

		MockHttpServletResponse response = handleRequest(request);

		Map<String, Object> json = toJson(response);
		Assert.assertEquals(200, response.getStatus());
		Assert.assertEquals(json.get("firstName"), "TESTUPDATE");
		Assert.assertEquals(json.get("lastName"), "USER");
	}

	@Test
	public void createOrUpdateWithJson() throws Exception {
		MockHttpServletRequest request = createJsonRequest();
		request.setMethod("POST");
		request.setRequestURI("/recruit/1234");
		request.setContent("{\"firstName\" : \"TESTUPDATEJSON\"}".getBytes());

		MockHttpServletResponse response = handleRequest(request);

		Map<String, Object> json = toJson(response);
		Assert.assertEquals(200, response.getStatus());
		Assert.assertEquals(json.get("firstName"), "TESTUPDATEJSON");
		Assert.assertEquals(json.get("lastName"), "USER");
	}

	@Test
	public void createOrUpdateSchoolListWithJson() throws Exception {
		MockHttpServletRequest request = createJsonRequest();
		request.setMethod("POST");
		request.setRequestURI("/recruit/1234");
		request.setContent("{'firstName' : 'TESTUPDATEJSON', 'education' : [{'name' : 'Yale University'}, {'name' : 'Harvard University'}]}".getBytes());

		MockHttpServletResponse response = handleRequest(request);

		Map<String, Object> json = toJson(response);
		Assert.assertEquals(200, response.getStatus());
		Assert.assertEquals(json.get("firstName"), "TESTUPDATEJSON");
		Assert.assertEquals(json.get("lastName"), "USER");
	}
	
	@Test
	public void delete() throws Exception {
		MockHttpServletRequest request = createJsonRequest();
		request.setMethod("POST");
		request.setRequestURI("/recruit/delete/1234");

		MockHttpServletResponse response = handleRequest(request);

		Map<String, Object> json = toJson(response);
		Assert.assertEquals(204, response.getStatus());
		Assert.assertEquals(true, json.isEmpty());
	}

	@Test
	public void createRecruitAndResumeWithPost() throws Exception {
		MockMultipartHttpServletRequest request = new MockMultipartHttpServletRequest();
		request.setRequestURI("/recruit");
		request.addParameter("resume", "true");
		request.addParameter("firstName", "TEST");
		request.addParameter("lastName", "WITHRESUME");

		String filename = "sample_resume.docx";
		byte[] content = IOUtils.toByteArray(new ClassPathResource("/sample_resume.docx").getInputStream());
		MockMultipartFile mockMultipartFile = new MockMultipartFile("file", filename, URLConnection.guessContentTypeFromName(filename), content);
		request.addFile(mockMultipartFile);

		MockHttpServletResponse response = handleRequest(request);
		Assert.assertEquals(200, response.getStatus());
	}
}
