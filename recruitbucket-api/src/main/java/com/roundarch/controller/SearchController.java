package com.roundarch.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.apache.commons.collections.trie.PatriciaTrie;
import org.apache.commons.collections.trie.StringKeyAnalyzer;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.annconia.api.controller.ApiController;
import com.annconia.util.CollectionUtils;
import com.roundarch.entity.College;
import com.roundarch.entity.Hometown;
import com.roundarch.repository.LookupDataRepository;

@SuppressWarnings("restriction")
@Controller
@RequestMapping("/search")
public class SearchController extends ApiController {

	@Autowired(required = false)
	private LookupDataRepository repository;

	@RequestMapping(value = "/hometown", method = RequestMethod.GET, params = "q")
	@ResponseBody
	public ResponseEntity<Object> hometown(@RequestParam String q, @RequestParam(defaultValue = "50") int size) throws Exception {
		return new ResponseEntity<Object>(repository.getHometowns(q, size), HttpStatus.OK);
	}

	@RequestMapping(value = "/college", method = RequestMethod.GET, params = "q")
	@ResponseBody
	public ResponseEntity<Object> college(@RequestParam String q, @RequestParam(defaultValue = "50") int size) throws Exception {
		return new ResponseEntity<Object>(repository.getColleges(q, size), HttpStatus.OK);
	}

	@RequestMapping(value = "/interviewer", method = RequestMethod.GET, params = "q")
	@ResponseBody
	public ResponseEntity<Object> interviewer(@RequestParam String q, @RequestParam(defaultValue = "50") int size) throws Exception {
		return new ResponseEntity<Object>(repository.getInterviewers(q, size), HttpStatus.OK);
	}

	@RequestMapping(value = "/question", method = RequestMethod.GET, params = "q")
	@ResponseBody
	public ResponseEntity<Object> question(@RequestParam String q, @RequestParam(defaultValue = "50") int size) throws Exception {
		return new ResponseEntity<Object>(repository.getQuestions(q, size), HttpStatus.OK);
	}
}
