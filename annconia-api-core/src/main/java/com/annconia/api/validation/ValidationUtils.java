package com.annconia.api.validation;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import javax.validation.Validation;

import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.BindingResult;
import org.springframework.validation.DataBinder;
import org.springframework.validation.Errors;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.validation.Validator;
import org.springframework.validation.beanvalidation.SpringValidatorAdapter;

import com.annconia.util.CollectionUtils;
import com.annconia.util.StringUtils;

abstract public class ValidationUtils extends org.springframework.validation.ValidationUtils {

	private static final Validator[] EMPTY_VALIDATORS = new Validator[0];

	private static Validator validator;

	public static Validator getValidator() {
		if (validator == null) {
			validator = new SpringValidatorAdapter(Validation.byDefaultProvider().configure().buildValidatorFactory().getValidator());
		}

		return validator;
	}

	public static void setValidator(Validator validator) {
		ValidationUtils.validator = validator;
	}

	public static Errors getErrors() {
		return getErrors(new Object());
	}

	public static Errors getErrors(Object target) {
		return getErrors(target, DataBinder.DEFAULT_OBJECT_NAME, EMPTY_VALIDATORS);
	}

	public static Errors getErrors(Object target, String objectName) {
		return getErrors(target, objectName, EMPTY_VALIDATORS);
	}

	public static Errors getErrors(Object target, Validator... validators) {
		return getErrors(target, DataBinder.DEFAULT_OBJECT_NAME, validators);
	}

	public static Errors getErrors(Object target, String objectName, Validator... validators) {
		Errors errors = getBindingResult(target, objectName);

		getValidator().validate(target, errors);

		if (CollectionUtils.isNotEmpty(validators)) {
			for (Validator validator : validators) {
				if (validator.supports(target.getClass())) {
					validator.validate(target, errors);
				}
			}
		}

		return errors;
	}

	public static Errors getErrorsFromValidatorsOnly(Object target, Validator... validators) {
		return getErrorsFromValidatorsOnly(target, DataBinder.DEFAULT_OBJECT_NAME, EMPTY_VALIDATORS);
	}

	public static Errors getErrorsFromValidatorsOnly(Object target, String objectName, Validator... validators) {
		Errors errors = getBindingResult(target, objectName);

		if (CollectionUtils.isNotEmpty(validators)) {
			for (Validator validator : validators) {
				invokeValidator(validator, target, errors);
			}
		}

		return errors;
	}

	public static Errors getErrorsFromValidatorsOnly(Validator... validators) {
		return getErrorsFromValidatorsOnly(new Object(), validators);
	}

	public static Errors validate(Object target) throws ValidationException {
		return validate(target, EMPTY_VALIDATORS);
	}

	public static Errors validate(Object target, Validator... validators) throws ValidationException {
		return validate(target, DataBinder.DEFAULT_OBJECT_NAME, validators);
	}

	public static Errors validate(Object target, String objectName, Validator... validators) throws ValidationException {
		Errors errors = getErrors(target, objectName, validators);

		if (errors.hasErrors()) {
			throw new ValidationException(errors);
		}

		return errors;
	}

	public static Errors validateWithValidatorsOnly(Object target, Validator... validators) throws ValidationException {
		return validateWithValidatorsOnly(target, DataBinder.DEFAULT_OBJECT_NAME, validators);
	}

	public static Errors validateWithValidatorsOnly(Object target, String objectName, Validator... validators) throws ValidationException {
		Errors errors = getErrorsFromValidatorsOnly(target, objectName, validators);

		if (errors.hasErrors()) {
			throw new ValidationException(errors);
		}

		return errors;
	}

	public static Errors validateWithValidatorsOnly(Validator... validators) throws ValidationException {
		return validateWithValidatorsOnly(new Object(), validators);
	}

	public static <T> Errors validateEach(final Collection<T> collection, final String name, final Validator... validators) throws ValidationException {
		if (collection == null) {
			return null;
		}

		final boolean singular = CollectionUtils.isSingular(collection);

		return ValidationUtils.validateWithValidatorsOnly(new com.annconia.api.validation.Validator<Object>() {

			@Override
			public void validate(Object target, Errors errors, Class<?> type) {
				Iterator<T> iter = collection.iterator();
				int i = 0;

				while (iter.hasNext()) {
					String property = StringUtils.valueOf(name) + "[" + i + "]";

					T item = iter.next();

					Errors temp = ValidationUtils.getErrors(item, validators);

					if (singular) {
						errors.addAllErrors(temp);
					}

					for (ObjectError error : temp.getGlobalErrors()) {
						errors.rejectValue(property, error.getCode(), error.getDefaultMessage());
					}

					for (FieldError error : temp.getFieldErrors()) {
						errors.rejectValue(property + "." + error.getField(), error.getCode(), error.getDefaultMessage());
					}

					i += 1;
				}
			}

		});
	}

	public static Errors joinErrors(List<Errors> all) {
		Errors errors = getErrors(new Object());

		if (CollectionUtils.isNotEmpty(all)) {
			for (Errors each : all) {
				errors.addAllErrors(each);
			}
		}

		return errors;
	}

	public static Errors applyObjectName(Errors errors, String objectName) {
		errors.setNestedPath(StringUtils.trimToEmpty(objectName));

		return errors;
	}

	/*
	 * Temporary fix until we come up with a way around Validating
	 */
	public static Errors addCascadedErrors(Errors errors, Object object, String objectName) {
		errors.addAllErrors(ValidationUtils.applyObjectName(ValidationUtils.getErrors(object), objectName));

		return errors;
	}

	public static void validateNotNull(final Object object, final String defaultMessage) throws ValidationException {
		ValidationUtils.validateWithValidatorsOnly(new com.annconia.api.validation.Validator<Object>() {

			@Override
			public void validate(Object target, Errors errors, Class<?> type) {
				if (object == null) {
					errors.reject("NotNull", defaultMessage);
				}
			}

		});
	}

	protected static BindingResult getBindingResult(Object target, String objectName) {
		BindingResult result = new SafeBeanPropertyBindingResult(target, objectName, true, DataBinder.DEFAULT_AUTO_GROW_COLLECTION_LIMIT);

		return result;
	}

	protected static class SafeBeanPropertyBindingResult extends BeanPropertyBindingResult {

		private static final long serialVersionUID = 4499425312749461874L;

		public SafeBeanPropertyBindingResult(Object target, String objectName, boolean autoGrowNestedPaths, int autoGrowCollectionLimit) {
			super(target, objectName, autoGrowNestedPaths, autoGrowCollectionLimit);
		}

		public SafeBeanPropertyBindingResult(Object target, String objectName) {
			super(target, objectName);
		}

		@Override
		protected Object getActualFieldValue(String field) {
			try {
				return super.getActualFieldValue(field);
			} catch (Throwable ex) {
				return null;
			}
		}

	}

}
