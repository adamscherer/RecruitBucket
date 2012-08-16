package com.roundarch.repository;

import org.springframework.data.repository.PagingAndSortingRepository;

import com.roundarch.entity.RecruitEntity;


public interface RecruitRepository extends PagingAndSortingRepository<RecruitEntity, String> {

}
