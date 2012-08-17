package com.roundarch.entity;


public class SchoolDetail {

	public enum SchoolType {

		HIGH_SCHOOL,
		COLLEGE,
		GRADUATE_SCHOOL

	}

	private String name;
	private int startYear;
	private int endYear;
	private String concentration1;
	private String concentration2;
	private String concentration3;
	private double gpa;
	private boolean graduated;
	private SchoolType type;

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

	public String getConcentration1() {
		return concentration1;
	}

	public void setConcentration1(String concentration1) {
		this.concentration1 = concentration1;
	}

	public String getConcentration2() {
		return concentration2;
	}

	public void setConcentration2(String concentration2) {
		this.concentration2 = concentration2;
	}

	public String getConcentration3() {
		return concentration3;
	}

	public void setConcentration3(String concentration3) {
		this.concentration3 = concentration3;
	}

	public double getGpa() {
		return gpa;
	}

	public void setGpa(double gpa) {
		this.gpa = gpa;
	}

	public boolean isGraduated() {
		return graduated;
	}

	public void setGraduated(boolean graduated) {
		this.graduated = graduated;
	}

	public SchoolType getType() {
		return type;
	}

	public void setType(SchoolType type) {
		this.type = type;
	}

}
