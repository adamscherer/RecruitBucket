package com.annconia.util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.NavigableSet;
import java.util.Random;

import org.apache.commons.collections.iterators.ReverseListIterator;

/**
 * 
 * @author Adam Scherer
 *
 */
public class CollectionUtils extends org.apache.commons.collections.CollectionUtils {

	private static final Random random = new Random(System.currentTimeMillis());

	public static <O> O getFirstElement(Collection<O> c) {
		return getElementAt(c, 0);
	}

	public static <O> O getLastElement(Collection<O> c) {
		return getElementAt(c, safeSize(c) - 1);
	}

	public static <O> O getRandomElement(Collection<O> c) {
		return getElementAt(c, random.nextInt(safeSize(c)));
	}

	@SuppressWarnings("unchecked")
	public static <O> O getElementAt(Collection<O> c, int index) {
		if (isEmpty(c)) {
			return null;
		}

		try {
			return (O) get(c, index);
		} catch (Throwable ex) {
			return null;
		}
	}

	public static <O> O getCircularElementAt(Collection<O> c, int index) {
		if (index < 0) {
			index += safeSize(c);
		}

		return getElementAt(c, index);
	}

	public static <O> O getFirstElement(List<O> l) {
		if (CollectionUtils.isEmpty(l)) {
			return null;
		}
		return l.get(0);
	}

	public static <O> O getLastElement(List<O> l) {
		if (CollectionUtils.isEmpty(l)) {
			return null;
		}

		int index = l.size() - 1;
		return l.get(index);
	}

	public static <O> O getRandomElement(List<O> l) {
		if (CollectionUtils.isEmpty(l)) {
			return null;
		}

		if (l.size() == 1) {
			return l.get(0);
		}

		int index = random.nextInt(l.size());

		return l.get(index);
	}

	public static <O> O getFirstElement(O[] a) {
		return getElementAt(a, 0);
	}

	public static <O> O getLastElement(O[] a) {
		return getElementAt(a, safeSize(a) - 1);
	}

	public static <O> O getRandomElement(O[] a) {
		return getElementAt(a, random.nextInt(safeSize(a)));
	}

	@SuppressWarnings("unchecked")
	public static <O> O getElementAt(O[] a, int index) {
		if (isEmpty(a)) {
			return null;
		}

		try {
			return (O) get(a, index);
		} catch (Throwable ex) {
			return null;
		}
	}

	public static <O> List<O> sort(List<O> collection, Comparator<? super O> comparator) {
		return sort(collection, comparator, false);
	}

	public static <O> List<O> sort(List<O> collection, Comparator<? super O> comparator, boolean clone) {
		if (collection == null)
			return collection;

		if (clone) {
			collection = new ArrayList<O>(collection);
		}

		Collections.sort(collection, comparator);
		return collection;
	}

	public static <O> List<O> sort(Collection<O> collection, Comparator<? super O> comparator) {
		if (collection == null)
			return null;

		return sort(new ArrayList<O>(collection), comparator);
	}

	public static <O> boolean isEmpty(O[] arr) {
		return arr == null || arr.length == 0;
	}

	public static <O> boolean isNotEmpty(O[] arr) {
		return !CollectionUtils.isEmpty(arr);
	}

	public static <O> boolean isSingular(O[] arr) {
		return CollectionUtils.safeSize(arr) == 1;
	}

	public static <O> boolean isSingular(Collection<O> c) {
		return CollectionUtils.safeSize(c) == 1;
	}
	
	public static <O> boolean isNotSingular(O[] arr) {
		return ! isSingular(arr);
	}

	public static <O> boolean isNotSingular(Collection<O> c) {
		return ! isSingular(c);
	}
	
	public static <O> List<O> asList(O... a) {
		return a == null ? new ArrayList<O>() : Arrays.asList(a);
	}

	public static <O> List<List<O>> split(List<O> l, int targetSize) {
		List<List<O>> groups = new ArrayList<List<O>>();
		for (int i = 0; i < l.size(); i += targetSize) {
			groups.add(l.subList(i, Math.min(i + targetSize, l.size())));
		}
		return groups;
	}

