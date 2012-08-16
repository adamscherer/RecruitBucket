package com.annconia.api.repository;

import org.springframework.data.mongodb.core.mapping.event.AbstractMongoEventListener;
import org.springframework.stereotype.Component;

import com.annconia.api.entity.AbstractEntity;
import com.annconia.api.validation.ValidationUtils;
import com.mongodb.DBObject;

@Component
public class BeforeSaveMongoValidator extends AbstractMongoEventListener<AbstractEntity> {

	public void onBeforeSave(AbstractEntity source, DBObject dbo) {
		ValidationUtils.validate(source);
	}

}