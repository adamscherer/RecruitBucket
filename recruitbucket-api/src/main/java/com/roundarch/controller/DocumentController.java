package com.roundarch.controller;

import java.io.IOException;
import java.net.URI;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.annconia.api.PagingAndSorting;
import com.annconia.api.controller.ApiPagingAndSortingController;
import com.mongodb.gridfs.GridFSDBFile;
import com.roundarch.entity.DocumentMetadataEntity;
import com.roundarch.repository.DocumentMetadataRepository;
import com.roundarch.repository.DocumentRepository;

@SuppressWarnings("restriction")
@Controller
@RequestMapping("/document")
public class DocumentController extends ApiPagingAndSortingController<DocumentMetadataEntity, DocumentMetadataRepository> {

	@Autowired(required = false)
	private DocumentMetadataRepository repository;

	@Autowired(required = false)
	private DocumentRepository documentRepository;

	@PostConstruct
	public void init() {
		setRepository(repository);
	}

	@RequestMapping(value = "/all", method = RequestMethod.GET, params = "recruitId")
	@ResponseBody
	public ResponseEntity<Object> findAll(@RequestParam String recruitId, PagingAndSorting pageSort) {

		List<DocumentMetadataEntity> documents = repository.findByRecruitId(recruitId);

		return new ResponseEntity<Object>(documents, HttpStatus.OK);
	}

	@RequestMapping(value = "/upload", method = RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<Object> create(@RequestParam MultipartFile file, @RequestParam String recruitId) throws Exception {

		String fileId = documentRepository.save(file.getInputStream(), file.getContentType(), file.getOriginalFilename());
		DocumentMetadataEntity entity = new DocumentMetadataEntity(fileId, file.getOriginalFilename(), file.getContentType());
		entity.setRecruitId(recruitId);
		repository.save(entity);

		String storedURL = "/api/document/view/" + fileId;
		HttpHeaders responseHeaders = new HttpHeaders();
		responseHeaders.setLocation(new URI(storedURL));

		return new ResponseEntity<Object>(entity, responseHeaders, HttpStatus.OK);
	}

	@RequestMapping(value = "/view/{id}", method = RequestMethod.GET)
	public void getById(@PathVariable(value = "id") String id, HttpServletResponse response) throws IOException {
		GridFSDBFile file = documentRepository.get(id);
		if (file != null) {
			byte[] data = IOUtils.toByteArray(file.getInputStream());
			response.setContentType(file.getContentType());
			response.setContentLength((int) file.getLength());
			response.getOutputStream().write(data);
			response.getOutputStream().flush();
		} else {
			response.setStatus(HttpStatus.NOT_FOUND.value());
		}
	}

}
