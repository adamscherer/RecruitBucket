package com.annconia.api.json;

import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.StringWriter;
import java.io.Writer;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.codehaus.jackson.JsonEncoding;
import org.codehaus.jackson.JsonFactory;
import org.codehaus.jackson.JsonGenerator;
import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.JsonParser;
import org.codehaus.jackson.JsonProcessingException;
import org.codehaus.jackson.map.DeserializationConfig;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.SerializationConfig;
import org.codehaus.jackson.map.type.CollectionType;
import org.codehaus.jackson.map.type.MapType;
import org.codehaus.jackson.map.type.TypeFactory;
import org.codehaus.jackson.type.JavaType;
import org.codehaus.jackson.type.TypeReference;

/**
 * 
 * @author Adam Scherer
 *
 */
public class JsonUtils {

	private static ObjectMapper m = new ObjectMapper();
	private static final JsonFactory jf = new JsonFactory();

	static {
		m.configure(DeserializationConfig.Feature.FAIL_ON_UNKNOWN_PROPERTIES, false);
		m.configure(JsonParser.Feature.ALLOW_UNQUOTED_FIELD_NAMES, true);
		m.configure(JsonParser.Feature.ALLOW_SINGLE_QUOTES, true);
		m.configure(SerializationConfig.Feature.FAIL_ON_EMPTY_BEANS, false);

		m.setDateFormat(new LocalStdDateFormat());
		
		//m.registerModule(new WithingsModule());
	}

	public static final String EMPTY_ARRAY = "[]";
	public static final String EMPTY_OBJECT = "{}";

	public static <T> T fromJson(String jsonAsString, Class<T> pojoClass) throws JsonException {
		try {
			return m.readValue(jsonAsString, pojoClass);
		} catch (JsonParseException e) {
			throw new JsonException(e);
		} catch (JsonMappingException e) {
			throw new JsonException(e);
		} catch (IOException e) {
			throw new JsonException(e);
		}
	}

	/**
	 * Convenience method for those situations where it not necessary to catch the exception
	 * and the result can be null.
	 * 
	 * @param <T>
	 * @param jsonAsString
	 * @param pojoClass
	 * @return
	 */
	public static <T> T fromJsonNullable(String jsonAsString, Class<T> pojoClass) {
		try {
			return fromJson(jsonAsString, pojoClass);
		} catch (Throwable e) {
			return null;
		}
	}

	public static <T> T fromJson(FileReader fr, Class<T> pojoClass) throws JsonException {
		try {
			return m.readValue(fr, pojoClass);
		} catch (JsonParseException e) {
			throw new JsonException(e);
		} catch (JsonMappingException e) {
			throw new JsonException(e);
		} catch (IOException e) {
			throw new JsonException(e);
		}
	}

	public static <T> T fromJson(InputStream is, Class<T> pojoClass) throws JsonException {
		try {
			return m.readValue(is, pojoClass);
		} catch (JsonParseException e) {
			throw new JsonException(e);
		} catch (JsonMappingException e) {
			throw new JsonException(e);
		} catch (IOException e) {
			throw new JsonException(e);
		}
	}

	/*
	 * Bug in the javac compiler forces an explicit cast whereas the Eclipse compiler does not.
	 * This should be fixed in javac 1.7.0 - http://bugs.sun.com/bugdatabase/view_bug.do?bug_id=6302954.
	 */
	@SuppressWarnings("unchecked")
	public static <T> T fromJson(String jsonAsString, JavaType javaType) throws JsonException {
		try {
			return (T) m.readValue(jsonAsString, javaType);
		} catch (JsonParseException e) {
			throw new JsonException(e);
		} catch (JsonMappingException e) {
			throw new JsonException(e);
		} catch (IOException e) {
			throw new JsonException(e);
		}
	}

	public static Map<String, Object> fromJsonToMap(String jsonAsString) {
		return fromJsonToMap(jsonAsString, String.class, Object.class);
	}

	public static <K, V> Map<K, V> fromJsonToMap(String jsonAsString, Class<K> keyClass, Class<V> valueClass) {
		try {
			return fromJsonToMapUnsafe(jsonAsString, keyClass, valueClass);
		} catch (JsonException ex) {
			return null;
		}
	}

	@SuppressWarnings("unchecked")
	public static <K, V> Map<K, V> fromJsonToMapUnsafe(String jsonAsString, Class<K> keyClass, Class<V> valueClass) throws JsonException {
		try {
			return (Map<K, V>) m.readValue(jsonAsString, getMapTypeReference(keyClass, valueClass));
		} catch (JsonParseException e) {
			throw new JsonException(e);
		} catch (JsonMappingException e) {
			throw new JsonException(e);
		} catch (IOException e) {
			throw new JsonException(e);
		}
	}

	public static List<Object> fromJsonToList(String jsonAsString) {
		return fromJsonToList(jsonAsString, Object.class);
	}

