package com.annconia.api.validation;

import org.springframework.validation.Errors;

public class ValidationException extends RuntimeException {
	
	private static final long serialVersionUID = 5952211673778455611L;
	
	final private Errors errors;

	public ValidationException(Errors errors) {
		super();
		
		this.errors = errors;
	}

	public ValidationException(String message, Errors errors) {
		super(message);
		
		this.errors = errors;
	}

	public ValidationException(Throwable cause, Errors errors) {
		super(cause);
		
		this.errors = errors;
	}

	public ValidationException(String message, Throwable cause, Errors errors) {
		super(message, cause);
		
		this.errors = errors;
	}

	public Errors getErrors() {
		return errors;
	}
	
}
