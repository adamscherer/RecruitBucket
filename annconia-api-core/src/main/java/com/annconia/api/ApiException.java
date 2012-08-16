package com.annconia.api;

public class ApiException extends RuntimeException {

	private static final long serialVersionUID = 1;

	public ApiException(String msg) {
		super(msg);
	}

	public String getMessageCode() {
		return getMessage();
	}

}
