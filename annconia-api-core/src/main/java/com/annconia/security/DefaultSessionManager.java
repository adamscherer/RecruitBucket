package com.annconia.security;

import java.util.Date;

import org.springframework.security.core.token.TokenService;

import com.annconia.security.core.context.SessionContext;
import com.annconia.security.entity.InvalidSessionException;
import com.annconia.security.entity.SecurityUser;
import com.annconia.security.entity.Session;
import com.annconia.security.repository.SecurityUserRepository;
import com.annconia.security.repository.SessionRepository;
import com.annconia.util.DateUtils;

public class DefaultSessionManager implements SessionManager {

	SecurityUserRepository userRepository;

	SessionRepository sessionRepository;

	TokenService tokenService;

	public SessionContext authenticate(String username, String password) {
		SecurityUser user = userRepository.authenticate(username, password);

		Session session = createSession(user);

		return new SessionContext(session, user);
	}

	public SessionContext authenticate(String sessionKey) {
		if (sessionKey == null) {
			throw new InvalidSessionException("Session not found", false);
		}

		return authenticate(sessionRepository.findOne(sessionKey));
	}

	public SessionContext authenticate(Session session) {
		if (session == null) {
			throw new InvalidSessionException("Session not found", false);
		}

		if (session.getCreationTime() == null) {
			throw new InvalidSessionException("Session expired", true);
		}

		return new SessionContext(session, userRepository.findOne(session.getUserId()));
	}

	public void refreshSession(Session session) {
		Date now = new Date();
		session.setLastAccessTime(now);
		if (DateUtils.isBefore(now, session.getReset())) {
			session.setRemaining(session.getLimit());
			session.setReset(DateUtils.addMinutes(session.getReset(), 60));
		} else {
			session.setRemaining(session.getRemaining() - 1);
		}

		sessionRepository.save(session);
	}

	public void destroySession(String sessionKey) {
		sessionRepository.delete(sessionKey);
	}

	public SecurityUserRepository getUserRepository() {
		return userRepository;
	}

	public void setUserRepository(SecurityUserRepository userRepository) {
		this.userRepository = userRepository;
	}

	public SessionRepository getSessionRepository() {
		return sessionRepository;
	}

	public void setSessionRepository(SessionRepository sessionRepository) {
		this.sessionRepository = sessionRepository;
	}

	public TokenService getTokenService() {
		return tokenService;
	}

	public void setTokenService(TokenService tokenService) {
		this.tokenService = tokenService;
	}

	private Session createSession(SecurityUser user) {

		Session session = new Session(tokenService.allocateToken("ASDFASFD").getKey());
		session.setUserId(user.getId().toString());
		session.setCreationTime(new Date());
		session.setLastAccessTime(new Date());
		session.setLimit(250);
		session.setRemaining(250);
		session.setReset(DateUtils.addMinutes(new Date(), 60));

		sessionRepository.save(session);

		return session;
	}

}
