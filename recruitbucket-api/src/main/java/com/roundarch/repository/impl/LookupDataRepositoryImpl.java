package com.roundarch.repository.impl;

import javax.annotation.PostConstruct;

import org.apache.commons.collections.trie.PatriciaTrie;
import org.apache.commons.collections.trie.StringKeyAnalyzer;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoOperations;

import com.roundarch.entity.College;
import com.roundarch.entity.Hometown;
import com.roundarch.entity.InterviewerEntity;
import com.roundarch.entity.QuestionEntity;
import com.roundarch.entity.TypeAheadResponse;
import com.roundarch.repository.LookupDataRepository;

@SuppressWarnings("restriction")
public class LookupDataRepositoryImpl implements LookupDataRepository {

	@Autowired
	MongoOperations mongoOperations;

	PatriciaTrie<String, Hometown> HOMETOWN_TRIE = new PatriciaTrie<String, Hometown>(new StringKeyAnalyzer());

	PatriciaTrie<String, College> COLLEGE_TRIE = new PatriciaTrie<String, College>(new StringKeyAnalyzer());

	PatriciaTrie<String, InterviewerEntity> INTERVIEWER_TRIE = new PatriciaTrie<String, InterviewerEntity>(new StringKeyAnalyzer());

	PatriciaTrie<String, QuestionEntity> QUESTIONS_TRIE = new PatriciaTrie<String, QuestionEntity>(new StringKeyAnalyzer());

	@PostConstruct
	public void init() {
		for (Hometown hometown : mongoOperations.findAll(Hometown.class)) {
			String key = StringUtils.upperCase(hometown.getCity() + " " + hometown.getState());
			if (HOMETOWN_TRIE.containsKey(key)) {
				Hometown existing = HOMETOWN_TRIE.get(key);
				existing.setPop(existing.getPop() + hometown.getPop());
			} else {
				HOMETOWN_TRIE.put(key, hometown);
			}
		}

		for (College college : mongoOperations.findAll(College.class)) {
			COLLEGE_TRIE.put(StringUtils.upperCase(college.getName()), college);
		}

		for (InterviewerEntity interviewer : mongoOperations.findAll(InterviewerEntity.class)) {
			if (StringUtils.isNotEmpty(interviewer.getFirstName())) {
				INTERVIEWER_TRIE.put(StringUtils.upperCase(interviewer.getFirstName() + " " + interviewer.getLastName()), interviewer);
			}
		}

		for (QuestionEntity question : mongoOperations.findAll(QuestionEntity.class)) {
			if (StringUtils.isNotEmpty(question.getQuestion())) {
				QUESTIONS_TRIE.put(StringUtils.upperCase(question.getQuestion()), question);
			}
		}
	}

	public TypeAheadResponse getHometowns(String query, int size) {
		return new TypeAheadResponse(HOMETOWN_TRIE.getPrefixedBy(StringUtils.upperCase(query)), size);
	}

	public TypeAheadResponse getColleges(String query, int size) {
		return new TypeAheadResponse(COLLEGE_TRIE.getPrefixedBy(StringUtils.upperCase(query)), size);
	}

	public TypeAheadResponse getInterviewers(String query, int size) {
		return new TypeAheadResponse(INTERVIEWER_TRIE.getPrefixedBy(StringUtils.upperCase(query)), size);
	}

	public TypeAheadResponse getQuestions(String query, int size) {
		return new TypeAheadResponse(QUESTIONS_TRIE.getPrefixedBy(StringUtils.upperCase(query)), size);
	}

}
