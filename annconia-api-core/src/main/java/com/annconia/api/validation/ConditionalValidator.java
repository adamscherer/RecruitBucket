package com.annconia.api.validation;

import java.lang.annotation.Annotation;
import java.util.List;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.hibernate.validator.internal.engine.ConstraintValidatorContextImpl;
import org.hibernate.validator.internal.engine.MessageAndPath;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.annconia.api.util.RequestContextHolder;

public abstract class ConditionalValidator<A extends Annotation> implements ConstraintValidator<A, Object> {

	protected final Logger logger = LoggerFactory.getLogger(getClass());

	protected boolean isParameterSet(ConstraintValidatorContext constraintValidatorContext) {
		if (constraintValidatorContext instanceof ConstraintValidatorContextImpl) {
			ConstraintValidatorContextImpl context = (ConstraintValidatorContextImpl) constraintValidatorContext;
			return RequestContextHolder.get().hasParameterName(getPath(context.getMessageAndPathList()));
		}

		return true;
	}

	private String getPath(List<MessageAndPath> paths) {
		try {
			return paths.get(0).getPath().toString();
		} catch (Throwable ex) {
			return null;
		}
	}

}
