package com.annconia.api.json;

import java.io.IOException;

import org.codehaus.jackson.JsonParser;
import org.codehaus.jackson.JsonProcessingException;
import org.codehaus.jackson.map.DeserializationContext;
import org.codehaus.jackson.map.JsonDeserializer;
import org.codehaus.jackson.map.annotate.JsonDeserialize;

import com.annconia.api.PagingAndSorting;
import com.annconia.api.json.DefaultProfileMixin.DefaultProfileDeserializer;

@JsonDeserialize(using = DefaultProfileDeserializer.class)
public class DefaultProfileMixin {

	static class DefaultProfileDeserializer extends JsonDeserializer<PagingAndSorting> {

		@Override
		public PagingAndSorting deserialize(JsonParser jp, DeserializationContext ctxt) throws IOException, JsonProcessingException {

			return null;
		}

	}
}
