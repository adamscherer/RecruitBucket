package com.annconia.security.entity;

import org.springframework.security.crypto.password.StandardPasswordEncoder;

public class PasswordUtility {

	private static final StandardPasswordEncoder ENCODER = new StandardPasswordEncoder("AS@$@#ADSF");

	public static String encodePassword(String rawPassword) {
		return ENCODER.encode(rawPassword);
	}

	public static boolean isValidPassword(String rawPassword, String encodedPassword) {
		return ENCODER.matches(rawPassword, encodedPassword);
	}

}
