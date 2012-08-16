package com.annconia.api.repository;

@SuppressWarnings("serial")
public class BeforeSaveEvent extends RepositoryEvent {
	public BeforeSaveEvent(Object source) {
		super(source);
	}
}
