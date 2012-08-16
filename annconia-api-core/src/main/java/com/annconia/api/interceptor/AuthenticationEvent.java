package com.annconia.api.interceptor;

import org.springframework.context.ApplicationEvent;

public class AuthenticationEvent extends ApplicationEvent {

	private static final long serialVersionUID = 1L;

	public AuthenticationEvent(Object source) {
		super(source);
	}
}
