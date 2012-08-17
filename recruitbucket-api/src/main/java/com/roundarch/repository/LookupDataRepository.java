package com.roundarch.repository;

import com.roundarch.entity.TypeAheadResponse;

public interface LookupDataRepository {

	public TypeAheadResponse getHometowns(String query, int size);

	public TypeAheadResponse getColleges(String query, int size);

	public TypeAheadResponse getInterviewers(String query, int size);

	public TypeAheadResponse getQuestions(String query, int size);

}
