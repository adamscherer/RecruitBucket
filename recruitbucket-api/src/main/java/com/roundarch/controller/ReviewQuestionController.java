package com.roundarch.controller;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.annconia.api.controller.ApiPagingAndSortingController;
import com.roundarch.entity.QuestionEntity;
import com.roundarch.repository.ReviewQuestionRepository;

@SuppressWarnings("restriction")
@Controller
@RequestMapping("/question")
public class ReviewQuestionController extends ApiPagingAndSortingController<QuestionEntity, ReviewQuestionRepository> {

	@Autowired(required = false)
	private ReviewQuestionRepository repository;

	@PostConstruct
	public void init() {
		setRepository(repository);
	}

}