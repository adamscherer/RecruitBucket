package com.roundarch.repository;

import java.io.InputStream;

import com.mongodb.gridfs.GridFSDBFile;

public interface DocumentRepository {

	public String save(InputStream inputStream, String filename);

	String save(InputStream inputStream, String contentType, String filename);

	GridFSDBFile get(String id);

	GridFSDBFile getByFilename(String filename);

}