	/**
	 * Used for dividing an original list into two lists.
	 * 
	 * @param <T>
	 * @param list
	 * @param i
	 * @return
	 */
	public static <T> List<T> divide(List<T> list, int i) {
		List<T> x = new ArrayList<T>(list.subList(i, list.size()));
		// Remove items from end of original list
		for (int j = list.size() - 1; j >= i; --j) {
			list.remove(j);
		}

		return x;
	}

	/**
	 * Used to return list1 - list2
	 * 
	 */
	public static <T> List<T> subtract(List<T> list1, List<T> list2) {
		try {
			if (list1 == null || list1.isEmpty())
				return list1;

			if (list2 == null || list2.isEmpty())
				return list1;

			List<T> x = new ArrayList<T>();

			for (T o : list1) {
				if (!list2.contains(o)) {
					x.add(o);
				}
			}

			return x;
		} catch (Throwable ex) {
			return list1;
		}
	}

	public static <O> O safeGet(List<O> l, int index) {
		try {
			return l.get(index);
		} catch (Throwable ex) {
			return null;
		}
	}

	public static <O> O safeRemove(List<O> l, int index) {
		try {
			return l.remove(index);
		} catch (Throwable ex) {
			return null;
		}
	}

	public static <O> boolean safeAdd(Collection<O> c, O item) {
		try {
			return c.add(item);
		} catch (Throwable ex) {
			return false;
		}
	}

	public static <O> boolean safeAddAll(Collection<O> c, Collection<? extends O> items) {
		try {
			return c.addAll(items);
		} catch (Throwable ex) {
			return false;
		}
	}

	public static <O, P extends O> boolean safeAddAll(Collection<O> c, P[] items) {
		try {
			for (O item : items) {
				c.add(item);
			}

			return true;
		} catch (Throwable ex) {
			return false;
		}
	}

	public static <O> List<O> safeSubList(List<O> l, int size) {
		try {
			if (l.size() < size) {
				return l;
			}

			return l.subList(0, size);
		} catch (Throwable ex) {
			return l;
		}
	}

	public static <O> List<O> safeSubList(List<O> l, int offset, int count) {
		if (l == null) {
			return null;
		}

		if (offset >= l.size())
			return Collections.emptyList();

		if (count == 0)
			count = l.size();

		return l.subList(offset, Math.min(offset + count, l.size()));
	}

	public static int safeSize(Object[] a) {
		return a != null ? a.length : 0;
	}

	public static int safeSize(Collection<?> c) {
		return c != null ? c.size() : 0;
	}

	public static int safeSize(Object object) {
		try {
			return CollectionUtils.size(object);
		} catch (Throwable ex) {
			return 0;
		}
	}
	
	protected static <O> boolean equals(O lhs, O rhs) {
		if (lhs == rhs) {
			return true;
		} else if (lhs != null && lhs.equals(rhs)) {
			return true;
		} else if (rhs != null && rhs.equals(lhs)) {
			return true;
		} else {
			return false;
		}
	}

	public static <O> boolean contains(O[] a, O e) {
		if (CollectionUtils.isEmpty(a)) {
			return false;
		}

		for (O i : a) {
			if (equals(i, e)) {
				return true;
			}
		}

		return false;
	}

	public static <O> boolean doesNotContain(O[] a, O e) {
		return !contains(a, e);
	}

	public static <O> boolean contains(Collection<O> c, O e) {
		if (CollectionUtils.isEmpty(c)) {
			return false;
		}

		return c.contains(e);
	}

	public static <O> boolean doesNotContain(Collection<O> c, O e) {
		return !contains(c, e);
	}

	public static <O> boolean contains(Collection<O> c, O e, Comparator<O> comparator) {
		if (CollectionUtils.isNotEmpty(c)) {
			for (O item : c) {
				try {
					if (comparator.compare(item, e) == 0) {
						return true;
					}
				} catch (Throwable ex) {
					// assume not equals
				}
			}
		}

		return false;
	}
	
	public static <O> boolean containsAll(Collection<O> c, Collection<O> i) {
		if (c == null || i == null) {
			return false;
		}
		
		for (O obj : i) {
			if (doesNotContain(c, obj)) {
				return false;
			}
		}
		
		return true;
	}
	
	public static <O> boolean doesNotContainAll(Collection<O> c, Collection<O> i) {
		if (c == null || i == null) {
			return false;
		}
		
		return ! containsAll(c, i);
	}

