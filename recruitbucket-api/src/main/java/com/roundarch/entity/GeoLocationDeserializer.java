package com.roundarch.entity;

import java.io.IOException;

import org.codehaus.jackson.JsonParser;
import org.codehaus.jackson.JsonProcessingException;
import org.codehaus.jackson.JsonToken;
import org.codehaus.jackson.map.DeserializationContext;
import org.codehaus.jackson.map.JsonDeserializer;

public class GeoLocationDeserializer extends JsonDeserializer<GeoLocation> {

	@Override
	public GeoLocation deserialize(JsonParser jp, DeserializationContext ctxt) throws IOException, JsonProcessingException {
		JsonToken t = jp.getCurrentToken();
		try {
			if (t == JsonToken.START_ARRAY) {
				return null;
			}

			throw ctxt.mappingException(GeoLocation.class);
		} catch (Throwable iae) {
			throw ctxt.weirdStringException(GeoLocation.class, "not a valid representation (error: " + iae.getMessage() + ")");
		}
	}

}
