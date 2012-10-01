package com.annconia.api.interceptor;

import com.annconia.api.ApiException;

public class RateLimitException extends ApiException {

	private static final long serialVersionUID = 1L;

	public RateLimitException(String msg) {
		super(msg);
	}

}
