package com.roundarch.controller;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.annconia.api.controller.ApiPagingAndSortingController;
import com.annconia.api.json.JsonResponse;
import com.roundarch.entity.DocumentMetadataEntity;
import com.roundarch.entity.RecruitEntity;
import com.roundarch.repository.DocumentMetadataRepository;
import com.roundarch.repository.DocumentRepository;
import com.roundarch.repository.RecruitRepository;

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

	@PostConstruct
	public void init() {
		setRepository(repository);
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
