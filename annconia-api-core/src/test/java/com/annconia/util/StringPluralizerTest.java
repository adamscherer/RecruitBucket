package com.annconia.util;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

/**
 * 
 * @author jstuppy
 *
 */
public class StringPluralizerTest {

	@Test
	public void testSimple() {
		assertEquals("quizzes", StringPluralizer.conditionallyPluralize("quiz", 2));
		assertEquals("cups", StringPluralizer.conditionallyPluralize("cup", 2));
		assertEquals("cup", StringPluralizer.conditionallyPluralize("cup", 1));
		assertEquals("cups", StringPluralizer.conditionallyPluralize("cup", 0));
		assertEquals("ellipses", StringPluralizer.pluralize("ellipsis"));
	}
}
