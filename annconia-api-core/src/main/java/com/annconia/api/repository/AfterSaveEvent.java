package com.annconia.api.repository;

@SuppressWarnings("serial")
public class AfterSaveEvent extends RepositoryEvent {
	public AfterSaveEvent(Object source) {
		super(source);
	}
}
