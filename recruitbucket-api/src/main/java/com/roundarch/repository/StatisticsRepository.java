package com.roundarch.repository;

import org.springframework.data.repository.PagingAndSortingRepository;

import com.roundarch.entity.StatisticsEntity;

public interface StatisticsRepository extends StatisticsRepositoryExtension, PagingAndSortingRepository<StatisticsEntity, String> {

}
