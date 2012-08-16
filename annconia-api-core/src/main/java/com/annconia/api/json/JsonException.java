package com.annconia.api.json;

public class JsonException extends Exception {

	private static final long serialVersionUID = 8730439928054603190L;

	public JsonException() {
		super();
	}

	public JsonException(String message, Throwable caught) {
		super(message, caught);
	}

	public JsonException(String message) {
		super(message);
	}

	public JsonException(Throwable caught) {
		super(caught);
	}

}
