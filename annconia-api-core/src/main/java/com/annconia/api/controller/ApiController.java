package com.annconia.api.controller;

import java.io.IOException;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpInputMessage;
import org.springframework.http.MediaType;
import org.springframework.http.converter.ByteArrayHttpMessageConverter;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.web.bind.ServletRequestDataBinder;
import org.springframework.web.servlet.support.WebContentGenerator;
import org.springframework.web.util.UriComponentsBuilder;

import com.annconia.api.json.JsonUtils;
import com.annconia.api.json.MappingJacksonJsonpHttpMessageConverter;

public abstract class ApiController extends WebContentGenerator {

	@Autowired(required = false)
	private List<HttpMessageConverter<?>> httpMessageConverters = new ArrayList<HttpMessageConverter<?>>();

	{
		httpMessageConverters.add(0, new StringHttpMessageConverter());
		httpMessageConverters.add(0, new ByteArrayHttpMessageConverter());
		httpMessageConverters.add(0, new MappingJacksonJsonpHttpMessageConverter());
	}

	@SuppressWarnings({ "all" })
	protected <V> V readIncoming(HttpInputMessage request, HttpServletRequest servletRequest, MediaType incomingMediaType, Class<V> targetType) throws IOException, InstantiationException, IllegalAccessException {
		for (HttpMessageConverter converter : httpMessageConverters) {
			if (converter.canRead(targetType, incomingMediaType)) {
				V v = (V) converter.read(targetType, request);

				return v;
			}
		}

		if (incomingMediaType.isCompatibleWith(MediaType.APPLICATION_FORM_URLENCODED) || incomingMediaType.isCompatibleWith(MediaType.MULTIPART_FORM_DATA)) {
			V v = targetType.newInstance();
			ServletRequestDataBinder binder = new ServletRequestDataBinder(v);
			binder.bind(servletRequest);

			return v;
		}

		return null;
	}

	@SuppressWarnings({ "all" })
	protected <V> V readIncoming(HttpInputMessage request, HttpServletRequest servletRequest, MediaType incomingMediaType, V target) throws IOException, Exception {

		if (incomingMediaType.isCompatibleWith(MediaType.APPLICATION_JSON)) {
			return JsonUtils.fromJsonInto(request.getBody(), target);
		}

		if (incomingMediaType.isCompatibleWith(MediaType.APPLICATION_FORM_URLENCODED)) {
			ServletRequestDataBinder binder = new ServletRequestDataBinder(target);
			binder.bind(servletRequest);
			return target;
		}

		return null;
	}

	protected URI buildUri(URI baseUri, String... pathSegments) {
		return UriComponentsBuilder.fromUri(baseUri).pathSegment(pathSegments).build().toUri();
	}

}
