package com.annconia.api.json;

import java.util.HashMap;
import java.util.Map;

import org.springframework.validation.Errors;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;

import com.annconia.api.util.MessageResolverUtils;

/**
 * 
 * @author Adam Scherer
 *
 */
public class JsonErrorResponse implements JsonResponse {

	private String message;
	private Map<String, String> errors;

	public JsonErrorResponse(String messageCode) {
		this.message = MessageResolverUtils.resolveMessage(messageCode);
	}

	public JsonErrorResponse(String field, String messageCode) {
		this.errors = new HashMap<String, String>();
		this.errors.put(field, MessageResolverUtils.resolveMessage(messageCode));
	}

	public JsonErrorResponse(Errors errors) {
		this.errors = new HashMap<String, String>();

		for (ObjectError error : errors.getGlobalErrors()) {
			this.errors.put(getFieldName(error), MessageResolverUtils.resolveMessage(error));
		}

		for (FieldError error : errors.getFieldErrors()) {
			this.errors.put(getFieldName(error, error.getField()), MessageResolverUtils.resolveMessage(error));
		}
	}

	public String getMessage() {
		return message;
	}

	public Map<String, String> getErrors() {
		return errors;
	}

	protected String getFieldName(ObjectError error) {
		return getFieldName(error, null);
	}

	protected String getFieldName(ObjectError error, String field) {
		if (field != null) {
			return field;
		} else if (error instanceof FieldError) {
			return ((FieldError) error).getField();
		} else {
			return null;
		}
	}

}