	public static <T> List<T> fromJsonToList(String jsonAsString, Class<T> pojoClass) {
		try {
			return fromJsonToListUnsafe(jsonAsString, pojoClass);
		} catch (JsonException ex) {
			return null;
		}
	}

	@SuppressWarnings("unchecked")
	public static <T> List<T> fromJsonToListUnsafe(String jsonAsString, Class<T> pojoClass) throws JsonException {
		try {
			return (List<T>) m.readValue(jsonAsString, getListTypeReference(pojoClass));
		} catch (JsonParseException e) {
			throw new JsonException(e);
		} catch (JsonMappingException e) {
			throw new JsonException(e);
		} catch (IOException e) {
			throw new JsonException(e);
		}
	}

	public static CollectionType getListTypeReference(Class<?> responseType) {
		return getCollectionTypeReference(ArrayList.class, responseType);
	}

	public static CollectionType getSetTypeReference(Class<?> responseType) {
		return getCollectionTypeReference(HashSet.class, responseType);
	}

	@SuppressWarnings("all")
	protected static CollectionType getCollectionTypeReference(Class<? extends Collection> collectionType, Class<?> responseType) {
		if (collectionType == null || collectionType.isInterface()) {
			throw new IllegalArgumentException("Collection Type cannot be null nor an interface");
		}

		try {
			collectionType.newInstance();
		} catch (Throwable e) {
			throw new IllegalArgumentException("Collection Type must have default constructor");
		}

		return TypeFactory.defaultInstance().constructCollectionType(collectionType, responseType);
	}

	public static MapType getMapTypeReference(Class<?> keyClass, Class<?> valueClass) {
		return TypeFactory.defaultInstance().constructMapType(LinkedHashMap.class, keyClass, valueClass);
	}

	public static Map<String, Object> mapify(Object pojo) throws JsonException {
		return fromJsonToMapUnsafe(toJson(pojo, false), String.class, Object.class);
	}

	public static <T> T unmapify(Map<String, ?> map, Class<T> pojoClass) throws JsonException {
		return fromJson(toJson(map, false), pojoClass);
	}

	public static List<Object> listify(Object pojo) throws JsonException {
		return fromJsonToListUnsafe(toJson(pojo, false), Object.class);
	}

	/**
	 * Convenience method for those situations where it not necessary to catch the exception
	 * and the result can be null.
	 * 
	 * Bug in the javac compiler forces an explicit cast whereas the Eclipse compiler does not.
	 * This should be fixed in javac 1.7.0 - http://bugs.sun.com/bugdatabase/view_bug.do?bug_id=6302954.
	 * 
	 * @param <T>
	 * @param jsonAsString
	 * @param javaType
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static <T> T fromJsonNullable(String jsonAsString, JavaType javaType) {
		try {
			return (T) fromJson(jsonAsString, javaType);
		} catch (Throwable ex) {
			return null;
		}
	}

	/**
	 * 
	 * @param <T>
	 * @param jsonAsString
	 * @param typeRef
	 * @return - type casted objects. this can be used to parse json string as collections.
	 * @throws JsonMappingException
	 * @throws JsonParseException
	 * @throws IOException
	 */
	@SuppressWarnings("unchecked")
	public static <T> T fromJson(String jsonAsString, TypeReference<T> typeRef) throws JsonException {
		try {
			return (T) m.readValue(jsonAsString, typeRef);
		} catch (JsonParseException e) {
			throw new JsonException(e);
		} catch (JsonMappingException e) {
			throw new JsonException(e);
		} catch (IOException e) {
			throw new JsonException(e);
		}
	}

	public static <T> T fromJsonNullable(String jsonAsString, TypeReference<T> typeRef) {
		try {
			return fromJson(jsonAsString, typeRef);
		} catch (Throwable ex) {
			return null;
		}
	}

	/**
	 * Convenience method for those situations where it not necessary to catch the exception
	 * and the result can be null.
	 * 
	 * @param pojo
	 * @return
	 */
	public static String toJsonNullable(Object pojo) {
		return toJsonNullable(pojo, false);
	}

	/**
	 * Convenience method for those situations where it not necessary to catch the exception
	 * and the result can be null.
	 * 
	 * @param pojo
	 * @param prettyPrint
	 * @return
	 */
	public static String toJsonNullable(Object pojo, boolean prettyPrint) {
		return toJsonNullable(pojo, Object.class, prettyPrint);
	}

	public static String toJsonNullable(Object pojo, Class<?> viewClass, boolean prettyPrint) {
		try {
			return toJson(pojo, viewClass, prettyPrint);
		} catch (JsonException e) {
			return null;
		}
	}

	public static String toJson(Object pojo) throws JsonException {
		return toJson(pojo, false);
	}

