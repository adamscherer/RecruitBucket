package com.roundarch.repository.impl;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.mapreduce.MapReduceOptions;
import org.springframework.data.mongodb.core.mapreduce.MapReduceResults;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;

import com.annconia.api.entity.AbstractEntity;
import com.annconia.api.repository.AfterSaveEvent;
import com.annconia.api.repository.BeforeSaveEvent;
import com.annconia.api.repository.RepositoryEvent;
import com.roundarch.entity.RecruitEntity;
import com.roundarch.entity.ReviewEntity;
import com.roundarch.entity.StatisticsEntity;
import com.roundarch.entity.StatisticsEntity.StatisticType;
import com.roundarch.entity.ValueObject;
import com.roundarch.repository.StatisticsRepositoryExtension;

@SuppressWarnings("restriction")
public class StatisticsRepositoryImpl implements StatisticsRepositoryExtension, ApplicationListener<RepositoryEvent> {

	@Autowired
	MongoOperations mongoOperations;

	public <T> MapReduceResults<T> runMapReduce(String collection, String mapper, String reducer, String outputCollection, Class<T> clazz) {
		return mongoOperations.mapReduce(collection, mapper, reducer, new MapReduceOptions().outputTypeReplace().outputCollection(outputCollection), clazz);
	}

	@PostConstruct
	public void generateStatistics() {
		for (StatisticType type : StatisticType.values()) {
			StatisticsEntity statistics = new StatisticsEntity();
			statistics.setType(type);
			mongoOperations.save(statistics);
		}
	}

	public void runRecruitStatistics() {
		runMapReduce("recruitEntity", "classpath:/javascript/school-mapper.js", "classpath:/javascript/count-reducer.js", "schoolStatistics", ValueObject.class);
		runMapReduce("recruitEntity", "classpath:/javascript/major-mapper.js", "classpath:/javascript/count-reducer.js", "majorStatistics", ValueObject.class);
	}

	public void runInterviewerStatistics() {
		runMapReduce("interviewerEntity", "classpath:/javascript/school-mapper.js", "classpath:/javascript/count-reducer.js", "interviewerStatistics", ValueObject.class);
	}

	public final void onApplicationEvent(RepositoryEvent event) {
		if ((event.getSource() instanceof RecruitEntity)) {
			RecruitEntity entity = (RecruitEntity) event.getSource();
			if (event instanceof BeforeSaveEvent) {
				onBeforeSave(entity);
			} else if (event instanceof AfterSaveEvent) {
				onAfterSave(entity);
			}

			return;
		}

		if ((event.getSource() instanceof ReviewEntity)) {
			ReviewEntity entity = (ReviewEntity) event.getSource();
			if (event instanceof AfterSaveEvent) {
				onAfterSave(entity);
			}

			return;
		}

	}

	/**
	 * Override this method if you are interested in {@literal beforeSave} events.
	 *
	 * @param entity
	 */
	protected void onBeforeSave(RecruitEntity entity) {
		entity.setBucketScore(4);
	}

	/**
	 * Override this method if you are interested in {@literal afterSave} events.
	 *
	 * @param entity
	 */
	protected void onAfterSave(RecruitEntity entity) {
		aggregateProperty(StatisticType.SCHOOLS, entity, "school", entity.getSchool());
		aggregateProperty(StatisticType.MAJORS, entity, "major", entity.getMajor());
		aggregateProperty(StatisticType.STAGES, entity, "stage", entity.getStage().name());
	}

	/**
	 * Override this method if you are interested in {@literal afterSave} events.
	 *
	 * @param entity
	 */
	protected void onAfterSave(ReviewEntity entity) {
		aggregateProperty(StatisticType.RESUMES_REVIEWS, entity, "interviewerId", entity.getInterviewerId());
		aggregateProperty(StatisticType.IN_PERSON_REVIEWS, entity, "interviewerId", entity.getInterviewerId());
	}

	protected void aggregateProperty(StatisticType type, AbstractEntity entity, String property, String newValue) {
		if (entity.isPreviousValueUpdated(property, newValue)) {
			Query query = new Query(Criteria.where("type").is(type));
			Update update = new Update();
			String totalKey = "values.%s";
			update.inc(String.format(totalKey, newValue), 1);

			String previousValue = entity.getPreviousValue(property);
			if (previousValue != null) {
				update.inc(String.format(totalKey, previousValue), -1);
			}

			mongoOperations.findAndModify(query, update, StatisticsEntity.class);
		}
	}
}
