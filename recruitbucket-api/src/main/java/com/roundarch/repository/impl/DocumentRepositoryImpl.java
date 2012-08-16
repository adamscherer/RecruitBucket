package com.roundarch.repository.impl;

import java.io.InputStream;
import java.net.URLConnection;

import org.bson.types.ObjectId;

import com.mongodb.DB;
import com.mongodb.gridfs.GridFS;
import com.mongodb.gridfs.GridFSDBFile;
import com.mongodb.gridfs.GridFSInputFile;
import com.roundarch.repository.DocumentRepository;

public class DocumentRepositoryImpl implements DocumentRepository {

	private final GridFS gridFs;

	public DocumentRepositoryImpl(DB gridfsDb) {
		gridFs = new GridFS(gridfsDb);
	}

	public String save(InputStream inputStream, String filename) {
		return save(inputStream, URLConnection.guessContentTypeFromName(filename), filename);
	}

	public String save(InputStream inputStream, String contentType, String filename) {
		GridFSInputFile input = gridFs.createFile(inputStream, filename, true);
		input.setContentType(contentType);
		input.save();
		return input.getId().toString();
	}

	public GridFSDBFile get(String id) {
		return gridFs.findOne(new ObjectId(id));
	}

	public GridFSDBFile getByFilename(String filename) {
		return gridFs.findOne(filename);
	}

}
