package com.annconia.api.repository;

import org.springframework.context.ApplicationEvent;

public abstract class RepositoryEvent extends ApplicationEvent {

	private static final long serialVersionUID = 1L;

	protected RepositoryEvent(Object source) {
		super(source);
	}
}
