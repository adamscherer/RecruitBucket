package com.roundarch.entity;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.validation.constraints.NotNull;

import com.annconia.api.entity.AbstractEntity;

public class RecruitEntity extends AbstractEntity {

	public static enum ActiveEndReason {
		RESUME,
		PHONE,
		IN_PERSON
	}

	@NotNull
	private String firstName;

	@NotNull
	private String lastName;

	private String email;
	private String mobileNumber;
	private String homeNumber;

	private Address currentAddress = new Address();
	private Address hometown;
	private String school;
	private String major;
	private double gpa;
	private double bucketScore;

	private Map<String, SchoolDetail> education = new HashMap<String, SchoolDetail>();
	private Map<String, WorkDetail> work = new HashMap<String, WorkDetail>();

	private String favoriteBook;
	private String favoriteMovie;
	private String favoriteBlog;
	private String favoriteWebsite;
	private String favoritePublication;
	private String favoriteTravelDestination;

	private String socialOrganization;
	private String foreignLanguage;
	private String musicalInstrument;

	private boolean active;
	private Date activeEndDate;
	private ActiveEndReason activeEndReason;

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

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getMobileNumber() {
		return mobileNumber;
	}

	public void setMobileNumber(String mobileNumber) {
		this.mobileNumber = mobileNumber;
	}

	public String getHomeNumber() {
		return homeNumber;
	}

	public void setHomeNumber(String homeNumber) {
		this.homeNumber = homeNumber;
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

	public Map<String, SchoolDetail> getEducation() {
		return education;
	}

	public void setEducation(Map<String, SchoolDetail> education) {
		this.education = education;
	}

	public Map<String, WorkDetail> getWork() {
		return work;
	}

	public void setWork(Map<String, WorkDetail> work) {
		this.work = work;
	}

	public String getFavoriteBook() {
		return favoriteBook;
	}

	public void setFavoriteBook(String favoriteBook) {
		this.favoriteBook = favoriteBook;
	}

	public String getFavoriteMovie() {
		return favoriteMovie;
	}

	public void setFavoriteMovie(String favoriteMovie) {
		this.favoriteMovie = favoriteMovie;
	}

	public String getFavoriteBlog() {
		return favoriteBlog;
	}

	public void setFavoriteBlog(String favoriteBlog) {
		this.favoriteBlog = favoriteBlog;
	}

	public String getFavoriteWebsite() {
		return favoriteWebsite;
	}

	public void setFavoriteWebsite(String favoriteWebsite) {
		this.favoriteWebsite = favoriteWebsite;
	}

	public String getFavoritePublication() {
		return favoritePublication;
	}

	public void setFavoritePublication(String favoritePublication) {
		this.favoritePublication = favoritePublication;
	}

	public String getFavoriteTravelDestination() {
		return favoriteTravelDestination;
	}

	public void setFavoriteTravelDestination(String favoriteTravelDestination) {
		this.favoriteTravelDestination = favoriteTravelDestination;
	}

	public String getSocialOrganization() {
		return socialOrganization;
	}

	public void setSocialOrganization(String socialOrganization) {
		this.socialOrganization = socialOrganization;
	}

	public String getForeignLanguage() {
		return foreignLanguage;
	}

	public void setForeignLanguage(String foreignLanguage) {
		this.foreignLanguage = foreignLanguage;
	}

	public String getMusicalInstrument() {
		return musicalInstrument;
	}

	public void setMusicalInstrument(String musicalInstrument) {
		this.musicalInstrument = musicalInstrument;
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

	@Override
	public void storePreviousValues() {
		addPreviousValue("school", getSchool());
		addPreviousValue("major", getMajor());
		addPreviousValue("stage", getStage().name());
	}

}
