package com.annconia.api.util;

import org.springframework.core.NamedThreadLocal;

public abstract class RequestContextHolder {

	private static final ThreadLocal<RequestContext> contextHolder = new NamedThreadLocal<RequestContext>("Request context");

	public static RequestContext get() {
		return contextHolder.get();
	}

	public static void set(RequestContext context) {
		contextHolder.set(context);
	}

	public static void reset() {
		contextHolder.remove();
	}
}
