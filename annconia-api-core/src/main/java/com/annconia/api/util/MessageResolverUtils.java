package com.annconia.api.util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;

import org.springframework.context.MessageSourceResolvable;
import org.springframework.context.NoSuchMessageException;
import org.springframework.validation.ObjectError;

import com.annconia.util.CollectionUtils;
import com.annconia.util.StringUtils;

public class MessageResolverUtils {

	public static String resolveMessage(String key) {
		if (RequestContextHolder.get() == null) {
			return "";
		}

		return RequestContextHolder.get().getMessage(key, new Object[] {});
	}

	public static String resolveMessage(String key, String defaultMessage, Object... args) {
		if (RequestContextHolder.get() == null) {
			return "";
		}

		return RequestContextHolder.get().getMessage(key, new Object[] {}, defaultMessage);
	}

	public static String resolveMessage(final ObjectError error) {
		if (RequestContextHolder.get() == null) {
			return StringUtils.nullAsEmpty(error.getDefaultMessage());
		}

		MessageSourceResolvable resolvable = new MessageSourceResolvable() {

			public String getDefaultMessage() {
				return error.getDefaultMessage();
			}

			private String[] codes;

			public String[] getCodes() {
				if (codes == null) {
					List<String> list = new ArrayList<String>(Arrays.asList(error.getCodes()));

					// go backwards so we don't have to jump forward later
					for (int i = list.size() - 1; i >= 0; i--) {
						String code = list.get(i);
						if (StringUtils.hasUpperCase(code)) {
							String lowered = StringUtils.lowerCase(code);

							list.add(i + 1, lowered);
						}
					}

					codes = list.toArray(new String[0]);
				}

				return codes;
			}

			public Object[] getArguments() {
				return error.getArguments();
			}
		};

		try {
			return RequestContextHolder.get().getTheme().getMessageSource().getMessage(resolvable, Locale.getDefault());
		} catch (NoSuchMessageException ex) {
			String code = CollectionUtils.getLastElement(resolvable.getCodes());

			if (StringUtils.isNotBlank(code)) {
				return code;
			} else {
				return null;
			}
		}
	}
}
