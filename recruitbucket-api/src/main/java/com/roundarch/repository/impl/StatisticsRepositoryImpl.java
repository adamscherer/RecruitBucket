package com.roundarch.repository.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.apache.commons.lang.StringUtils;
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
import com.annconia.util.CollectionUtils;
import com.roundarch.entity.Named;
import com.roundarch.entity.RecruitEntity;
import com.roundarch.entity.ReviewEntity;
import com.roundarch.entity.SchoolDetail;
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

		List<RecruitEntity> recruits = mongoOperations.findAll(RecruitEntity.class);

		StatisticsEntity statistics = getStatistic(StatisticType.SCHOOLS);
		statistics.setValues(getCurrentSchools(recruits));
		mongoOperations.save(statistics);

		StatisticsEntity majors = getStatistic(StatisticType.MAJORS);
		majors.setValues(getCurrentMajors(recruits));
		mongoOperations.save(majors);

		StatisticsEntity stages = getStatistic(StatisticType.STAGES);
		stages.setValues(getCurrentStages(recruits));
		mongoOperations.save(stages);

		StatisticsEntity resumeReviews = getStatistic(StatisticType.RESUMES_REVIEWS);
		//resumeReviews.setValues(getCurrentStages(recruits));
		mongoOperations.save(resumeReviews);

		StatisticsEntity interviewReviews = getStatistic(StatisticType.IN_PERSON_REVIEWS);
		interviewReviews.setValues(getCurrentStages(recruits));
		mongoOperations.save(interviewReviews);
	}

	public StatisticsEntity getStatistic(StatisticType type) {
		Query query = new Query(Criteria.where("type").is(type));
		StatisticsEntity statistic = mongoOperations.findOne(query, StatisticsEntity.class);
		if (statistic == null) {
			statistic = new StatisticsEntity();
			statistic.setType(type);
		}

		return statistic;
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
		aggregateEducation(entity);

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

			String previousValue = entity.getPreviousValueAsString(property);
			if (previousValue != null) {
				update.inc(String.format(totalKey, previousValue), -1);
			}

			mongoOperations.findAndModify(query, update, StatisticsEntity.class);
		}
	}

	@SuppressWarnings("unchecked")
	protected void aggregateEducation(RecruitEntity entity) {
		List<SchoolDetail> previousSchools = (List<SchoolDetail>) entity.getPreviousValue("education");

		aggregateSchoolNames(entity.getEducation(), previousSchools);
		aggregateMajors(entity.getEducation(), previousSchools);
	}

	protected void aggregateSchoolNames(List<SchoolDetail> currentValues, List<SchoolDetail> previousValues) {
		List<String> previousSchools = getNamedValues(previousValues);
		List<String> currentSchools = getNamedValues(currentValues);
		if (CollectionUtils.doesNotContainAll(previousSchools, currentSchools)) {
			for (String value : previousSchools) {
				decrement(StatisticType.SCHOOLS, value);
			}

			for (String value : currentSchools) {
				increment(StatisticType.SCHOOLS, value);
			}
		}
	}

	protected void aggregateMajors(List<SchoolDetail> currentValues, List<SchoolDetail> previousValues) {
		List<String> previousSchools = getMajorsValues(previousValues);
		List<String> currentSchools = getMajorsValues(currentValues);
		if (CollectionUtils.doesNotContainAll(previousSchools, currentSchools)) {
			for (String value : previousSchools) {
				decrement(StatisticType.MAJORS, value);
			}

			for (String value : currentSchools) {
				increment(StatisticType.MAJORS, value);
			}
		}
	}

	protected void increment(StatisticType type, String value) {
		Query query = new Query(Criteria.where("type").is(type));
		Update update = new Update();
		String totalKey = "values.%s";
		update.inc(String.format(totalKey, value), 1);
		mongoOperations.findAndModify(query, update, StatisticsEntity.class);
	}

	protected void decrement(StatisticType type, String value) {
		Query query = new Query(Criteria.where("type").is(type));
		Update update = new Update();
		String totalKey = "values.%s";
		update.inc(String.format(totalKey, value), -1);
		mongoOperations.findAndModify(query, update, StatisticsEntity.class);
	}

	public List<String> getMajorsValues(List<SchoolDetail> values) {
		List<String> results = new ArrayList<String>();

		if (values == null) {
			return results;
		}

		for (SchoolDetail value : values) {
			if (StringUtils.isNotEmpty(value.getConcentration1())) {
				results.add(value.getConcentration1());
			}

			if (StringUtils.isNotEmpty(value.getConcentration2())) {
				results.add(value.getConcentration2());
			}

			if (StringUtils.isNotEmpty(value.getConcentration3())) {
				results.add(value.getConcentration3());
			}
		}

		return results;
	}

	public List<String> getNamedValues(List<? extends Named> values) {
		List<String> results = new ArrayList<String>();

		if (values == null) {
			return results;
		}

		for (Named value : values) {
			results.add(value.getName());
		}

		return results;
	}

	public Map<String, Integer> getCurrentSchools(List<RecruitEntity> recruits) {
		Map<String, Integer> values = new HashMap<String, Integer>();

		for (RecruitEntity value : recruits) {
			for (SchoolDetail school : value.getEducation()) {
				incrementTotal(values, school.getName());
			}
		}

		return values;
	}

	public Map<String, Integer> getCurrentMajors(List<RecruitEntity> recruits) {
		Map<String, Integer> values = new HashMap<String, Integer>();

		for (RecruitEntity value : recruits) {
			for (SchoolDetail school : value.getEducation()) {
				if (StringUtils.isNotEmpty(school.getConcentration1())) {
					incrementTotal(values, school.getConcentration1());
				}

				if (StringUtils.isNotEmpty(school.getConcentration2())) {
					incrementTotal(values, school.getConcentration2());
				}

				if (StringUtils.isNotEmpty(school.getConcentration3())) {
					incrementTotal(values, school.getConcentration3());
				}
			}

		}

		return values;
	}

	public Map<String, Integer> getCurrentStages(List<RecruitEntity> recruits) {
		Map<String, Integer> values = new HashMap<String, Integer>();

		for (RecruitEntity value : recruits) {
			if (value.getStage() != null) {
				incrementTotal(values, value.getStage().name());
			}
		}

		return values;
	}

	protected Map<String, Integer> incrementTotal(Map<String, Integer> map, String key) {
		Integer current = map.get(key);
		if (current == null) {
			current = 0;
		}

		map.put(key, current + 1);

		return map;
	}
}
