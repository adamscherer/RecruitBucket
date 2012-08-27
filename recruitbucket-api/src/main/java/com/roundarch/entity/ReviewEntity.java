package com.roundarch.entity;

import java.util.List;

import javax.validation.constraints.NotNull;

import org.codehaus.jackson.map.annotate.JsonSerialize;

import com.annconia.api.entity.AbstractEntity;
import com.roundarch.repository.JsonIdSerializers.InterviewerSerializer;
import com.roundarch.repository.JsonIdSerializers.RecruitSerializer;

public class ReviewEntity extends AbstractEntity {

	public static enum ReviewType {
		RESUME,
		INTERVIEW
	}

	@NotNull
	@JsonSerialize(using = InterviewerSerializer.class)
	private String interviewerId;

	@NotNull
	@JsonSerialize(using = RecruitSerializer.class)
	private String recruitId;

	@NotNull
	private ReviewType type;

	private int overallScore;
	private String overallComments;

	private List<Response> responses;

	public String getInterviewerId() {
		return interviewerId;
	}

	public void setInterviewerId(String interviewerId) {
		this.interviewerId = interviewerId;
	}

	public String getRecruitId() {
		return recruitId;
	}

	public void setRecruitId(String recruitId) {
		this.recruitId = recruitId;
	}

	public ReviewType getType() {
		return type;
	}

	public void setType(ReviewType type) {
		this.type = type;
	}

	public int getOverallScore() {
		return overallScore;
	}

	public void setOverallScore(int overallScore) {
		this.overallScore = overallScore;
	}

	public String getOverallComments() {
		return overallComments;
	}

	public void setOverallComments(String overallComments) {
		this.overallComments = overallComments;
	}

	public List<Response> getResponses() {
		return responses;
	}

	public void setResponses(List<Response> responses) {
		this.responses = responses;
	}

	@Override
	public void storePreviousValues() {
		addPreviousValue("recruitId", getRecruitId());
		addPreviousValue("interviewerId", getInterviewerId());
	}

}
