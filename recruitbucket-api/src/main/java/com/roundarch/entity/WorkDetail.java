package com.roundarch.entity;

public class WorkDetail {

	public enum WorkType {

		INTERNSHIP,
		FULL_TIME
	}

	private String name;
	private int startYear;
	private int endYear;
	private String description;
	private WorkType type;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getStartYear() {
		return startYear;
	}

	public void setStartYear(int startYear) {
		this.startYear = startYear;
	}

	public int getEndYear() {
		return endYear;
	}

	public void setEndYear(int endYear) {
		this.endYear = endYear;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public WorkType getType() {
		return type;
	}

	public void setType(WorkType type) {
		this.type = type;
	}

}
