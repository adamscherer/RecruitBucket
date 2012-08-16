package com.annconia.security.core.context;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.annconia.security.entity.SecurityUser;
import com.annconia.security.entity.Session;

public class SessionContext {

	protected static final Logger logger = LoggerFactory.getLogger(SessionContext.class);

	private SecurityUser user;
	private Session session;

	public SessionContext(Session session) {
		this.session = session;
	}

	public SessionContext(Session session, SecurityUser user) {
		this.session = session;
		this.user = user;
	}

	public SecurityUser getUser() {
		return user;
	}

	public void setUser(SecurityUser user) {
		this.user = user;
	}

	public Session getSession() {
		return session;
	}

	public void setSession(Session session) {
		this.session = session;
	}

}