	public static <O> boolean doesNotContain(Collection<O> c, O e, Comparator<O> comparator) {
		return !contains(c, e, comparator);
	}

	public static <O> List<O> clone(Collection<O> c) {
		return c == null ? null : new ArrayList<O>(c);
	}

	public static <O> List<O> cloneToEmpty(Collection<O> l) {
		return l == null ? new ArrayList<O>() : clone(l);
	}

	public static <O> List<O> nullAsEmpty(List<O> l) {
		return l == null ? new ArrayList<O>() : l;
	}

	public static <O> Collection<O> nullAsEmpty(Collection<O> c) {
		return c == null ? new ArrayList<O>() : c;
	}

	public static <O> O[] nullAsEmpty(O[] a, O[] empty) {
		return a == null ? empty : a;
	}

	public static <O> List<O> emptyAsNull(List<O> l) {
		return isEmpty(l) ? null : l;
	}

	public static <O> O[] emptyAsNull(O[] a) {
		return isEmpty(a) ? null : a;
	}

	public static <O> boolean toggleExistence(Collection<O> c, O e, boolean toggle) {
		if (toggle) {
			if (CollectionUtils.doesNotContain(c, e)) {
				return c.add(e);
			}
		} else {
			if (CollectionUtils.contains(c, e)) {
				return c.remove(e);
			}
		}

		return false;
	}

	public static <O> List<O> concat(Collection<? extends O>... collections) {
		return concatInto(new ArrayList<O>(), collections);
	}

	public static <O, C extends Collection<O>> C concatInto(C concatInto, Collection<? extends O>... collections) {
		if (CollectionUtils.isNotEmpty(collections)) {
			for (Collection<? extends O> collection : collections) {
				CollectionUtils.safeAddAll(concatInto, collection);
			}
		}

		return concatInto;
	}

	public static <O> List<O> cast(Collection<? extends O> c, Class<O> clazz) {
		if (c == null) {
			return null;
		}

		List<O> casted = new ArrayList<O>();

		CollectionUtils.safeAddAll(casted, c);

		return casted;
	}

	@SuppressWarnings("unchecked")
	public static <O> List<O> convert(List<?> list, Class<O> clazz) {
		if (list == null) {
			return null;
		}

		List<O> converted = new ArrayList<O>();

		for (Object item : list) {
			if (clazz.isInstance(item)) {
				converted.add((O) item);
			}
		}

		return converted;
	}

	public static <O> void pruneAllThatDoNotExist(final Collection<O> source, Collection<O> subset) {
		if (CollectionUtils.isEmpty(subset)) {
			return;
		}

		if (CollectionUtils.isEmpty(source)) {
			subset.clear();
			return;
		}

		for (Iterator<O> iter = subset.iterator(); iter.hasNext();) {
			O item = iter.next();

			if (CollectionUtils.doesNotContain(source, item)) {
				iter.remove();
			}
		}
	}

	public static <INPUT, OUTPUT> List<OUTPUT> transform(final Collection<? extends INPUT> source, Transformer<INPUT, OUTPUT> transformer) {
		if (source == null) {
			return null;
		}

		if (CollectionUtils.isEmpty(source)) {
			return new ArrayList<OUTPUT>();
		}

		List<OUTPUT> output = new ArrayList<OUTPUT>();
		for (INPUT input : source) {
			output.add(transformer.transform(input));
		}

		return output;
	}
	
	public static <INPUT, OUTPUT> List<OUTPUT> transformAndReduce(final Collection<INPUT> source, Transformer<INPUT, OUTPUT> transformer) {
		List<OUTPUT> output = transform(source, transformer);
		
		return stripNull(output);
	}
	
	public static <T> Collection<T> each(Collection<T> c, Statement<? super T> statement) {
		if (isEmpty(c) || statement == null) {
			return c;
		}
		
		Iterator<T> iter = c.iterator();
		
		while (iter.hasNext()) {
			T item = iter.next();
			statement.run(item);
		}
		
		return c;
	}
	
