package com.roundarch.controller;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.async.DeferredResult;

import com.annconia.api.PagingAndSorting;
import com.annconia.api.controller.ApiPagingAndSortingController;
import com.roundarch.entity.ActivityEntity;
import com.roundarch.repository.ActivityRepository;
import com.roundarch.repository.listener.ActivityListener;

@SuppressWarnings("restriction")
@Controller
@RequestMapping("/activity")
public class ActivityController extends ApiPagingAndSortingController<ActivityEntity, ActivityRepository> {

	@Autowired(required = false)
	private ActivityRepository repository;

	@Autowired(required = false)
	private ActivityListener listener;

	@PostConstruct
	public void init() {
		setRepository(repository);
	}

	@RequestMapping(value = "/all", method = RequestMethod.GET, params = "recruitId")
	@ResponseBody
	public ResponseEntity<Object> findAll(@RequestParam String recruitId, PagingAndSorting pageSort) {

		Page<ActivityEntity> documents = repository.findByRecruitId(recruitId, pageSort);

		return new ResponseEntity<Object>(documents, HttpStatus.OK);
	}

	@RequestMapping(value = "/subscribe", method = RequestMethod.GET)
	@ResponseBody
	public Object lastestActivities() {
		return listener.subscribe(new DeferredResult<Page<ActivityEntity>>());
	}

	public ActivityListener getListener() {
		return listener;
	}

	public void setListener(ActivityListener listener) {
		this.listener = listener;
	}

}
