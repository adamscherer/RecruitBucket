package com.annconia.api.interceptor;

import org.springframework.core.NamedThreadLocal;

public abstract class PerformanceContextHolder {

	private static final ThreadLocal<PerformanceContext> contextHolder = new NamedThreadLocal<PerformanceContext>("Performance context");

	public static PerformanceContext get() {
		return contextHolder.get();
	}

	public static void set(PerformanceContext context) {
		contextHolder.set(context);
	}

	public static void reset() {
		contextHolder.remove();
	}
}
