package com.roundarch.entity;

import java.util.HashMap;
import java.util.Map;

import org.springframework.data.mongodb.core.index.Indexed;

import com.annconia.api.entity.AbstractEntity;

public class StatisticsEntity extends AbstractEntity {

	public static enum StatisticType {
		MAJORS,
		SCHOOLS,
		STAGES,
		RESUMES_REVIEWS,
		IN_PERSON_REVIEWS
	}

	@Indexed(unique = true)
	private StatisticType type;

	private Map<String, Integer> values = new HashMap<String, Integer>();

	public StatisticType getType() {
		return type;
	}

	public void setType(StatisticType type) {
		this.type = type;
	}

	public Map<String, Integer> getValues() {
		return values;
	}

	public void setValues(Map<String, Integer> values) {
		if (values != null) {
			this.values = values;
		}
	}

}
