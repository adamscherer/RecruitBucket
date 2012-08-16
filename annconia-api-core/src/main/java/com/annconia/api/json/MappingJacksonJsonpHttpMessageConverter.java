package com.annconia.api.json;

import java.io.IOException;
import java.nio.charset.Charset;

import javax.annotation.PostConstruct;

import org.apache.commons.lang.StringUtils;
import org.codehaus.jackson.JsonEncoding;
import org.codehaus.jackson.JsonGenerator;
import org.codehaus.jackson.JsonProcessingException;
import org.codehaus.jackson.map.SerializationConfig;
import org.codehaus.jackson.map.type.TypeFactory;
import org.codehaus.jackson.type.JavaType;
import org.springframework.http.HttpInputMessage;
import org.springframework.http.HttpOutputMessage;
import org.springframework.http.MediaType;
import org.springframework.http.converter.AbstractHttpMessageConverter;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.http.converter.HttpMessageNotWritableException;

import com.annconia.api.util.RequestContext;
import com.annconia.api.util.RequestContextHolder;

@SuppressWarnings("rawtypes")
public class MappingJacksonJsonpHttpMessageConverter extends AbstractHttpMessageConverter {

	public static final Charset DEFAULT_CHARSET = Charset.forName("UTF-8");

	private MediaType JAVASCRIPT_CONTENT_TYPE = new MediaType("text", "javascript", DEFAULT_CHARSET);

	private MediaType HTML_CONTENT_TYPE = new MediaType("text", "html", DEFAULT_CHARSET);

	private String callbackName = "callback";

	/*
	 * Can be used for file upload where the client submits to an IFrame
	 */
	private String htmlJsCallbackName = "htmlJsCallback";

	private boolean prefixJson;

	private SerializationConfig config;

	public MappingJacksonJsonpHttpMessageConverter() {
		super(new MediaType("application", "json", DEFAULT_CHARSET));
		prefixJson = false;
	}

	@PostConstruct
	protected void init() {
		config = JsonUtils.getObjectMapper().copySerializationConfig().without(SerializationConfig.Feature.FLUSH_AFTER_WRITE_VALUE);
	}

	public void setPrefixJson(boolean prefixJson) {
		this.prefixJson = prefixJson;
	}

	public boolean canRead(Class clazz, MediaType mediaType) {
		JavaType javaType = getJavaType(clazz);
		return JsonUtils.getObjectMapper().canDeserialize(javaType) && canRead(mediaType);
	}

	public boolean canWrite(Class clazz, MediaType mediaType) {
		return JsonUtils.getObjectMapper().canSerialize(clazz) && canWrite(mediaType);
	}

	protected boolean supports(Class clazz) {
		throw new UnsupportedOperationException();
	}

	protected Object readInternal(Class clazz, HttpInputMessage inputMessage) throws IOException, HttpMessageNotReadableException {
		JavaType javaType = getJavaType(clazz);
		try {
			return JsonUtils.getObjectMapper().readValue(inputMessage.getBody(), javaType);
		} catch (JsonProcessingException ex) {
			throw new HttpMessageNotReadableException((new StringBuilder("Could not read JSON: ")).append(ex.getMessage()).toString(), ex);
		}
	}

	@Override
	protected void writeInternal(Object object, HttpOutputMessage outputMessage) throws IOException, HttpMessageNotWritableException {

		if (isJsonpRequest()) {
			outputMessage.getHeaders().setContentType(JAVASCRIPT_CONTENT_TYPE);
			writeJsonp(object, outputMessage);
			return;
		}

		if (isWrapJavascriptRequest()) {
			outputMessage.getHeaders().setContentType(HTML_CONTENT_TYPE);
			writeWrappedJavascript(object, outputMessage);
			return;
		}

		JsonEncoding encoding = getJsonEncoding(outputMessage.getHeaders().getContentType());
		JsonGenerator jsonGenerator = JsonUtils.getObjectMapper().getJsonFactory().createJsonGenerator(outputMessage.getBody(), encoding);
		try {
			if (prefixJson)
				jsonGenerator.writeRaw("{} && ");
			JsonUtils.getObjectMapper().writeValue(jsonGenerator, object);
		} catch (JsonProcessingException ex) {
			throw new HttpMessageNotWritableException((new StringBuilder("Could not write JSON: ")).append(ex.getMessage()).toString(), ex);
		}
	}

