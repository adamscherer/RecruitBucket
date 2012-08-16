package com.roundarch.entity;

public class ValueObject {

	private String id;
	private float count;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public float getCount() {
		return count;
	}

	public void setCount(float count) {
		this.count = count;
	}

	@Override
	public String toString() {
		return "ValueObject [id=" + id + ", count=" + count + "]";
	}
}