package com.annconia.security.core.context;

import org.springframework.core.NamedThreadLocal;

public abstract class SessionContextHolder {

	public static final String sessionName = "SID";
	
	private static final ThreadLocal<SessionContext> contextHolder = new NamedThreadLocal<SessionContext>("Session context");

	public static SessionContext get() {
		return contextHolder.get();
	}

	public static void set(SessionContext context) {
		contextHolder.set(context);
	}

	public static void reset() {
		contextHolder.remove();
	}
}