	public static String toJson(Object pojo, boolean prettyPrint) throws JsonException {
		return toJson(pojo, Object.class, prettyPrint);
	}

	public static String toJson(Object pojo, Class<?> viewClass, boolean prettyPrint) throws JsonException {
		try {
			StringWriter sw = new StringWriter();
			JsonGenerator jg = createJsonGenerator(sw);

			if (prettyPrint) {
				jg.useDefaultPrettyPrinter();
			}

			m.writerWithView(viewClass).writeValue(jg, pojo);
			return sw.toString();
		} catch (JsonParseException e) {
			throw new JsonException(e);
		} catch (JsonMappingException e) {
			throw new JsonException(e);
		} catch (IOException e) {
			throw new JsonException(e);
		}
	}

	public static void toJson(Object pojo, Writer fw, boolean prettyPrint) throws JsonException {
		JsonGenerator jg = createJsonGenerator(fw);

		if (prettyPrint) {
			jg.useDefaultPrettyPrinter();
		}

		try {
			m.writeValue(jg, pojo);
		} catch (JsonParseException e) {
			throw new JsonException(e);
		} catch (JsonMappingException e) {
			throw new JsonException(e);
		} catch (IOException e) {
			throw new JsonException(e);
		}
	}

	public static void toJson(Object pojo, OutputStream os) throws JsonException {
		JsonGenerator jg = createJsonGenerator(os);

		try {
			m.writeValue(jg, pojo);
		} catch (JsonParseException e) {
			throw new JsonException(e);
		} catch (JsonMappingException e) {
			throw new JsonException(e);
		} catch (IOException e) {
			throw new JsonException(e);
		}
	}

	public static JsonGenerator createJsonGenerator(Writer w) throws JsonException {
		try {
			return jf.createJsonGenerator(w);
		} catch (IOException ex) {
			throw new JsonException(ex);
		}
	}

	public static JsonGenerator createJsonGenerator(OutputStream os) throws JsonException {
		try {
			return jf.createJsonGenerator(os, JsonEncoding.UTF8);
		} catch (IOException ex) {
			throw new JsonException(ex);
		}
	}

	public static JsonParser createJsonParser(InputStream in) throws JsonException {
		try {
			return jf.createJsonParser(in);
		} catch (IOException ex) {
			throw new JsonException(ex);
		}
	}

	public static JsonParser createJsonParser(String in) throws JsonException {
		try {
			return jf.createJsonParser(in);
		} catch (IOException ex) {
			throw new JsonException(ex);
		}
	}

	public static ObjectMapper getObjectMapper() {
		return JsonUtils.m;
	}

	public static void setObjectMapper(ObjectMapper objectMapper) {
		JsonUtils.m = objectMapper;
	}

	// adding cast for java compiler
	@SuppressWarnings("unchecked")
	public static <T> T fromJsonInto(String jsonAsString, T pojo) throws JsonException {
		try {
			return (T) m.readerForUpdating(pojo).readValue(jsonAsString);
		} catch (JsonProcessingException e) {
			throw new JsonException(e);
		} catch (IOException e) {
			throw new JsonException(e);
		}
	}

	@SuppressWarnings("unchecked")
	public static <T> T fromJsonInto(InputStream jsonStream, T pojo) throws JsonException {
		try {
			return (T) m.readerForUpdating(pojo).readValue(jsonStream);
		} catch (JsonProcessingException e) {
			throw new JsonException(e);
		} catch (IOException e) {
			throw new JsonException(e);
		}
	}

	@SuppressWarnings("unchecked")
	public static <T> T fromJsonIntoClone(String jsonAsString, T pojo) throws JsonException {
		Map<String, Object> incoming = JsonUtils.fromJsonToMap(jsonAsString);

		Map<String, Object> existing = JsonUtils.mapify(pojo);

		existing.putAll(incoming);

		T clone = (T) unmapify(existing, pojo.getClass());

		return clone;
	}

	// adding cast for java compiler
	@SuppressWarnings("unchecked")
	public static <T> T fromMapInto(Map<String, ?> map, T pojo) throws JsonException {
		try {
			String jsonAsString = JsonUtils.toJson(map);
			return (T) m.readerForUpdating(pojo).readValue(jsonAsString);
		} catch (JsonProcessingException e) {
			throw new JsonException(e);
		} catch (IOException e) {
			throw new JsonException(e);
		}
	}

	@SuppressWarnings("unchecked")
	public static <T> T fromMapIntoClone(Map<String, ?> map, T pojo) throws JsonException {
		String jsonAsString = JsonUtils.toJson(map);

		Map<String, Object> incoming = JsonUtils.fromJsonToMap(jsonAsString);

		Map<String, Object> existing = JsonUtils.mapify(pojo);

		existing.putAll(incoming);

		T clone = (T) unmapify(existing, pojo.getClass());

		return clone;
	}

}