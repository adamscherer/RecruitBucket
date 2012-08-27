package com.roundarch.entity;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.validation.constraints.NotNull;

import org.springframework.data.annotation.Transient;

import com.annconia.api.entity.AbstractEntity;
import com.annconia.api.entity.DeleteArchive;

public class RecruitEntity extends AbstractEntity implements DeleteArchive {

	@NotNull
	private String firstName;

	@NotNull
	private String lastName;

	private String email;
	private String mobileNumber;
	private String homeNumber;

	private Address currentAddress = new Address();
	private Address hometown;
	private double bucketScore;

	private String favoriteBook;
	private String favoriteMovie;
	private String favoriteBlog;
	private String favoriteWebsite;
	private String favoritePublication;
	private String favoriteTravelDestination;

	private String socialOrganization;
	private String foreignLanguage;
	private String musicalInstrument;

	@NotNull
	private RecruitStage stage = RecruitStage.NOT_STAGED;

	private Date stageDate;

	private List<String> foreignLanguages;

	private ArrayList<SchoolDetail> education = new ArrayList<SchoolDetail>();

	private ArrayList<WorkDetail> work = new ArrayList<WorkDetail>();

	@Transient
	private List<DocumentMetadataEntity> documents;

	@Transient
	private List<ReviewEntity> reviews;

	@Transient
	private List<ActivityEntity> activities;

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

	public ArrayList<SchoolDetail> getEducation() {
		return education;
	}

	public void setEducation(ArrayList<SchoolDetail> education) {
		this.education = education;
	}

	public ArrayList<WorkDetail> getWork() {
		return work;
	}

	public void setWork(ArrayList<WorkDetail> work) {
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

	public List<DocumentMetadataEntity> getDocuments() {
		return documents;
	}

	public void setDocuments(List<DocumentMetadataEntity> documents) {
		this.documents = documents;
	}

	public List<ReviewEntity> getReviews() {
		return reviews;
	}

	public void setReviews(List<ReviewEntity> reviews) {
		this.reviews = reviews;
	}

	public List<ActivityEntity> getActivities() {
		return activities;
	}

	public void setActivities(List<ActivityEntity> activities) {
		this.activities = activities;
	}

	@Override
	public void storePreviousValues() {
		addPreviousValue("education", getEducation());
		addPreviousValue("work", getWork());
		addPreviousValue("stage", getStage().name());
	}

}
