package com.annconia.util;

import java.util.Properties;

import org.springframework.beans.factory.config.PropertyPlaceholderConfigurer;

/**
 * <strong>
 *   Delegates to ApplicationConfiguration for properties.<br>
 *   Any properties defined for this configurer will be ignored!
 * </strong>
 * 
 * @author Adam Scherer
 *
 */
public class ConfigurationPropertyPlaceholderConfigurer extends PropertyPlaceholderConfigurer {

	/**
	 * @param placeholder property key
	 * @param props ignored properties b/c ApplicationConfiguration is used as sole properties source
	 * @param systemPropertiesMode ignored b/c ApplicationConfiguration is used as sole properties source
	 */
	protected String resolvePlaceholder(String placeholder, Properties props, int systemPropertiesMode) {
		return resolvePlaceholder(placeholder, props);
	}

	/**
	 * @param placeholder property key
	 * @param props ignored properties b/c ApplicationConfiguration is used as sole properties source
	 */
	protected String resolvePlaceholder(String placeholder, Properties props) {
		return super.resolvePlaceholder(placeholder, props);
	}

}
