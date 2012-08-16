package com.roundarch.entity;

import com.annconia.api.entity.AbstractEntity;

public class Signal extends AbstractEntity {

	private double weight;
	private String name;

	public Signal() {

	}

	public Signal(String name) {
		this.name = name;
	}

	public double getWeight() {
		return weight;
	}

	public void setWeight(double weight) {
		this.weight = weight;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

}
