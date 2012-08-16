package com.roundarch.repository.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoOperations;

import com.roundarch.entity.Hometown;
import com.roundarch.repository.LookupDataRepository;

public class LookupDataRepositoryImpl implements LookupDataRepository {

	@Autowired
	MongoOperations mongoOperations;
	
	public List<Hometown> getAllHometowns() {
		return mongoOperations.findAll(Hometown.class);
	}

}
