package com.annconia.api.repository;

import org.springframework.context.ApplicationListener;
import org.springframework.data.mongodb.core.MongoOperations;

import com.annconia.api.entity.DeleteArchive;

public class BeforeDeleteListener implements ApplicationListener<BeforeDeleteEvent> {

	MongoOperations mongoOperations;

	@Override
	public void onApplicationEvent(BeforeDeleteEvent event) {

		if (event.getSource() instanceof DeleteArchive) {
			mongoOperations.save(event.getSource());
		}

	}

	public MongoOperations getMongoOperations() {
		return mongoOperations;
	}

	public void setMongoOperations(MongoOperations mongoOperations) {
		this.mongoOperations = mongoOperations;
	}

}
