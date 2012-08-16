package com.annconia.api.repository;

import java.util.Date;

import org.apache.commons.lang.StringUtils;
import org.springframework.data.mongodb.core.mapping.event.AbstractMongoEventListener;
import org.springframework.stereotype.Component;

import com.annconia.api.entity.AbstractEntity;

@Component
public class BeforeSaveMongoMetadata extends AbstractMongoEventListener<AbstractEntity> {

	public void onBeforeConvert(AbstractEntity source) {

		if (StringUtils.isEmpty(source.getId())) {
			source.setCreationTime(new Date());
		} else {
			source.setUpdateTime(new Date());
		}

	}

}