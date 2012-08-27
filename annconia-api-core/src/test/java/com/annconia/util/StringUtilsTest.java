package com.annconia.util;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import org.junit.Test;

/**
 * 
 * @author jstuppy
 *
 */
public class StringUtilsTest {

	@Test
	public void stripTagsNull() {
		assertNull(StringUtils.stripTags(null));
	}

	@Test
	public void stripTagsEmpty() {
		assertEquals("", StringUtils.stripTags(""));
	}

	@Test
	public void stripTagsEmptyDiv() {
		assertEquals("", StringUtils.stripTags("<div></div>"));
	}

	@Test
	public void stripTagsEmptyComplicatedDiv() {
		assertEquals("", StringUtils.stripTags("<div><span><p></p></span></div>"));
	}

	@Test
	public void stripTagsEmptyBr() {
		assertEquals("", StringUtils.stripTags("<br />"));
	}

	@Test
	public void stripTagsSimpleDiv() {
		assertEquals("Hello World", StringUtils.stripTags("<div>Hello World</div>"));
	}

	@Test
	public void stripTagsComplicatedDiv() {
		assertEquals("!HelloWorldHowAreYou?", StringUtils.stripTags("!<div>Hello<span>World<p>How</p>Are</span>You</div>?"));
	}

	@Test
	public void formatYM() {
		assertEquals("archive/2011/3", String.format("archive/%d/%d", 2011, 4 - 1));
	}

	@Test
	public void testMakeSecureUrl() {
		String base_http_url = "http://deliveries.eatingwell.com/xml/images/recipe/50/SA4674.JPG";
		String base_https_url = "https://deliveries.eatingwell.com/xml/images/recipe/50/SA4674.JPG";

		String secureUrl = StringUtils.makeSecureUrl(base_http_url);
		String secureUrl2 = StringUtils.makeSecureUrl(base_https_url);

		assertEquals(true, secureUrl.startsWith("https"));
		assertEquals(secureUrl, base_https_url);
		assertEquals(secureUrl, secureUrl2);
	}

	@Test
	public void valueOf() {
		assertEquals("[A,B,C]", StringUtils.valueOf(Arrays.asList("A", "B", "C")));
		assertEquals("[A,B,C]", StringUtils.valueOf(new String[] { "A", "B", "C" }));
		assertEquals(null, StringUtils.valueOf(null));
		assertEquals("NULL!", StringUtils.valueOf(null, "NULL!"));
	}

	@Test
	public void testCommaSeparatedList() {
		List<String> nullList = null;
		String strNull = StringUtils.toCommaSeparatedString(nullList);
		assertNotNull(strNull);
		assertEquals(StringUtils.EMPTY, strNull);

		List<String> emptyList = new ArrayList<String>();
		String strEmpty = StringUtils.toCommaSeparatedString(emptyList);
		assertNotNull(strEmpty);
		assertEquals(StringUtils.EMPTY, strEmpty);

		List<String> onelist = new ArrayList<String>();
		onelist.add("test");
		String strOne = StringUtils.toCommaSeparatedString(onelist);
		assertNotNull(onelist);
		assertEquals("test", strOne);

		List<String> threelist = new ArrayList<String>();
		threelist.add("test");
		threelist.add("test");
		threelist.add("test");
		String strThree = StringUtils.toCommaSeparatedString(threelist);
		assertNotNull(threelist);
		assertEquals("test,test,test", strThree);
	}

	@Test
	public void testEnsureHasSuffix() {
		String suffix = ".xml";
		String sourceNoSuffix = "thisRecipe";
		String sourceHasSuffix = "thisRecipe.xml";

		String shouldHave = StringUtils.ensureHasSuffix(sourceNoSuffix, suffix);
		assertEquals(shouldHave, sourceHasSuffix);
		
		String shouldKeep = StringUtils.ensureHasSuffix(sourceHasSuffix, suffix);
		assertEquals(shouldKeep, sourceHasSuffix);
	}
	
	@Test
	public void replaceChars() {
		assertEquals("hello world how are you", StringUtils.replaceChars("hello {world} how {are} {you}", "{}", ""));
	}
	
	@Test
	public void capitalizeName() {
		assertEquals("Adam", StringUtils.titleCase("ADAM"));
		assertEquals("Adam", StringUtils.titleCase("adam"));
		assertEquals("Mary Jo", StringUtils.titleCase("MARY JO"));
	}
	
