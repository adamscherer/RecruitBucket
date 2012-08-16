package com.roundarch.entity;

import com.annconia.api.entity.AbstractEntity;

public class DocumentMetadataEntity extends AbstractEntity {

	private String fileId;
	private String filename;
	private String contentType;
	private String recruitId;

	public DocumentMetadataEntity() {

	}

	public DocumentMetadataEntity(String fileId, String filename, String contentType) {
		this.fileId = fileId;
		this.filename = filename;
		this.contentType = contentType;
	}

	public String getFileId() {
		return fileId;
	}

	public void setFileId(String fileId) {
		this.fileId = fileId;
	}

	public String getFilename() {
		return filename;
	}

	public void setFilename(String filename) {
		this.filename = filename;
	}

	public String getContentType() {
		return contentType;
	}

	public void setContentType(String contentType) {
		this.contentType = contentType;
	}

	public String getRecruitId() {
		return recruitId;
	}

	public void setRecruitId(String recruitId) {
		this.recruitId = recruitId;
	}

}
