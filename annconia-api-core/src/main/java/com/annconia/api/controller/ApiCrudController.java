package com.annconia.api.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.core.GenericTypeResolver;
import org.springframework.data.repository.CrudRepository;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.annconia.api.entity.AbstractEntity;
import com.annconia.api.json.JsonErrorResponse;
import com.annconia.api.json.JsonResponse;
import com.annconia.api.repository.AfterDeleteEvent;
import com.annconia.api.repository.AfterSaveEvent;
import com.annconia.api.repository.BeforeDeleteEvent;
import com.annconia.api.repository.BeforeSaveEvent;

public abstract class ApiCrudController<ENTITY extends AbstractEntity, REPOSITORY extends CrudRepository<ENTITY, String>> extends ApiController {

	private final Class<ENTITY> entityType;

	REPOSITORY repository;

	private final JsonResponse NULL_RESPONSE = new JsonResponse() {

	};

	@SuppressWarnings("unchecked")
	public ApiCrudController() {
		this.entityType = (Class<ENTITY>) GenericTypeResolver.resolveTypeArguments(getClass(), ApiCrudController.class)[0];
	}

	@RequestMapping(method = RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<JsonResponse> create(ServerHttpRequest request, HttpServletRequest servletRequest) throws Exception {

		MediaType incomingMediaType = request.getHeaders().getContentType();
		final ENTITY incoming = readIncoming(request, servletRequest, incomingMediaType, entityType);
		if (null == incoming) {
			return new ResponseEntity<JsonResponse>(new JsonErrorResponse("entity.not.created"), HttpStatus.NOT_ACCEPTABLE);
		}

		ENTITY savedEntity = save(incoming);

		HttpHeaders headers = new HttpHeaders();
		headers.setLocation(buildUri(request.getURI(), savedEntity.getId()));
		return new ResponseEntity<JsonResponse>(savedEntity, headers, HttpStatus.OK);
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	@ResponseBody
	public ResponseEntity<JsonResponse> entity(ServerHttpRequest request, @PathVariable String id) {

		ENTITY entity = repository.findOne(id);
		if (null == entity) {
			return new ResponseEntity<JsonResponse>(new JsonErrorResponse("entity.not.found"), HttpStatus.NOT_FOUND);
		}

		return new ResponseEntity<JsonResponse>(entity, HttpStatus.OK);
	}

	@RequestMapping(value = "/{id}", method = { RequestMethod.POST })
	@ResponseBody
	public ResponseEntity<JsonResponse> createOrUpdate(ServerHttpRequest request, HttpServletRequest servletRequest, @PathVariable String id) throws IllegalAccessException, Exception {

		ENTITY entity = repository.findOne(id);
		if (null == entity) {
			return new ResponseEntity<JsonResponse>(new JsonErrorResponse("entity.not.found"), HttpStatus.NOT_FOUND);
		}

		final MediaType incomingMediaType = request.getHeaders().getContentType();
		entity.storePreviousValues();
		entity = readIncoming(request, servletRequest, incomingMediaType, entity);

		ENTITY savedEntity = save(entity);

		HttpHeaders headers = new HttpHeaders();
		headers.setLocation(request.getURI());
		return new ResponseEntity<JsonResponse>(savedEntity, headers, HttpStatus.OK);
	}


	@RequestMapping(value = "/{id}", headers = "X-HTTP-Method-Override=DELETE", method = { RequestMethod.POST, RequestMethod.DELETE })
	@ResponseBody
	public ResponseEntity<JsonResponse> deleteEntityById(@PathVariable String id) {
		return deleteEntity(id);
	}
	
	@RequestMapping(value = "/delete/{id}", method = { RequestMethod.POST, RequestMethod.DELETE })
	@ResponseBody
	public ResponseEntity<JsonResponse> deleteEntity(@PathVariable String id) {

		ENTITY entity = repository.findOne(id);
		if (null == entity) {
			return new ResponseEntity<JsonResponse>(new JsonErrorResponse("entity.not.found"), HttpStatus.NOT_FOUND);
		}

		if (null != getApplicationContext()) {
			getApplicationContext().publishEvent(new BeforeDeleteEvent(entity));
		}
		repository.delete(id);
		if (null != getApplicationContext()) {
			getApplicationContext().publishEvent(new AfterDeleteEvent(entity));
		}

		return new ResponseEntity<JsonResponse>(NULL_RESPONSE, HttpStatus.NO_CONTENT);
	}

	public REPOSITORY getRepository() {
		return repository;
	}

	public void setRepository(REPOSITORY repository) {
		this.repository = repository;
	}

	private ENTITY save(ENTITY entity) {
		if (null != getApplicationContext()) {
			getApplicationContext().publishEvent(new BeforeSaveEvent(entity));
		}
		ENTITY savedEntity = repository.save(entity);
		if (null != getApplicationContext()) {
			getApplicationContext().publishEvent(new AfterSaveEvent(savedEntity));
		}

		return savedEntity;
	}

}
