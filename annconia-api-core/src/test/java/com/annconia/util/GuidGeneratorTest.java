package com.annconia.util;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.HashSet;
import java.util.Set;

import org.junit.Test;

public class GuidGeneratorTest {

	@Test
	public void generate() {
		String guid = GuidGenerator.generate();
		
		assertEquals(32, guid.length());
	}
	
	@Test
	public void generateMany() {
		int num = 100;
		
		Set<String> guids = new HashSet<String>();
		
		for (int i = 0; i < num; i++) {
			String guid = GuidGenerator.generate();
			boolean isNew = guids.add(guid);
			assertTrue("Expecting GUID #" + (i + 1) + " (" + guid + ") to be new", isNew);
		}
	}
	
	@Test
	public void generatePrefix() {
		String prefix = "test";
		String guid = GuidGenerator.generate(prefix);
		
		assertTrue(GuidGenerator.startsWith(guid, prefix));
		assertEquals(32 + prefix.length() + GuidGenerator.PREFIX_SEPARATOR.length(), guid.length());
	}
	
	@Test
	public void generateAndPrintGuids() {
		int num = 10;
		
		if (num <= 0) {
			return;
		}
		
		System.out.println("Generating " + num + " GUIDs");
		
		for (int i = 0; i < num; i++) {
			System.out.println( "  " + GuidGenerator.generate() );
		}
	}
	
}
