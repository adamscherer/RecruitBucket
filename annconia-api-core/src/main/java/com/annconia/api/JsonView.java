package com.annconia.api;

import java.io.ByteArrayOutputStream;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.servlet.view.AbstractView;

import com.annconia.api.json.JsonResponse;
import com.annconia.api.json.JsonUtils;

public class JsonView extends AbstractView {

	private static final String DEFAULT_JSON_CONTENT_TYPE = "application/json";

	private String json;

	public JsonView() {
		super();
		
		setContentType(DEFAULT_JSON_CONTENT_TYPE);
	}

	public JsonView(JsonResponse response) {
		this();

		this.json = JsonUtils.toJsonNullable(response, false);
	}

	public JsonView(String response) {
		this();
		
		this.json = response;
	}

	public JsonView(Object response) {
		this();

		this.json = JsonUtils.toJsonNullable(response, false);
	}

	public JsonView setJson(String json) {
		this.json = json;
		
		return this;
	}

	@Override
	protected void renderMergedOutputModel(Map<String, Object> model, HttpServletRequest request, HttpServletResponse response) throws Exception {
		// no cache
		response.addHeader("Pragma", "no-cache");
		response.addHeader("Cache-Control", "no-cache, no-store, max-age=0");
		response.addDateHeader("Expires", 1L);
		
		String nullSafeJson = String.valueOf(json); // null --> "null"
		
		ByteArrayOutputStream baos = new ByteArrayOutputStream(StringUtils.length(nullSafeJson));
		FileCopyUtils.copy(nullSafeJson.getBytes("UTF8"), baos);
		
		writeToResponse(response, baos);
	}

}
