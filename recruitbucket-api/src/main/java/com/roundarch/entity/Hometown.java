package com.roundarch.entity;

public class Hometown {

	private String id;
	private String city;
	private String state;
	private int pop;
	private GeoLocation log;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public int getPop() {
		return pop;
	}

	public void setPop(int pop) {
		this.pop = pop;
	}

	public GeoLocation getLog() {
		return log;
	}

	public void setLog(GeoLocation log) {
		this.log = log;
	}

}
