package com.roundarch.controller;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.annconia.api.controller.ApiPagingAndSortingController;
import com.roundarch.entity.StatisticsEntity;
import com.roundarch.repository.StatisticsRepository;

@SuppressWarnings("restriction")
@Controller
@RequestMapping(value = "/statistics")
public class StatisticController extends ApiPagingAndSortingController<StatisticsEntity, StatisticsRepository> {

	@Autowired(required = false)
	private StatisticsRepository repository;

	@PostConstruct
	public void init() {
		setRepository(repository);
	}

}
