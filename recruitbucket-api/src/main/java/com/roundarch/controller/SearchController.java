package com.roundarch.controller;

import java.util.SortedMap;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.collections.trie.PatriciaTrie;
import org.apache.commons.collections.trie.StringKeyAnalyzer;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.annconia.api.controller.ApiController;
import com.roundarch.entity.Hometown;
import com.roundarch.repository.LookupDataRepository;

@SuppressWarnings("restriction")
@Controller
@RequestMapping("/search")
public class SearchController extends ApiController {

	@Autowired(required = false)
	private LookupDataRepository repository;

	PatriciaTrie<String, String> HOMETOWN_TRIE = new PatriciaTrie<String, String>(new StringKeyAnalyzer());

	@PostConstruct
	public void init() {
		for (Hometown hometown : repository.getAllHometowns()) {
			HOMETOWN_TRIE.put(hometown.getCity(), hometown.getCity() + " " + hometown.getState());
		}
	}

	@RequestMapping(value = "/hometown", method = RequestMethod.GET, params = "q")
	@ResponseBody
	public ResponseEntity<Object> hometown(@RequestParam String q, ServerHttpRequest request, HttpServletRequest servletRequest) throws Exception {

		SortedMap<String, String> values = HOMETOWN_TRIE.getPrefixedBy(StringUtils.upperCase(q));
		return new ResponseEntity<Object>(values, HttpStatus.OK);
	}

}
