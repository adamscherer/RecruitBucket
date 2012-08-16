package com.annconia.util;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.beanutils.BeanUtils;

/**
 * 
 * @author John Stuppy
 * @author Adam Scherer
 *
 */
public class ObjectUtils extends org.apache.commons.lang.ObjectUtils {

	/**
	 * <p>Compares two objects for inequality, where either one or both
	 * objects may be <code>null</code>.</p>
	 *
	 * <pre>
	 * ObjectUtils.equals(null, null)                  = false
	 * ObjectUtils.equals(null, "")                    = true
	 * ObjectUtils.equals("", null)                    = true
	 * ObjectUtils.equals("", "")                      = false
	 * ObjectUtils.equals(Boolean.TRUE, null)          = true
	 * ObjectUtils.equals(Boolean.TRUE, "true")        = true
	 * ObjectUtils.equals(Boolean.TRUE, Boolean.TRUE)  = false
	 * ObjectUtils.equals(Boolean.TRUE, Boolean.FALSE) = true
	 * </pre>
	 *
	 * @param object1  the first object, may be <code>null</code>
	 * @param object2  the second object, may be <code>null</code>
	 * @return <code>true</code> if the values of both objects are the same
	 */
	public static boolean unequal(Object object1, Object object2) {
		return !ObjectUtils.equals(object1, object2);
	}

	@SuppressWarnings("unchecked")
	static public Map<String, Object> describe(Object bean) throws IllegalAccessException, InvocationTargetException, NoSuchMethodException {
		if (bean == null)
			return null;

		Map<String, Object> map = (Map<String, Object>) BeanUtils.describe(bean);

		map = new LinkedHashMap<String, Object>(map);

		for (Field field : bean.getClass().getFields()) {
			map.put(field.getName(), field.get(bean));
		}

		return Collections.unmodifiableMap(map);
	}

	static public <T, I> Map<I, T> collectionToMap(Collection<T> collection, PropertyFetcher<I, ? super T> fetcher) {
		return collectionToMap(collection, fetcher, false);
	}

	static public <T, I> Map<I, T> collectionToMap(Collection<T> collection, PropertyFetcher<I, ? super T> fetcher, boolean skipOnError) {
		Map<I, T> map = new LinkedHashMap<I, T>();

		if (CollectionUtils.isNotEmpty(collection)) {
			for (T item : collection) {
				if (skipOnError) {
					try {
						map.put(fetcher.fetch(item), item);
					} catch (Throwable ex) {
						// skip
					}
				} else {
					map.put(fetcher.fetch(item), item);
				}
			}
		}

		return map;
	}

	static public <T, I> Map<I, List<T>> collectionToMapOfLists(Collection<T> collection, PropertyFetcher<I, ? super T> fetcher) {
		Map<I, List<T>> map = new LinkedHashMap<I, List<T>>();

		if (CollectionUtils.isNotEmpty(collection)) {
			for (T item : collection) {
				I key = fetcher.fetch(item);
				if (key != null) {
					if (MapUtils.doesNotContainKey(map, key)) {
						map.put(key, new ArrayList<T>());
					}
					map.get(key).add(item);
				}
			}
		}

		return map;
	}

	static public <T> T coalesce(T... items) {
		if (CollectionUtils.isNotEmpty(items)) {
			for (T item : items) {
				if (item != null) {
					return item;
				}
			}
		}

		return null;
	}

	static public <T> int compare(T lhs, T rhs, Collection<Comparator<T>> comparators) {
		if (CollectionUtils.isNotEmpty(comparators)) {
			for (Comparator<T> comparator : comparators) {
				int result = comparator.compare(lhs, rhs);

				if (result != 0) {
					return result;
				}
			}
		}

		return 0;
	}

	static abstract public interface PropertyFetcher<I, O> {
		I fetch(O obj);
	}

	public static <FROM, TO extends FROM> TO castOrNull(FROM object, Class<TO> to) {
		try {
			return to.cast(object);
		} catch (Throwable ex) {
			return null;
		}
	}
	
	static public boolean isAnyNull(Object... objs) {
		if (CollectionUtils.isEmpty(objs)) {
			return false;
		}
		
		for (Object obj : objs) {
			if (obj == null) {
				return true;
			}
		}
		
		return false;
	}
	
	static public boolean isAnyNotNull(Object... objs) {
		if (CollectionUtils.isEmpty(objs)) {
			return false;
		}
		
		for (Object obj : objs) {
			if (obj != null) {
				return true;
			}
		}
		
		return false;
	}
	
	static public boolean isAllNotNull(Object... objs) {
		if (CollectionUtils.isEmpty(objs)) {
			return false;
		}
		
		for (Object obj : objs) {
			if (obj == null) {
				return false;
			}
		}
		
		return true;
	}

}
