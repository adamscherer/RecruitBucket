package com.roundarch.entity;

import org.codehaus.jackson.map.annotate.JsonSerialize;

import com.annconia.api.entity.AbstractEntity;
import com.roundarch.repository.JsonIdSerializers.InterviewerSerializer;
import com.roundarch.repository.JsonIdSerializers.RecruitSerializer;

public class ActivityEntity extends AbstractEntity {

	@JsonSerialize(using = RecruitSerializer.class)
	private String recruitId;

	@JsonSerialize(using = InterviewerSerializer.class)
	private String interviewerId;

	private ActivityType activityType;

	public String getRecruitId() {
		return recruitId;
	}

	public void setRecruitId(String recruitId) {
		this.recruitId = recruitId;
	}

	public String getInterviewerId() {
		return interviewerId;
	}

	public void setInterviewerId(String interviewerId) {
		this.interviewerId = interviewerId;
	}

	public ActivityType getActivityType() {
		return activityType;
	}

	public void setActivityType(ActivityType activityType) {
		this.activityType = activityType;
	}

}
