package com.annconia.util;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotSame;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.junit.Test;

public class ObjectUtilsTest {

	@Test
	public void equals() {
		A a1 = new A();
		A a2 = new A();
		
		assertNotSame(a1, a2);
		assertFalse(a1.equals(a2));
		assertFalse(a2.equals(a1));

		assertEquals(a1, a1);
		assertEquals(a2, a2);
		assertSame(a2, a2);
		assertSame(a2, a2);
	}
	
	@Test
	public void contains() {
		A a1 = new A();
		A a2 = new A();
		
		List<A> list = new ArrayList<A>();
		
		list.add(a1);

		assertTrue(list.contains(a1));
		assertFalse(list.contains(a2));
	}
	
	
	static class A {
		private String s;
		private int i;
		private long l;
		private Date d;
		
		public String getS() {
			return s;
		}
		public void setS(String s) {
			this.s = s;
		}
		public int getI() {
			return i;
		}
		public void setI(int i) {
			this.i = i;
		}
		public long getL() {
			return l;
		}
		public void setL(long l) {
			this.l = l;
		}
		public Date getD() {
			return d;
		}
		public void setD(Date d) {
			this.d = d;
		}
	}
	
	static class B extends A {
		private float f;

		public float getF() {
			return f;
		}

		public void setF(float f) {
			this.f = f;
		}
	}
	
}
