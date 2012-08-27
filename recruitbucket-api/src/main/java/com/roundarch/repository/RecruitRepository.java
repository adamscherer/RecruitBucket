package com.roundarch.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.roundarch.entity.RecruitEntity;
import com.roundarch.entity.RecruitStage;


public interface RecruitRepository extends PagingAndSortingRepository<RecruitEntity, String> {

	Page<RecruitEntity> findByStage(RecruitStage stage, Pageable pageable);
	
}
