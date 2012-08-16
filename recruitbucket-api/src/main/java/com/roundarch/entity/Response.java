package com.roundarch.entity;

import com.annconia.api.entity.AbstractEntity;

public class Response extends AbstractEntity {

	private String question;
	private int score;
	private String comments;

	public String getQuestion() {
		return question;
	}

	public void setQuestion(String question) {
		this.question = question;
	}

	public int getScore() {
		return score;
	}

	public void setScore(int score) {
		this.score = score;
	}

	public String getComments() {
		return comments;
	}

	public void setComments(String comments) {
		this.comments = comments;
	}

}