	@Test
	public void processHyphenatedString() {
		assertEquals("Tomato Basil Soup", StringUtils.prettyPrintHyphenatedValue("tomato-basil-soup"));
		assertEquals("Tomato Basil Soup", StringUtils.prettyPrintHyphenatedValue("TOMATO-BASIL-SOUP"));
		assertEquals("Tomato Basil Soup", StringUtils.prettyPrintHyphenatedValue("tOMATO-bASIL-sOUP"));
		assertEquals("Tomato Basil Soup", StringUtils.prettyPrintHyphenatedValue("     tomato-basil-soup    "));
	}
	
	// XXX move to TrackerDataTest
//	@Test
//	public void pattern() {
//		assertFalse(ExerciseTrackerData.isValidCustomExerciseName(null));
//		assertFalse(ExerciseTrackerData.isValidCustomExerciseName(""));
//
//		assertTrue(ExerciseTrackerData.isValidCustomExerciseName("hello world"));
//		assertTrue(ExerciseTrackerData.isValidCustomExerciseName("hello world 123-_"));
//		assertFalse(ExerciseTrackerData.isValidCustomExerciseName("hello world 123-_+"));
//		assertFalse(ExerciseTrackerData.isValidCustomExerciseName("hello,world 123-_"));
//	}
	
	@Test
	public void formatExerciseData() {
		Date d = new GregorianCalendar(1995, Calendar.MAY, 5).getTime();
		
		assertEquals(
				"Exercised 30 minutes and burned 50 Calories by participating in Running on 05/05/1995", 
				String.format("Exercised %1d minutes and burned %2d Calories by participating in %3s on %4$tm/%4$td/%4$tY", 30, 50, "Running", d)
			);
	}
	
	@Test
	public void formatServingsData() {
		Date d = new GregorianCalendar(1995, Calendar.MAY, 5).getTime();
		
		assertEquals(
				"Ate 3.0 fruits, 1.5 vegetables, and 8.5 water servings on 05/05/1995", 
				String.format("Ate %1.1f fruits, %2.1f vegetables, and %3.1f water servings on %4$tm/%4$td/%4$tY", 3d, 1.5d, 8.5d, d)
			);
	}
	
	// XXX move to StaticContentTest
//	@Test
//	public void formatStaticContent() {
//		StaticContent content = new StaticContent();
//		content.setText("Hello %s");
//		assertEquals("Hello World", content.print("World"));
//	}
	
	// XXX move to MessageCenterTest
//	@Test
//	public void validateFolderName() {
//		try {
//			assertEquals("123New Folder", MessageCenterController.validateFolderName("123New Folder"));
//		} catch (ValidationException e) {
//			fail();
//		}
//		
//		try {
//			assertEquals("123New Folder", MessageCenterController.validateFolderName("      123New Folder       "));
//		} catch (ValidationException e) {
//			fail();
//		}
//		
//		try {
//			MessageCenterController.validateFolderName("!");
//			fail();
//		} catch (ValidationException e) {
//		}
//		
//		try {
//			MessageCenterController.validateFolderName("123456789012345678901234567890A");
//			fail();
//		} catch (ValidationException e) {
//		}
//	}
	
	@Test
	public void toCharArray() {
		assertEquals(5, StringUtils.toCharArray("ABCDE").length);
	}
	
	@Test
	public void splitInto() {
		String s = "1234567890";

		assertArrayEquals(new String[] { "1", "2", "3", "4", "5", "6", "7", "8", "9", "0" }, StringUtils.split(s, 1));
		assertArrayEquals(new String[] { "123", "456", "789", "0" }, StringUtils.split(s, 3));
		assertArrayEquals(new String[] { "1234567890" }, StringUtils.split(s, 300));
	}
	
	@Test
	public void hasUpperCase() {
		assertFalse(StringUtils.hasUpperCase(null));
		assertFalse(StringUtils.hasUpperCase(""));
		assertFalse(StringUtils.hasUpperCase("abcd"));
		assertFalse(StringUtils.hasUpperCase("qwertyuiopasdfghjklzxcvbnm`1234567890-=~!@#$%^&*()_+[]{}|;':\",./<>?"));

		assertTrue(StringUtils.hasUpperCase("A"));
		assertTrue(StringUtils.hasUpperCase("A!@#$%^&*()_"));
	}
	
	@Test
	public void isUpperCase() {
		String str = "qwertyuiopasdfghjklzxcvbnm`1234567890-=~!@#$%^&*()_+[]{}|;':\",./<>?";
		
		for (int i = 0; i < str.length(); i++) {
			char c = str.charAt(i);
			
			assertFalse("Expected " + c + " to not be upper case", Character.isUpperCase(c));
		}
	}
	
	@Test
	public void htmlToText() {
		String html = "<div>&amp; you &lt;&gt;</div>";
		
		String text = StringUtils.htmlToText(html);
		
		assertEquals("& you <>", text);
	}
	
	
}
