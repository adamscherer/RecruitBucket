package com.roundarch.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.roundarch.entity.ActivityEntity;


public interface ActivityRepository extends ActivityRepositoryExtension, PagingAndSortingRepository<ActivityEntity, String> {

	Page<ActivityEntity> findByRecruitId(String recruitId, Pageable pageable);
	
}
