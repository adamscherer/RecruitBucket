package com.annconia.api.repository;

@SuppressWarnings("serial")
public class AfterDeleteEvent extends RepositoryEvent {
	public AfterDeleteEvent(Object source) {
		super(source);
	}
}
