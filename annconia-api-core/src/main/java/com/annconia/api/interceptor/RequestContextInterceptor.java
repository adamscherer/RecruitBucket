package com.annconia.api.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.annconia.api.util.RequestContext;
import com.annconia.api.util.RequestContextHolder;

public class RequestContextInterceptor extends HandlerInterceptorAdapter {

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

		LocaleContextHolder.setLocale(request.getLocale());
		RequestContextHolder.set(new RequestContext(request, response, null, null));

		return true;
	}

	public void afterCompletion(HttpServletRequest httpservletrequest, HttpServletResponse httpservletresponse, Object obj, Exception exception) throws Exception {

		LocaleContextHolder.resetLocaleContext();
		RequestContextHolder.reset();
	}

}
