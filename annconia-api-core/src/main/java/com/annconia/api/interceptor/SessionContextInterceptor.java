package com.annconia.api.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.annconia.api.util.RequestContextHolder;
import com.annconia.security.SessionManager;
import com.annconia.security.core.context.SessionContext;
import com.annconia.security.core.context.SessionContextHolder;
import com.annconia.security.entity.InvalidSessionException;
import com.annconia.security.entity.Session;
import com.annconia.util.StringUtils;

public class SessionContextInterceptor extends HandlerInterceptorAdapter {

	@Autowired
	SessionManager sessionManager;

	private String sessionName = "SID";

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

		String sessionId = getSessionId();

		try {
			SessionContext sessionContext = sessionManager.authenticate(getSessionId());
			SessionContextHolder.set(sessionContext);
			Session session = sessionContext.getSession();
			if (session != null) {
				sessionManager.refreshSession(session);
				RequestContextHolder.get().addSessionCookie(sessionName, session.getId());
			}
		} catch (InvalidSessionException ex) {
			if (StringUtils.isNotEmpty(sessionId) && removeSessionCookie((HandlerMethod) handler)) {
				RequestContextHolder.get().removeCookie(sessionName);
			}
		} catch (Throwable ex) {

		}

		return true;
	}

	public void postHandle(HttpServletRequest httpservletrequest, HttpServletResponse httpservletresponse, Object object, ModelAndView modelandview) throws Exception {
		SessionContextHolder.reset();
	}

	private String getSessionId() {
		String sessionKey = RequestContextHolder.get().getParameter(sessionName);
		if (StringUtils.isNotEmpty(sessionKey)) {
			return sessionKey;
		}

		sessionKey = RequestContextHolder.get().getHeader(sessionName);
		if (StringUtils.isNotEmpty(sessionKey)) {
			return sessionKey;
		}

		return RequestContextHolder.get().getCookieValue(sessionName);

	}

	private boolean removeSessionCookie(HandlerMethod handler) {
		if (StringUtils.isNotEquivalent("authenticate", handler.getMethod().getName())) {
			return true;
		}

		if (handler.getMethodParameters().length == 0) {
			return true;
		}

		return false;
	}

	public String getSessionName() {
		return sessionName;
	}

	public void setSessionName(String sessionName) {
		this.sessionName = sessionName;
	}

}
