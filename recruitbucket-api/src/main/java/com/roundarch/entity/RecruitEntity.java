package com.roundarch.entity;

import java.util.List;

import javax.validation.constraints.NotNull;

import com.annconia.api.entity.AbstractEntity;

public class RecruitEntity extends AbstractEntity {

	@NotNull
	private String firstName;

	@NotNull
	private String lastName;

	private Address currentAddress;
	private Address hometown;
	private String school;
	private String major;
	private double gpa;
	private double bucketScore;

	@NotNull
	private RecruitStage stage = RecruitStage.IDENTIFICATION;

	private List<String> foreignLanguages;

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public Address getCurrentAddress() {
		return currentAddress;
	}

	public void setCurrentAddress(Address currentAddress) {
		this.currentAddress = currentAddress;
	}

	public Address getHometown() {
		return hometown;
	}

	public void setHometown(Address hometown) {
		this.hometown = hometown;
	}

	public double getGpa() {
		return gpa;
	}

	public void setGpa(double gpa) {
		this.gpa = gpa;
	}

	public String getMajor() {
		return major;
	}

	public void setMajor(String major) {
		this.major = major;
	}

	public String getSchool() {
		return school;
	}

	public void setSchool(String school) {
		this.school = school;
	}

	public double getBucketScore() {
		return bucketScore;
	}

	public void setBucketScore(double bucketScore) {
		this.bucketScore = bucketScore;
	}

	public List<String> getForeignLanguages() {
		return foreignLanguages;
	}

	public void setForeignLanguages(List<String> foreignLanguages) {
		this.foreignLanguages = foreignLanguages;
	}

	public RecruitStage getStage() {
		return stage;
	}

	public void setStage(RecruitStage stage) {
		if (stage != null) {
			this.stage = stage;
		}
	}

	@Override
	public void storePreviousValues() {
		addPreviousValue("school", getSchool());
		addPreviousValue("major", getMajor());
		addPreviousValue("stage", getStage().name());
	}

}
