package com.roundarch.repository.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.mapreduce.MapReduceOptions;
import org.springframework.data.mongodb.core.mapreduce.MapReduceResults;

import com.annconia.api.repository.AfterSaveEvent;
import com.annconia.api.repository.BeforeSaveEvent;
import com.annconia.api.repository.RepositoryEvent;
import com.roundarch.entity.InterviewerEntity;
import com.roundarch.entity.ValueObject;
import com.roundarch.repository.InterviewerRepositoryExtension;

public class InterviewerRepositoryImpl implements InterviewerRepositoryExtension, ApplicationListener<RepositoryEvent> {

	@Autowired
	MongoOperations mongoOperations;

	public <T> MapReduceResults<T> runMapReduce(String collection, String mapper, String reducer, String outputCollection, Class<T> clazz) {
		return mongoOperations.mapReduce(collection, mapper, reducer, new MapReduceOptions().outputTypeReplace().outputCollection(outputCollection), clazz);
	}

	public void runInterviewerStatistics() {
		runMapReduce("interviewerEntity", "classpath:/javascript/school-mapper.js", "classpath:/javascript/count-reducer.js", "interviewerStatistics", ValueObject.class);
	}

	public final void onApplicationEvent(RepositoryEvent event) {
		if (!(event.getSource() instanceof InterviewerEntity)) {
			return;
		}

		InterviewerEntity entity = (InterviewerEntity) event.getSource();
		if (event instanceof BeforeSaveEvent) {
			onBeforeSave(entity);
		} else if (event instanceof AfterSaveEvent) {
			onAfterSave(entity);
		}
	}

	/**
	 * Override this method if you are interested in {@literal beforeSave} events.
	 *
	 * @param entity
	 */
	protected void onBeforeSave(InterviewerEntity entity) {
		System.out.println("RepositoryEvent Before Save: " + entity);
	}

	/**
	 * Override this method if you are interested in {@literal afterSave} events.
	 *
	 * @param entity
	 */
	protected void onAfterSave(InterviewerEntity entity) {
		System.out.println("RepositoryEvent After Save: " + entity);
	}
}
