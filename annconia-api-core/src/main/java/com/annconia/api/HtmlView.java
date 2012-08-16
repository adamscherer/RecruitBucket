package com.annconia.api;

import java.io.ByteArrayOutputStream;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.servlet.view.AbstractView;

public class HtmlView extends AbstractView {

	String html;

	public HtmlView() {
		super();
	}

	public HtmlView(String html) {
		this();

		this.html = html;
	}

	public HtmlView setHtml(String html) {
		this.html = html;

		return this;
	}

	public HtmlView setRawJavascript(String javascript) {
		this.html = "<html><body><script type=\"text/javascript\">" + javascript + "</script></body></html>";

		return this;
	}

	@Override
	protected void renderMergedOutputModel(Map<String, Object> model, HttpServletRequest request, HttpServletResponse response) throws Exception {

		ByteArrayOutputStream out = new ByteArrayOutputStream(StringUtils.length(html));
		FileCopyUtils.copy(html.getBytes("UTF8"), out);

		writeToResponse(response, out);
	}

}
