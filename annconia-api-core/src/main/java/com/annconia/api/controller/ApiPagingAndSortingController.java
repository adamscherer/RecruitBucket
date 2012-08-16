package com.annconia.api.controller;

import org.springframework.data.domain.Page;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.WebRequest;

import com.annconia.api.PagingAndSorting;
import com.annconia.api.entity.AbstractEntity;
import com.annconia.api.json.JsonErrorResponse;

public abstract class ApiPagingAndSortingController<ENTITY extends AbstractEntity, REPOSITORY extends PagingAndSortingRepository<ENTITY, String>> extends ApiCrudController<ENTITY, REPOSITORY> {

	@RequestMapping(value = "/all", method = RequestMethod.GET)
	@ResponseBody
	public ResponseEntity<Object> findAll(ServerHttpRequest request, WebRequest webRequest, PagingAndSorting pageSort) {

		Page<ENTITY> page = repository.findAll(pageSort);
		if (null == page) {
			return new ResponseEntity<Object>(new JsonErrorResponse("entity.not.found"), HttpStatus.NOT_FOUND);
		}

		return new ResponseEntity<Object>(page, HttpStatus.OK);
	}

}
