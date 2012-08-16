package com.annconia.api.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.annconia.api.ApiException;
import com.annconia.api.util.MvcUtils;
import com.annconia.security.core.context.SessionContext;
import com.annconia.security.core.context.SessionContextHolder;

public class ApiInterceptor extends HandlerInterceptorAdapter {

	private final Logger logger = LoggerFactory.getLogger(getClass());

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
		logger.debug("preHandle(" + request.getRequestURI() + ", ..., " + handler.getClass() + ")");

		SessionContext context = SessionContextHolder.get();

		if (isAuthenticationRequired(handler) && (context == null || context.getUser() == null)) {
			response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);

			throw new ApiException("not.authenticated.error");
		}

		return true;
	}

	public void postHandle(HttpServletRequest httpservletrequest, HttpServletResponse httpservletresponse, Object object, ModelAndView modelandview) throws Exception {

	}

	protected boolean isRateLimited(Object handler) {
		return MvcUtils.findAnnotationOnMethodOrController(handler, RateLimit.class) != null;
	}

	protected boolean isAuthenticationRequired(Object handler) {
		return MvcUtils.findAnnotationOnMethodOrController(handler, RequireAuthentication.class) != null;
	}

}
