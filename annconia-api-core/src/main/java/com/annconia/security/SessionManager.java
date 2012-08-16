package com.annconia.security;

import com.annconia.security.core.context.SessionContext;
import com.annconia.security.entity.Session;

public interface SessionManager {

	/**
	 * Authenticate based on username/password.
	 */
	SessionContext authenticate(String username, String password);

	/**
	 * Authenticate based on existing Session token.
	 */
	SessionContext authenticate(String sessionKey);

	/**
	 * Authenticate based on existing Session.
	 */
	SessionContext authenticate(Session session);

	/**
	 * Refresh an existing session
	 * 
	 * @param session
	 */
	void refreshSession(Session session);

	/**
	 * Remove an existing session
	 * 
	 * @param sessionKey
	 */
	void destroySession(String sessionKey);

}
