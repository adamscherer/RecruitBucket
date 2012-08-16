package com.annconia.security.entity;

import java.util.Date;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * Annotations are leveraged for the Mongo implementation.
 * 
 * @author Adam Scherer
 *
 */
@Document
public class Session {

	@Id
	private String id;

	private String userId;
	private Date creationTime;
	private Date lastAccessTime;

	private int limit;
	private int remaining;
	private Date reset;

	public Session(String id) {
		this.id = id;
	}

	public String getId() {
		return id;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public Date getCreationTime() {
		return creationTime;
	}

	public void setCreationTime(Date creationTime) {
		this.creationTime = creationTime;
	}

	public Date getLastAccessTime() {
		return lastAccessTime;
	}

	public void setLastAccessTime(Date lastAccessTime) {
		this.lastAccessTime = lastAccessTime;
	}

	public int getLimit() {
		return limit;
	}

	public void setLimit(int limit) {
		this.limit = limit;
	}

	public int getRemaining() {
		return remaining;
	}

	public void setRemaining(int remaining) {
		this.remaining = remaining;
	}

	public Date getReset() {
		return reset;
	}

	public void setReset(Date reset) {
		this.reset = reset;
	}

	@Override
	public String toString() {
		return "Session [id=" + id + ", userId=" + userId + ", creationTime=" + creationTime + "]";
	}

}
