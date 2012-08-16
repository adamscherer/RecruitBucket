package com.annconia.api.validation;

import java.util.Calendar;
import java.util.Date;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.apache.commons.lang.time.DateUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MinimumAgeValidator implements ConstraintValidator<MinimumAge, Object> {

	protected final Logger logger = LoggerFactory.getLogger(getClass());

	private int minimumAge;

	@Override
	public void initialize(MinimumAge contraintAnnotation) {
		this.minimumAge = contraintAnnotation.age();
	}

	@Override
	public boolean isValid(Object value, ConstraintValidatorContext context) {
		if (value == null)
			return false;

		if (value instanceof Date) {
			return validateDate((Date) value, context);
		} else if (value instanceof Calendar) {
			return validateCalendar((Calendar) value, context);
		}

		throw new IllegalArgumentException("Value must be instance of java.util.Date or java.util.Calendar'. Got type of " + value.getClass().getName());
	}

	protected boolean validateDate(Date d, ConstraintValidatorContext context) {
		Calendar c = Calendar.getInstance();
		c.setTime(d);

		return validateCalendar(c, context);
	}

	protected boolean validateCalendar(Calendar dateOfBirth, ConstraintValidatorContext context) {
		Calendar cutoff = Calendar.getInstance();
		cutoff.add(Calendar.YEAR, -minimumAge);
		cutoff = DateUtils.truncate(cutoff, Calendar.DATE);
		cutoff.add(Calendar.SECOND, 1);

		dateOfBirth = DateUtils.truncate(dateOfBirth, Calendar.DATE);

		return dateOfBirth.before(cutoff);
	}
}
