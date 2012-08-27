package com.annconia.util;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.PriorityQueue;
import java.util.SortedSet;
import java.util.TreeSet;

import org.junit.Assert;
import org.junit.Test;

/**
 * 
 * @author jstuppy
 *
 */
public class CollectionUtilsTest {

	@Test
	public void split() {
		List<String> members = new ArrayList<String>();
		for (int i = 0; i < 18; i++) {
			members.add("Test");
		}
		List<List<String>> groups = CollectionUtils.split(members, 5);
		assertEquals(true, groups.size() == 4);
	}

	@Test
	public void splitEmpty() {
		List<String> members = new ArrayList<String>();
		List<List<String>> groups = CollectionUtils.split(members, 5);
		assertEquals(true, groups.size() == 0);
	}

	@Test
	public void getElementCollection() {
		SortedSet<String> letters = new TreeSet<String>();

		letters.addAll(Arrays.asList("X", "Y", "B", "A", "C", "Z", "J"));

		assertEquals("A", CollectionUtils.getFirstElement(letters));
		assertEquals("Z", CollectionUtils.getLastElement(letters));
		assertNotNull(CollectionUtils.getRandomElement(letters));
		assertNull(CollectionUtils.getElementAt(letters, 900));

		assertEquals("Z", CollectionUtils.getCircularElementAt(letters, -1));
	}

	@Test
	public void subList() {
		int MAX_SIZE = 3;

		List<String> emptyCollection = new ArrayList<String>();

		List<String> smallCollection = new ArrayList<String>();
		smallCollection.add("1");
		smallCollection.add("2");

		List<String> mediumCollection = new ArrayList<String>();
		mediumCollection.add("1");
		mediumCollection.add("2");
		mediumCollection.add("3");
		mediumCollection.add("4");

		List<String> result = CollectionUtils.safeSubList(emptyCollection, MAX_SIZE);
		assertNotNull(result);
		assertEquals(result.size(), 0);

		result = CollectionUtils.safeSubList(smallCollection, MAX_SIZE);
		assertNotNull(result);
		assertEquals(result.size(), 2);

		result = CollectionUtils.safeSubList(mediumCollection, MAX_SIZE);
		assertNotNull(result);
		assertEquals(result.size(), MAX_SIZE);
	}
	
	@Test
	public void cast() {
		class A {
			
		}
		
		class B extends A {
			
		}
		
		List<B> list = Arrays.asList(new B(), new B(), new B());
		
		List<A> casted = CollectionUtils.cast(list, A.class);
		
		Assert.assertArrayEquals(list.toArray(), casted.toArray());
	}
	
	@Test
	public void priorityQueue() {
		PriorityQueue<Integer> queue = new PriorityQueue<Integer>();

		queue.add(5);
		queue.add(4);
		queue.add(3);
		queue.add(2);
		queue.add(1);

		assertEquals(1, queue.poll().intValue());
		assertEquals(2, queue.poll().intValue());
		assertEquals(3, queue.poll().intValue());
		assertEquals(4, queue.poll().intValue());
		assertEquals(5, queue.poll().intValue());
		
		assertNull(queue.poll());
	}
	
	@Test
	public void priorityQueueAddAll() {
		PriorityQueue<Integer> queue = new PriorityQueue<Integer>();

		queue.addAll(Arrays.asList(5,4,3,2,1));

		assertEquals(1, queue.poll().intValue());
		assertEquals(2, queue.poll().intValue());
		assertEquals(3, queue.poll().intValue());
		assertEquals(4, queue.poll().intValue());
		assertEquals(5, queue.poll().intValue());
		
		assertNull(queue.poll());
	}
	
	@Test
	public void stripNull() {
		String[] strings = new String[] {
				"one", null, "two", "three", null, "four", null, null
		};
		
		strings = CollectionUtils.stripNull(strings);
		
		assertArrayEquals(new String[]{ "one", "two", "three", "four" }, strings);
	}
	

}
