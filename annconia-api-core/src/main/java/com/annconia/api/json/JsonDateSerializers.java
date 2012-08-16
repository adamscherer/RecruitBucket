package com.annconia.api.json;

import java.io.IOException;
import java.util.Date;

import org.apache.commons.lang.time.DateFormatUtils;
import org.codehaus.jackson.JsonGenerator;
import org.codehaus.jackson.JsonProcessingException;
import org.codehaus.jackson.map.JsonSerializer;
import org.codehaus.jackson.map.SerializerProvider;

public class JsonDateSerializers {

	public static class SmtpDateTimeSerializer extends JsonSerializer<Date> {
		
		@Override
		public void serialize(Date value, JsonGenerator jgen, SerializerProvider provider) throws IOException, JsonProcessingException {
			if (value != null) {
				jgen.writeString(DateFormatUtils.SMTP_DATETIME_FORMAT.format(value));
			} else {
				jgen.writeNull();
			}
		}
		
	}
}
