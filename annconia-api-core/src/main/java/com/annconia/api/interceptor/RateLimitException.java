package com.annconia.api.interceptor;

public class RateLimitException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public RateLimitException() {
		super();
	}

	public RateLimitException(String msg) {
		super(msg);
	}

}
