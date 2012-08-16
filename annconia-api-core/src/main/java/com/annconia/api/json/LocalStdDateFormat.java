package com.annconia.api.json;

import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.codehaus.jackson.map.util.StdDateFormat;

/**
 * Simplified from org.codehaus.jackson.map.util.StdDateFormat
 * 
 * We'll either serialize dates as yyyy-MM-dd OR unix timestamp, the former by local timezone.
 * 
 * @author John Stuppy
 * @author Adam Scherer
 * 
 */
@SuppressWarnings("serial")
public class LocalStdDateFormat extends StdDateFormat {
	final static String DATE_FORMAT_STR_PLAIN = "yyyy-MM-dd";

	final static SimpleDateFormat DATE_FORMAT_PLAIN;

	static {
		DATE_FORMAT_PLAIN = new SimpleDateFormat(DATE_FORMAT_STR_PLAIN);
	}

	public final static LocalStdDateFormat instance = new LocalStdDateFormat();

	transient SimpleDateFormat _formatPlain;

	public LocalStdDateFormat() {
	}

	@Override
	public LocalStdDateFormat clone() {
		return new LocalStdDateFormat();
	}

	@Override
	protected Date parseAsISO8601(String dateStr, ParsePosition pos) {
		int len = dateStr.length();
		char c = dateStr.charAt(len - 1);
		SimpleDateFormat df;

		if (len <= 10 && Character.isDigit(c)) {
			df = _formatPlain;
			if (df == null) {
				df = _formatPlain = (SimpleDateFormat) DATE_FORMAT_PLAIN.clone();
			}
	        return df.parse(dateStr, pos);
		} else {
			return super.parseAsISO8601(dateStr, pos);
		}
	}
}
