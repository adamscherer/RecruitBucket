package com.roundarch.entity;

import java.util.Date;
import java.util.List;

import javax.validation.constraints.NotNull;

import com.annconia.api.entity.AbstractEntity;

public class ReviewEntity extends AbstractEntity {

	public static enum ReviewType {
		RESUME,
		PHONE,
		IN_PERSON
	}

	public static enum ActiveEndReason {
		RESUME,
		PHONE,
		IN_PERSON
	}

	@NotNull
	private String interviewerId;

	@NotNull
	private String recruitId;

	@NotNull
	private ReviewType type;

	private int overallScore;
	private String overallComments;
	private boolean active;
	private Date activeEndDate;
	private ActiveEndReason activeEndReason;

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

	public boolean isActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
	}

	public Date getActiveEndDate() {
		return activeEndDate;
	}

	public void setActiveEndDate(Date activeEndDate) {
		this.activeEndDate = activeEndDate;
	}

	public ActiveEndReason getActiveEndReason() {
		return activeEndReason;
	}

	public void setActiveEndReason(ActiveEndReason activeEndReason) {
		this.activeEndReason = activeEndReason;
	}

	public List<Response> getResponses() {
		return responses;
	}

	public void setResponses(List<Response> responses) {
		this.responses = responses;
	}

}
