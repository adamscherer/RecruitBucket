package com.roundarch.repository;

import org.springframework.data.repository.PagingAndSortingRepository;

import com.roundarch.entity.InterviewerEntity;

public interface InterviewerRepository extends InterviewerRepositoryExtension, PagingAndSortingRepository<InterviewerEntity, String> {

	InterviewerEntity findBySecurityId(String securityId);

}
