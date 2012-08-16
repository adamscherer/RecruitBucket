package com.roundarch.repository;

import org.springframework.data.repository.PagingAndSortingRepository;

import com.roundarch.entity.QuestionEntity;

public interface ReviewQuestionRepository extends PagingAndSortingRepository<QuestionEntity, String> {

}
