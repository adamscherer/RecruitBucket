package com.annconia.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


/**
 * 
 * @author Adam Scherer
 *
 */
public class StringPluralizer {

	protected static final List<Rule> rules = new ArrayList<Rule>();
	protected static final Map<String, String> irregulars = new HashMap<String, String>();
	protected static final Set<String> uncountables = new HashSet<String>();

	static {
		/*
				rules.add(new Rule("(quiz)$", "$1zes"));
				rules.add(new Rule("^(ox)$", "$1en"));
				rules.add(new Rule("([m|l])ouse$", "$1ice"));
				rules.add(new Rule("(matr|vert|ind)ix|ex$", "$1ices"));
				rules.add(new Rule("(x|ch|ss|sh)$", "$1es"));
				rules.add(new Rule("([^aeiouy]|qu)y$", "$1ies"));
				rules.add(new Rule("([^aeiouy]|qu)ies$", "$1y"));
				rules.add(new Rule("(hive)$", "$1s"));
				rules.add(new Rule("(?:([^f])fe|([lr])f)$", "$1$2ves"));
				rules.add(new Rule("sis$", "ses"));
				rules.add(new Rule("([ti])um$", "$1a"));
				rules.add(new Rule("(buffal|tomat)o$", "$1oes"));
				rules.add(new Rule("(bu)s$", "$1ses"));
				rules.add(new Rule("(alias|status)$", "$1es"));
				rules.add(new Rule("(octop|vir)us$", "$1i"));
				rules.add(new Rule("is$", "es$"));
				//rules.add(new Rule("([*])s$", "$1"));
		*/
		rules.add(0, new Rule("$", "s"));
		rules.add(0, new Rule("s$", "s"));
		rules.add(0, new Rule("(ax|test)is$", "$1es"));
		rules.add(0, new Rule("(octop|vir)us$", "$1i"));
		rules.add(0, new Rule("(alias|status)$", "$1es"));
		rules.add(0, new Rule("(bu)s$", "$1ses"));
		rules.add(0, new Rule("(buffal|tomat)o$", "$1oes"));
		rules.add(0, new Rule("([ti])um$", "$1a"));
		rules.add(0, new Rule("sis$", "ses"));
		rules.add(0, new Rule("(?:([^f])fe|([lr])f)$", "$1$2ves"));
		rules.add(0, new Rule("(hive)$", "$1s"));
		rules.add(0, new Rule("([^aeiouy]|qu)y$", "$1ies"));
		rules.add(0, new Rule("(x|ch|ss|sh)$", "$1es"));
		rules.add(0, new Rule("(matr|vert|ind)(?:ix|ex)$", "$1ices"));
		rules.add(0, new Rule("([m|l])ouse$", "$1ice"));
		rules.add(0, new Rule("^(ox)$", "$1en"));
		rules.add(0, new Rule("(quiz)$", "$1zes"));

		irregulars.put("move", "moves");
		irregulars.put("sex", "sexes");
		irregulars.put("child", "children");
		irregulars.put("man", "men");
		irregulars.put("person", "people");

		uncountables.add("sheep");
		uncountables.add("fish");
		uncountables.add("deer");
		uncountables.add("series");
		uncountables.add("species");
		uncountables.add("money");
		uncountables.add("rice");
		uncountables.add("information");
		uncountables.add("equipment");
	}

	public static String conditionallyPluralize(String s, int count) {
		if (Math.abs(count) != 1) {
			return pluralize(s);
		}

		return s;
	}

	public static String conditionallyPluralize(String s, long count) {
		if (Math.abs(count) != 1) {
			return pluralize(s);
		}

		return s;
	}

	public static String conditionallyPluralize(String s, float count) {
		if (Math.abs(count) != 1) {
			return pluralize(s);
		}

		return s;
	}

	public static String conditionallyPluralize(String s, double count) {
		if (Math.abs(count) != 1) {
			return pluralize(s);
		}

		return s;
	}

	public static String pluralize(String s) {
		if (StringUtils.isEmpty(s)) {
			return s;
		}

		String lower = s.toLowerCase();

		// save some time in the case that singular and plural are the same
		if (uncountables.contains(lower)) {
			return lower;
		}

		// check for irregular singular forms
		String irregularPlural = irregulars.get(lower);
		if (StringUtils.isNotEmpty(irregularPlural)) {
			return irregularPlural;
		}

		// check for matches using regular expressions
		for (Rule rule : rules) {
			Matcher m = rule.pattern.matcher(s);
			if (m.find()) {
				return m.replaceFirst(rule.replacement);
			}
		}

		return s + "s";
	}

	protected static class Rule {

		/**
		 * Defaults to case-insensitive 
		 * @param pattern
		 * @param replacement
		 */
		protected Rule(String pattern, String replacement) {
			this.pattern = Pattern.compile(pattern, Pattern.CASE_INSENSITIVE);
			this.replacement = replacement;
		}

		protected Rule(Pattern pattern, String replacement) {
			this.pattern = pattern;
			this.replacement = replacement;
		}

		Pattern pattern;
		String replacement;
	}
}
