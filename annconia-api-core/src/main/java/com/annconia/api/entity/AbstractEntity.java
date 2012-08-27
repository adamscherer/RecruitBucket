package com.annconia.api.entity;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.codehaus.jackson.annotate.JsonIgnore;
import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.Document;

import com.annconia.api.json.JsonDateSerializers.SmtpDateTimeSerializer;
import com.annconia.api.json.JsonResponse;
import com.annconia.util.StringUtils;

@Document
public class AbstractEntity implements JsonResponse {

	@Id
	private String id;

	@JsonSerialize(using = SmtpDateTimeSerializer.class)
	private Date creationTime;

	@JsonSerialize(using = SmtpDateTimeSerializer.class)
	private Date updateTime;

	@JsonIgnore
	@Transient
	private Map<String, Object> previousValues;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Date getCreationTime() {
		return creationTime;
	}

	public void setCreationTime(Date creationTime) {
		this.creationTime = creationTime;
	}

	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	public Map<String, Object> getPreviousValues() {
		return previousValues;
	}

	public void setPreviousValues(Map<String, Object> previousValues) {
		this.previousValues = previousValues;
	}

	public void addPreviousValue(String key, Object value) {
		if (previousValues == null) {
			previousValues = new HashMap<String, Object>();
		}

		previousValues.put(key, value);
	}

	public boolean isPreviousValueUpdated(String key, String newValue) {
		if (previousValues == null && newValue != null) {
			return true;
		}

		if (previousValues == null && newValue == null) {
			return false;
		}

		return (StringUtils.isNotEquivalent(getPreviousValueAsString(key), newValue));
	}

	public Object getPreviousValue(String key) {
		if (previousValues == null) {
			return null;
		}

		return previousValues.get(key);
	}

	public String getPreviousValueAsString(String key) {
		if (previousValues == null) {
			return null;
		}

		return StringUtils.valueOf(previousValues.get(key), null);
	}

	public void storePreviousValues() {
		//Subclass should override as necessary
	}

}
