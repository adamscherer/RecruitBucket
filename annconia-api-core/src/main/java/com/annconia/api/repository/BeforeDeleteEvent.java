package com.annconia.api.repository;

@SuppressWarnings("serial")
public class BeforeDeleteEvent extends RepositoryEvent {

	public BeforeDeleteEvent(Object source) {
		super(source);
	}

}
