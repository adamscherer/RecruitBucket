package com.annconia.api.util;

import java.util.Arrays;
import java.util.Collections;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.annconia.util.StringUtils;

public class RequestContext extends org.springframework.web.servlet.support.RequestContext {

	protected static final Logger logger = LoggerFactory.getLogger(RequestContext.class);

	HttpServletResponse response;

	public RequestContext(HttpServletRequest request) {
		super(request);
	}

	public RequestContext(HttpServletRequest request, ServletContext servletContext) {
		super(request, servletContext);
	}

	public RequestContext(HttpServletRequest request, HttpServletResponse response, ServletContext servletContext, Map<String, Object> model) {
		super(request, response, servletContext, model);

		this.response = response;
	}

	public String getHeader(String headerName) {
		return getRequest().getHeader(headerName);
	}

	@SuppressWarnings("unchecked")
	public boolean hasParameterName(String param) {
		for (Enumeration<String> enumeration = getRequest().getParameterNames(); enumeration.hasMoreElements();) {
			if (StringUtils.isEquivalent(enumeration.nextElement(), param)) {
				return true;
			}
		}
		return false;
	}

	public boolean hasParameterValue(String param) {
		return StringUtils.isNotEmpty(getRequest().getParameter(param));
	}

	public String getParameter(String param) {
		return getRequest().getParameter(param);
	}

	public boolean getBooleanParameter(String name) {
		return getBooleanParameter(name, false);
	}

	public boolean getBooleanParameter(String param, boolean def) {
		String value = getParameter(param);
		if (value == null)
			return def;
		else
			return StringUtils.parseBoolean(value, def);
	}

	public boolean hasAttribute(String key) {
		return getAttribute(key) != null;
	}

	public Object getAttribute(String param) {
		return getRequest().getAttribute(param);
	}

	public void removeAttribute(String param) {
		getRequest().removeAttribute(param);
	}

	public void setAttribute(String param, Object e) {
		getRequest().setAttribute(param, e);
	}

	public void setAttribute(String param, int i) {
		Integer value = new Integer(i);
		setAttribute(param, value);
	}

	public List<Cookie> getCookies() {
		Cookie cookies[] = getRequest().getCookies();
		if (cookies != null)
			return Arrays.asList(cookies);
		else
			return Collections.emptyList();
	}

	@SuppressWarnings("rawtypes")
	public Cookie getCookie(String name) {
		for (Iterator i = getCookies().iterator(); i.hasNext();) {
			Cookie c = (Cookie) i.next();
			if (StringUtils.isEquivalent(c.getName(), name))
				return c;
		}

		return null;
	}

	@SuppressWarnings("rawtypes")
	public String getCookieValue(String name, String def) {
		for (Iterator i = getCookies().iterator(); i.hasNext();) {
			Cookie c = (Cookie) i.next();
			if (c.getName().equals(name))
				return c.getValue();
		}

		return def;
	}

	public String getCookieValue(String name) {
		return getCookieValue(name, null);
	}

	public int getIntegerCookieValue(String name, int def) {
		return StringUtils.parseInteger(getCookieValue(name, null), def);
	}

	public boolean getBooleanCookieValue(String name, boolean def) {
		return StringUtils.parseBoolean(getCookieValue(name, null), def);
	}

	public boolean hasCookie(String name) {
		return getCookie(name) != null;
	}

	public void removeCookie(String cookieName) {
		removeCookie(cookieName, "/", null);
		String domain = getRequest().getServerName();
		int index = StringUtils.indexOf(domain, '.');
		while (index > 0) {
			String subdomain = domain.substring(index, domain.length());
			if (StringUtils.indexOf(subdomain, '.', 1) > 0)
				removeCookie(cookieName, "/", subdomain);
			domain = subdomain;
			index = StringUtils.indexOf(domain, '.', 1);
		}
	}

	public void removeCookie(Cookie c) {
		if (c == null) {
			return;
		} else {
			removeCookie(c.getName());
			removeCookie(c.getName(), c.getPath(), c.getDomain(), Boolean.valueOf(c.getSecure()));
			return;
		}
	}

	public void removeCookie(String name, String path, String domain) {
		removeCookie(name, path, domain, null);
		removeCookie(name, path, domain, Boolean.valueOf(false));
		removeCookie(name, path, domain, Boolean.valueOf(true));
	}

	public void removeCookie(String name, String path, String domain, Boolean secure) {
		Cookie c = new Cookie(name, null);
		c.setMaxAge(0);
		if (path != null)
			c.setPath(path);
		if (domain != null)
			c.setDomain(domain);
		if (secure != null)
			c.setSecure(secure.booleanValue());
		addCookie(c);
	}

	@SuppressWarnings("rawtypes")
	public void removeAllCookies() {
		Iterator i$ = getCookies().iterator();
		do {
			if (!i$.hasNext())
				break;
			Cookie c = (Cookie) i$.next();
			removeCookie(c);
		} while (true);
	}

	protected void addCookie(Cookie c) {
		if (response != null) {
			response.addCookie(c);
		}
	}

	public void addCookie(String name, String value) {
		int oneYear = 31536000;
		addCookie(name, value, oneYear);
	}

	public void addCookie(String name, long value) {
		int oneYear = 31536000;
		addCookie(name, Long.toString(value), oneYear);
	}

	public void addCookie(String name, boolean value) {
		int oneYear = 31536000;
		addCookie(name, Boolean.toString(value), oneYear);
	}

	public void addSessionCookie(String name, String value) {
		addCookie(name, value, -1);
	}

	public void addCookie(String name, String value, int age) {
		Cookie c = new Cookie(name, value);
		c.setMaxAge(age);
		c.setPath("/");
		addCookie(c);
	}

}
