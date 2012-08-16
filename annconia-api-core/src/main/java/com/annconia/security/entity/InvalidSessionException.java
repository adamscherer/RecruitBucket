package com.annconia.security.entity;

public class InvalidSessionException extends RuntimeException {

	private static final long serialVersionUID = 1;

	private boolean expired;

	public InvalidSessionException(String msg, boolean expired) {
		super(msg);
		
		this.expired = expired;
	}

	public boolean isExpired() {
		return expired;
	}

}
