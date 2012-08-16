package com.annconia.api.validation;

import javax.annotation.PostConstruct;

import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;


public class CustomLocalValidatorFactoryBean extends LocalValidatorFactoryBean {

	@PostConstruct
	public void init() {
		ValidationUtils.setValidator(this);
	}

}
