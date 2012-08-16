package com.roundarch.controller;

import java.net.URLConnection;

import org.apache.commons.io.IOUtils;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.core.io.ClassPathResource;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.mock.web.MockMultipartHttpServletRequest;

public class ResumeControllerIntegrationTest extends AbstractControllerIntegrationTest {

	@Test
	public void createResumeWithPost() throws Exception {
		MockMultipartHttpServletRequest request = new MockMultipartHttpServletRequest();
		request.setRequestURI("/resume/store");

		String filename = "sample_resume.docx";
		byte[] content = IOUtils.toByteArray(new ClassPathResource("/sample_resume.docx").getInputStream());
		MockMultipartFile mockMultipartFile = new MockMultipartFile("file", filename, URLConnection.guessContentTypeFromName(filename), content);
		request.addFile(mockMultipartFile);

		MockHttpServletResponse response = handleRequest(request);
		Assert.assertEquals(201, response.getStatus());
	}

}
