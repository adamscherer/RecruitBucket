package com.roundarch.controller;

import java.util.List;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.annconia.api.PagingAndSorting;
import com.annconia.api.controller.ApiPagingAndSortingController;
import com.roundarch.entity.ReviewEntity;
import com.roundarch.repository.ReviewRepository;

@SuppressWarnings("restriction")
@Controller
@RequestMapping("/review")
public class ReviewController extends ApiPagingAndSortingController<ReviewEntity, ReviewRepository> {

	@Autowired(required = false)
	private ReviewRepository repository;

	@PostConstruct
	public void init() {
		setRepository(repository);
	}

	@RequestMapping(value = "/all", method = RequestMethod.GET, params = "recruitId")
	@ResponseBody
	public ResponseEntity<Object> findAll(@RequestParam String recruitId, PagingAndSorting pageSort) {

		List<ReviewEntity> documents = repository.findByRecruitId(recruitId);

		return new ResponseEntity<Object>(documents, HttpStatus.OK);
	}

}