package com.roundarch.entity;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.annconia.util.CollectionUtils;

public class TypeAheadResponse {

	private int total;
	private List<Object> values;

	public TypeAheadResponse(Map<String, ?> data) {
		this(data, 50);
	}

	public TypeAheadResponse(Map<String, ?> data, int size) {
		this.total = data.size();
		this.values = CollectionUtils.safeSubList(new ArrayList<Object>(data.values()), size);
	}

	public int getTotal() {
		return total;
	}

	public void setTotal(int total) {
		this.total = total;
	}

	public List<Object> getValues() {
		return values;
	}

	public void setValues(List<Object> values) {
		this.values = values;
	}

}
