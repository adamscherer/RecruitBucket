package com.roundarch.repository;

import java.util.List;

import org.springframework.data.repository.PagingAndSortingRepository;

import com.roundarch.entity.ReviewEntity;

public interface ReviewRepository extends PagingAndSortingRepository<ReviewEntity, String> {

	List<ReviewEntity> findByRecruitId(String recruitId);

}
