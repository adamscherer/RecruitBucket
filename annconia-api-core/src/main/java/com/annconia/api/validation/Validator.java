package com.annconia.api.validation;

import java.lang.reflect.ParameterizedType;

import org.springframework.validation.Errors;

import com.annconia.util.CollectionUtils;

abstract public class Validator<T> implements org.springframework.validation.Validator {
	
	final protected Class<T> type;
	
	public Validator() {
		super();
		
		this.type = findTypeClass();
	}
	
	protected Validator(Class<T> type) {
		super();
		
		this.type = type;
	}

	public boolean supports(Class<?> clazz) {
		return type.isAssignableFrom(clazz);
	}

	@SuppressWarnings("unchecked")
	final public void validate(Object target, Errors errors) {
		validate((T) target, errors, type);
	}
	
	abstract public void validate(T target, Errors errors, Class<?> type);
	
	@SuppressWarnings("unchecked")
	protected Class<T> findTypeClass() throws RuntimeException {
		ParameterizedType pType = (ParameterizedType) this.getClass().getGenericSuperclass();
		
		if (CollectionUtils.isEmpty(pType.getActualTypeArguments())) {
			throw new RuntimeException("JdbcFilterClass not found");
		}
		
		return (Class<T>) pType.getActualTypeArguments()[0];
	}

}