	private void writeWrappedJavascript(Object object, HttpOutputMessage outputMessage) throws IOException, HttpMessageNotWritableException {
		JsonEncoding encoding = getJsonEncoding(outputMessage.getHeaders().getContentType());
		JsonGenerator jsonGenerator = JsonUtils.getObjectMapper().getJsonFactory().createJsonGenerator(outputMessage.getBody(), encoding);
		try {
			jsonGenerator.writeRaw("<html><body><script type=\"text/javascript\">" + getHtmlJsCallbackParam() + "(");
			JsonUtils.getObjectMapper().writeValue(jsonGenerator, object, config);
			jsonGenerator.writeRaw(")</script></body></html>");
			jsonGenerator.flush();
		} catch (JsonProcessingException ex) {
			throw new HttpMessageNotWritableException((new StringBuilder("Could not write JSON: ")).append(ex.getMessage()).toString(), ex);
		}
	}

	private void writeJsonp(Object object, HttpOutputMessage outputMessage) throws IOException, HttpMessageNotWritableException {
		JsonEncoding encoding = getJsonEncoding(outputMessage.getHeaders().getContentType());
		JsonGenerator jsonGenerator = JsonUtils.getObjectMapper().getJsonFactory().createJsonGenerator(outputMessage.getBody(), encoding);
		try {
			jsonGenerator.writeRaw(getCallbackParam() + "(");
			JsonUtils.getObjectMapper().writeValue(jsonGenerator, object, config);
			jsonGenerator.writeRaw(")");
			jsonGenerator.flush();
		} catch (JsonProcessingException ex) {
			throw new HttpMessageNotWritableException((new StringBuilder("Could not write JSON: ")).append(ex.getMessage()).toString(), ex);
		}
	}

	private boolean isJsonpRequest() {
		return StringUtils.isNotEmpty(getCallbackParam());
	}

	private boolean isWrapJavascriptRequest() {
		return StringUtils.isNotEmpty(getHtmlJsCallbackParam());
	}

	private String getCallbackParam() {
		RequestContext request = RequestContextHolder.get();
		if (request == null) {
			return null;
		}

		return request.getParameter(callbackName);
	}

	private String getHtmlJsCallbackParam() {
		RequestContext request = RequestContextHolder.get();
		if (request == null) {
			return null;
		}

		return request.getParameter(htmlJsCallbackName);
	}

	public String getCallbackName() {
		return callbackName;
	}

	public void setCallbackName(String callbackName) {
		this.callbackName = callbackName;
	}

	public String getHtmlJsCallbackName() {
		return htmlJsCallbackName;
	}

	public void setHtmlJsCallbackName(String htmlJsCallbackName) {
		this.htmlJsCallbackName = htmlJsCallbackName;
	}

	@SuppressWarnings("deprecation")
	protected JavaType getJavaType(Class clazz) {
		return TypeFactory.type(clazz);
	}

	protected JsonEncoding getJsonEncoding(MediaType contentType) {
		if (contentType != null && contentType.getCharSet() != null) {
			Charset charset = contentType.getCharSet();
			JsonEncoding ajsonencoding[];
			int j = (ajsonencoding = JsonEncoding.values()).length;
			for (int i = 0; i < j; i++) {
				JsonEncoding encoding = ajsonencoding[i];
				if (charset.name().equals(encoding.getJavaName()))
					return encoding;
			}

		}
		return JsonEncoding.UTF8;
	}

}
