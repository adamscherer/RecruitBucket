package com.roundarch.entity;

import com.annconia.api.entity.AbstractEntity;

public class QuestionEntity extends AbstractEntity {

	private String question;
	private QuestionCategory category;

	public String getQuestion() {
		return question;
	}

	public void setQuestion(String question) {
		this.question = question;
	}

	public QuestionCategory getCategory() {
		return category;
	}

	public void setCategory(QuestionCategory category) {
		this.category = category;
	}

}
