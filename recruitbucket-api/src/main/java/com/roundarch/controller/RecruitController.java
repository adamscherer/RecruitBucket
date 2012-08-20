package com.roundarch.controller;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.annconia.api.PagingAndSorting;
import com.annconia.api.controller.ApiPagingAndSortingController;
import com.annconia.api.json.JsonErrorResponse;
import com.annconia.api.json.JsonResponse;
import com.roundarch.entity.DocumentMetadataEntity;
import com.roundarch.entity.RecruitEntity;
import com.roundarch.repository.ActivityRepository;
import com.roundarch.repository.DocumentMetadataRepository;
import com.roundarch.repository.DocumentRepository;
import com.roundarch.repository.RecruitRepository;
import com.roundarch.repository.ReviewRepository;

@SuppressWarnings("restriction")
@Controller
@RequestMapping("/recruit")
public class RecruitController extends ApiPagingAndSortingController<RecruitEntity, RecruitRepository> {

	@Autowired(required = false)
	private RecruitRepository repository;

	@Autowired(required = false)
	private DocumentRepository resumeRepository;

	@Autowired(required = false)
	private DocumentMetadataRepository documentMetadataRepository;

	@Autowired(required = false)
	private ReviewRepository reviewRepository;

	@Autowired(required = false)
	private ActivityRepository activityRepository;

	@PostConstruct
	public void init() {
		setRepository(repository);
	}

	@RequestMapping(value = "/composite/{id}", method = RequestMethod.GET)
	@ResponseBody
	public ResponseEntity<JsonResponse> entity(ServerHttpRequest request, @PathVariable String id, PagingAndSorting pageSort) {

		RecruitEntity entity = repository.findOne(id);
		if (null == entity) {
			return new ResponseEntity<JsonResponse>(new JsonErrorResponse("entity.not.found"), HttpStatus.NOT_FOUND);
		}

		entity.setReviews(reviewRepository.findByRecruitId(entity.getId()));
		entity.setDocuments(documentMetadataRepository.findByRecruitId(entity.getId()));
		entity.setActivities(activityRepository.findByRecruitId(entity.getId(), pageSort).getContent());

		return new ResponseEntity<JsonResponse>(entity, HttpStatus.OK);
	}

	@RequestMapping(value = "/composite/{id}", method = RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<JsonResponse> createOrUpdate(ServerHttpRequest request, HttpServletRequest servletRequest, @PathVariable String id, PagingAndSorting pageSort) throws IllegalAccessException, Exception {

		ResponseEntity<JsonResponse> response = super.createOrUpdate(request, servletRequest, id);

		if (response.getStatusCode() != HttpStatus.OK) {
			return response;
		}

		if (response.getBody() instanceof RecruitEntity) {
			RecruitEntity entity = (RecruitEntity) response.getBody();
			entity.setReviews(reviewRepository.findByRecruitId(entity.getId()));
			entity.setDocuments(documentMetadataRepository.findByRecruitId(entity.getId()));
			entity.setActivities(activityRepository.findByRecruitId(entity.getId(), pageSort).getContent());
		}

		return response;
	}

	@RequestMapping(method = RequestMethod.POST, params = "resume")
	@ResponseBody
	public ResponseEntity<JsonResponse> create(@RequestParam(required = false) MultipartFile file, ServerHttpRequest request, HttpServletRequest servletRequest) throws Exception {

		ResponseEntity<JsonResponse> response = super.create(request, servletRequest);

		if (response.getStatusCode() != HttpStatus.OK || file == null || file.getSize() < 1) {
			return response;
		}

		if (response.getBody() instanceof RecruitEntity) {
			String fileId = resumeRepository.save(file.getInputStream(), file.getContentType(), file.getOriginalFilename());
			RecruitEntity recruit = (RecruitEntity) response.getBody();
			DocumentMetadataEntity entity = new DocumentMetadataEntity(fileId, file.getOriginalFilename(), file.getContentType());
			entity.setRecruitId(recruit.getId());
			documentMetadataRepository.save(entity);
		}

		return response;
	}

}
