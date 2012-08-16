package com.annconia.api.validation;

import javax.validation.ConstraintValidatorContext;

import com.annconia.util.StringUtils;

public class ConditionalIsEmptyValidator extends ConditionalValidator<ConditionalIsEmpty> {

	public void initialize(ConditionalIsEmpty parameters) {
	}

	public boolean isValid(Object object, ConstraintValidatorContext constraintValidatorContext) {

		if (!isParameterSet(constraintValidatorContext)) {
			return true;
		}

		return StringUtils.isNotEmpty((String)object);
	}
}
