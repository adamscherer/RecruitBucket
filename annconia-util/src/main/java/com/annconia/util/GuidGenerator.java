package com.annconia.util;

import java.util.UUID;

/**
 * Static class that is used to generate unique id's for all database records.  Delegate to
 * Java's UUID implementation.
 * 
 * Additionally, entities can be prefixed to enable entity type from an entity GUID.
 * 
 * @author Adam Scherer
 *
 */
public final class GuidGenerator {

	public static String generate() {
		return generate(null);
	}

	public static String generate(String prefix) {
		String uuid = UUID.randomUUID().toString().replaceAll("-", "");

		if (StringUtils.isBlank(prefix)) {
			return uuid;
		}
		return constructGuidPrefix(prefix) + uuid;
	}
	
	protected static boolean startsWith(String guid, String prefix) {
		if (StringUtils.isBlank(guid) || StringUtils.isBlank(prefix)) {
			return false;
		}
		
		return StringUtils.startsWith(guid, constructGuidPrefix(prefix));
	}
	
	public static final String PREFIX_SEPARATOR = "_";
	protected static String constructGuidPrefix(String prefix) {
		return StringUtils.isNotBlank(prefix) ? StringUtils.trimToEmpty(prefix) + PREFIX_SEPARATOR : StringUtils.EMPTY;
	}
}
