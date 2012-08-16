package com.roundarch.repository;

import java.util.List;

import org.springframework.data.repository.PagingAndSortingRepository;

import com.roundarch.entity.DocumentMetadataEntity;

public interface DocumentMetadataRepository extends PagingAndSortingRepository<DocumentMetadataEntity, String> {

	List<DocumentMetadataEntity> findByRecruitId(String recruitId);

}