package com.annconia.util;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.fail;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * 
 * @author jstuppy
 *
 */
public class NumberUtilsTest {
	
	protected Number number;
	
	@Before
	public void setUp() {
		number = mock(Number.class);
	}
	
	@After
	public void tearDown() {
		number = null;
	}
	
	@Test
	public void castByte() {
		when(number.byteValue()).thenReturn((byte) 1);
		
		assertEquals(Byte.valueOf((byte) 1), NumberUtils.cast(number, Byte.class));
	}
	
	@Test
	public void castDouble() {
		when(number.doubleValue()).thenReturn((double) 1);
		
		assertEquals(Double.valueOf((double) 1), NumberUtils.cast(number, Double.class));
	}
	
	@Test
	public void castFloat() {
		when(number.floatValue()).thenReturn((float) 1);
		
		assertEquals(Float.valueOf((float) 1), NumberUtils.cast(number, Float.class));
	}
	
	@Test
	public void castInteger() {
		when(number.intValue()).thenReturn((int) 1);
		
		assertEquals(Integer.valueOf((int) 1), NumberUtils.cast(number, Integer.class));
	}
	
	@Test
	public void castLong() {
		when(number.longValue()).thenReturn(1l);
		
		assertEquals(Long.valueOf(1l), NumberUtils.cast(number, Long.class));
	}
	
	@Test
	public void castShort() {
		when(number.shortValue()).thenReturn((short) 1);
		
		assertEquals(Short.valueOf((short) 1), NumberUtils.cast(number, Short.class));
	}
	
	@Test
	public void castBigDecimal() {
		when(number.toString()).thenReturn("1");
		
		assertEquals(new BigDecimal("1"), NumberUtils.cast(number, BigDecimal.class));
	}
	
	@Test
	public void castNumberThrowsException() {
		try {
			NumberUtils.cast(number, Number.class);
			fail("Expected RuntimeException due to number superclass");
		} catch (RuntimeException ex) {
			assertNotNull(ex);
		}
	}
	
	@Test
	public void castUnknownThrowsException() {
		try {
			NumberUtils.cast(number, Unknown.class);
			fail("Expected RuntimeException due to unknown class");
		} catch (RuntimeException ex) {
			assertNotNull(ex);
		}
	}
	
	@Test
	public void castNullReturnsNull() {
		assertNull(NumberUtils.cast(null, Number.class));
	}
	
	@SuppressWarnings("unchecked")
	@Test
	public void classToClassCast() {
		Class<?> clazz = Long.class;
		
		assertNotNull((Class<Number>) clazz);
	}
	
	@SuppressWarnings("unchecked")
	@Test
	public void castLongToNumber() {
		Class<?> clazz = Long.class;
		
		when(number.longValue()).thenReturn(1l);
		
		assertEquals(Long.valueOf(1l), NumberUtils.cast(number, (Class<Number>) clazz));
	}
	
	@Test
	public void rollover() {
		assertEquals(0, NumberUtils.rollover(0, 0, 0));
		assertEquals(0, NumberUtils.rollover(0, 0, 10));
		assertEquals(10, NumberUtils.rollover(0, 1, 10));

		assertEquals(7, NumberUtils.rollover(-7, 1, 7));
		assertEquals(1, NumberUtils.rollover(-6, 1, 7));
		assertEquals(2, NumberUtils.rollover(-5, 1, 7));
		assertEquals(3, NumberUtils.rollover(-4, 1, 7));
		assertEquals(4, NumberUtils.rollover(-3, 1, 7));
		assertEquals(5, NumberUtils.rollover(-2, 1, 7));
		assertEquals(6, NumberUtils.rollover(-1, 1, 7));
		assertEquals(7, NumberUtils.rollover(0, 1, 7));
		assertEquals(1, NumberUtils.rollover(1, 1, 7));
		assertEquals(2, NumberUtils.rollover(2, 1, 7));
		assertEquals(3, NumberUtils.rollover(3, 1, 7));
		assertEquals(4, NumberUtils.rollover(4, 1, 7));
		assertEquals(5, NumberUtils.rollover(5, 1, 7));
		assertEquals(6, NumberUtils.rollover(6, 1, 7));
		assertEquals(7, NumberUtils.rollover(7, 1, 7));
		assertEquals(1, NumberUtils.rollover(8, 1, 7));
		assertEquals(2, NumberUtils.rollover(9, 1, 7));
		assertEquals(3, NumberUtils.rollover(10, 1, 7));
		assertEquals(4, NumberUtils.rollover(11, 1, 7));
		assertEquals(5, NumberUtils.rollover(12, 1, 7));
		assertEquals(6, NumberUtils.rollover(13, 1, 7));
		assertEquals(7, NumberUtils.rollover(14, 1, 7));
	}
	
	private static class Unknown extends Number {

		private static final long serialVersionUID = 9147331387848177618L;

		@Override
		public double doubleValue() {
			return 0;
		}

		@Override
		public float floatValue() {
			return 0;
		}

		@Override
		public int intValue() {
			return 0;
		}

		@Override
		public long longValue() {
			return 0;
		}
		
	}
}
