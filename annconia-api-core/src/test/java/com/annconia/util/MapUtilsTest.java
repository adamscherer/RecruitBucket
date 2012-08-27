package com.annconia.util;

import static org.junit.Assert.assertEquals;

import java.util.HashMap;
import java.util.Map;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class MapUtilsTest {
	
	protected Map<String, Object> map;
	
	@Before
	public void setUp() {
		map = new HashMap<String, Object>();

		map.put("string", "Hello World");
		map.put("short", (short) 111);
		map.put("integer", (int) 222);
		map.put("long", 333L);
		map.put("double", 123.456);
		map.put("boolean", true);
	}
	
	@After
	public void tearDown() {
		map = null;
	}
	
	@Test
	public void getString() {
		assertEquals("Hello World", MapUtils.getString(map, "string"));
	}
	
	@Test
	public void getStringNotFound() {
		assertEquals(null, MapUtils.getString(map, "!"));
	}
	
	@Test
	public void getInteger() {
		assertEquals(222, MapUtils.getInt(map, "integer"));
	}
	
	@Test
	public void getIntegerNotFound() {
		assertEquals(0, MapUtils.getInt(map, "!"));
	}
	
	@Test
	public void getDouble() {
		assertEquals(123.456, MapUtils.getDouble(map, "double"), 0.000001);
	}
	
}
