package com.annconia.api.validation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import javax.validation.constraints.NotNull;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class NotAllDigitsValidator implements ConstraintValidator<NotAllDigits, Object> {

	protected final Logger logger = LoggerFactory.getLogger(getClass());

	@Override
	public void initialize(NotAllDigits contraintAnnotation) {

	}
	
	@NotNull
	@Override
	public boolean isValid(Object value, ConstraintValidatorContext context) {
		if (value == null)
			return false;

		if (value instanceof String) {
			return !StringUtils.isNumeric((String)value);
		}
		
		return false;
	}

}