	public static <T> Collection<T> each(Collection<T> c, Collection<Statement<? super T>> statements) {
		if (isEmpty(c) || isEmpty(statements)) {
			return c;
		}
		
		Iterator<T> iter = c.iterator();
		
		while (iter.hasNext()) {
			T item = iter.next();
			for (Statement<? super T> statement : statements) {
				if (statement != null) {
					statement.run(item);
				}
			}
		}
		
		return c;
	}
	
	public static <T> Collection<T> each(Collection<T> c, Statement<? super T>... statements) {
		return each(c, Arrays.asList(statements));
	}
	
	public static interface Transformer<INPUT, OUTPUT> {
		OUTPUT transform(INPUT item);
	}
	
	public static interface Predicate<T> {
		boolean evaluate(T object);
	}
	
	public static interface Statement<T> {
		void run(T object);
	}
	
	public static <O> List<O> filter(Collection<O> c, Predicate<O> predicate) {
		if (c == null || predicate == null) {
			return null;
		}
		
		List<O> clone = CollectionUtils.clone(c);
		
		Iterator<O> iter = clone.iterator();
		
		while (iter.hasNext()) {
			O object = iter.next();
			
			if ( ! predicate.evaluate(object) ) {
				iter.remove();
			}
		}
		
		return clone;
	}
	
	public static <O> O firstOrDefault(Collection<O> c, Predicate<O> predicate) {
		if (c == null || predicate == null) {
			return null;
		}
		
		Iterator<O> iter = c.iterator();
		
		while (iter.hasNext()) {
			O object = iter.next();
			
			if ( predicate.evaluate(object) ) {
				return object;
			}
		}
		
		return null;
	}
	
	public static <O> O singleOrDefault(Collection<O> c, Predicate<O> predicate) {
		Collection<O> filtered = filter(c, predicate);
		
		if (CollectionUtils.isSingular(filtered)) {
			return CollectionUtils.getFirstElement(filtered);
		} else {
			if (CollectionUtils.isEmpty(filtered)) {
				return null;
			} else {
				throw new RuntimeException("Expected one or zero results, received: " + CollectionUtils.safeSize(filtered));
			}
		}
	}
	
	
	public static <O> List<O> stripNull(Collection<O> c) {
		if (c == null) {
			return null;
		}
		
		List<O> response = CollectionUtils.clone(c);
		
		Iterator<O> iter = response.iterator();
		
		while (iter.hasNext()) {
			O item = iter.next();
			if (item == null) {
				iter.remove();
			}
		}
		
		return response;
	}
	
	public static <O> O[] stripNull(O[] a) {
		List<O> list = stripNull( Arrays.asList(a) );
		
		O[] empty = Arrays.copyOf(a, 0);
		
		return list.toArray(empty);
	}
	
	public static <O> List<O> head(Collection<O> c, int num) {
		return extract(c != null ? c.iterator() : null, num);
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static <O> List<O> tail(Collection<O> c, int num) {
		ReverseListIterator iter = null;
		
		if (c != null) {
			List<O> list = null;
			
			if (c instanceof List) {
				list = (List) c;
			} else {
				list = new ArrayList<O>(c);
			}
			
			iter = new ReverseListIterator(list);
		}
		
		return extract(iter, num);
	}
	
	@SuppressWarnings("unchecked")
	protected static <O> List<O> extract(Iterator<?> iter, int num) {
		if (iter == null) {
			return null;
		}
		
		List<O> list = new ArrayList<O>();
		
		while (iter.hasNext() && (num == -1 || list.size() < num)) {
			O item = (O) iter.next();
			list.add(item);
		}
		
		return list;
	}

	public static <O> int indexOf(Collection<O> c, O value) {
		if (CollectionUtils.isEmpty(c)) {
			return -1;
		}
		
		Iterator<O> iter = c.iterator();
		
		int index = 0;
		while (iter.hasNext()) {
			O item = iter.next();
			
			if (equals(item, value)) {
				return index;
			}
			
			index++;
		}
		
		return -1;
	}
	
	public static <O> O getElement(NavigableSet<O> set, O item) {
		if (set == null) {
			return null;
		}
		return set.contains(item) ? set.ceiling(item) : null;
		
	}

	public static <O> boolean replace(Collection<O> c, O item) {
		if (c == null) {
			return false;
		}
		
		c.remove(item);
		c.add(item);
		
		return true;
	}
	
}
