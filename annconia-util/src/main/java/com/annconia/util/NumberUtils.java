package com.annconia.util;

import java.math.BigDecimal;

/**
 * 
 * @author John Stuppy
 * @author Adam Scherer
 *
 */
public class NumberUtils extends org.apache.commons.lang.math.NumberUtils {
	/*
	 * To return a default if it is null 
	 */
	public static <N extends Number> N defaultIfNull(N value, N defaultValue) {
		if (value != null)
			return value;
		return defaultValue;
	}

	public static boolean isEquals(Long a, Long b) {
		if (a == null || b == null)
			return false;
		return a.compareTo(b) == 0;
	}

	public static boolean isNotEquals(Long a, Long b) {
		return !isEquals(a, b);
	}

	public static <N extends Number> N cast(Number number, Class<N> clazz) {
		if (clazz == null) {
			throw new RuntimeException("Class cannot be null");
		}

		if (number == null) {
			return null;
		}

		Object value = null;

		if (Byte.class.equals(clazz)) {
			value = number.byteValue();
		} else if (Double.class.equals(clazz)) {
			value = number.doubleValue();
		} else if (Float.class.equals(clazz)) {
			value = number.floatValue();
		} else if (Integer.class.equals(clazz)) {
			value = number.intValue();
		} else if (Long.class.equals(clazz)) {
			value = number.longValue();
		} else if (Short.class.equals(clazz)) {
			value = number.shortValue();
		} else if (BigDecimal.class.equals(clazz)) {
			value = new BigDecimal(number.toString());
		} else if (String.class.equals(clazz)) {
			value = number.toString();
		} else {
			throw new RuntimeException("Could not determine Number type");
		}

		return clazz.cast(value);
	}

	public static boolean isEven(int number) {
		return number % 2 == 0;
	}

	public static boolean isEquivalent(BigDecimal o1, BigDecimal o2) {
		if (o1 == null || o2 == null) {
			return false;
		}
		return o1.compareTo(o2) == 0;
	}

	public static boolean isNotEquivalent(BigDecimal o1, BigDecimal o2) {
		return !isEquivalent(o1, o2);
	}

	public static boolean isEquivalent(Long o1, Long o2) {
		if (o1 == null || o2 == null) {
			return false;
		}
		return o1.compareTo(o2) == 0;
	}

	public static boolean isNotEquivalent(Long o1, Long o2) {
		return !isEquivalent(o1, o2);
	}

	public static boolean isEquivalent(Integer o1, Integer o2) {
		if (o1 == null || o2 == null) {
			return false;
		}
		return o1.compareTo(o2) == 0;
	}

	public static boolean isEquivalentOrBothNull(Integer o1, Integer o2) {
		if (o1 == null && o2 == null) {
			return true;
		}
		return isEquivalent(o1, o2);
	}

	public static boolean isNotEquivalent(Integer o1, Integer o2) {
		return !isEquivalent(o1, o2);
	}

	public static double divide(double numerator, double denominator) {
		if (denominator == 0) {
			throw new RuntimeException("Divide By 0");
		}

		double result = numerator / denominator;

		if (Double.isNaN(result)) {
			throw new RuntimeException("isNaN");
		}

		if (Double.isInfinite(result)) {
			throw new RuntimeException("isInfinite");
		}

		return result;
	}

	public static Double safeDivide(Double numerator, Double denominator, Double valueOnError) {
		try {
			return divide(numerator, denominator);
		} catch (Throwable ex) {
			return valueOnError;
		}
	}

	public static Double safeDivide(Integer numerator, Integer denominator, Double valueOnError) {
		try {
			return divide(numerator.doubleValue(), denominator.doubleValue());
		} catch (Throwable ex) {
			return valueOnError;
		}
	}

	public static int compare(int lhs, int rhs) {
		return new Integer(lhs).compareTo(rhs);
	}

	public static int compare(long lhs, long rhs) {
		return new Long(lhs).compareTo(rhs);
	}

	// negative is greater than all
	public static int compare(Integer lhs, Integer rhs) {
		if (lhs == rhs)
			return 0;
		if (lhs == null)
			return 1;
		if (rhs == null)
			return -1;

		return compare(lhs.intValue(), rhs.intValue());
	}

	public static int compare(Long lhs, Long rhs) {
		if (lhs == rhs)
			return 0;
		if (lhs == null)
			return 1;
		if (rhs == null)
			return -1;

		return compare(lhs.longValue(), rhs.longValue());
	}

	public static int compare(Double lhs, Double rhs) {
		if (lhs == rhs)
			return 0;
		if (lhs == null)
			return 1;
		if (rhs == null)
			return -1;

		return compare(lhs.doubleValue(), rhs.doubleValue());
	}

	public static int compare(Float lhs, Float rhs) {
		if (lhs == rhs)
			return 0;
		if (lhs == null)
			return 1;
		if (rhs == null)
			return -1;

		return compare(lhs.floatValue(), rhs.floatValue());
	}

	public static int roundToInt(double value) {
		return (int) Math.round(value);
	}

	public static double round(double value, int precision) {
		if (precision < 0) {
			return value;
		}

		if (precision == 0) {
			return Math.round(value);
		}

		double multiplier = Math.pow(10, precision);

		return Math.round(value * multiplier) / multiplier;
	}

	public static double roundToNearest(double value, double nearest) {
		if (nearest <= 0 || nearest == 1) {
			return Math.round(value);
		}

		double multiplier = 1 / nearest;

		return Math.round(value * multiplier) / multiplier;
	}

	public static String padZero(int number, int length) {
		if (length == 0) {
			return String.valueOf(number);
		}

		return String.format("%0" + length + "d", number);
	}

	public static short valueOf(Short value) {
		return valueOf(value, (short) 0);
	}

	public static int valueOf(Integer value) {
		return valueOf(value, 0);
	}

	public static double valueOf(Double value) {
		return valueOf(value, 0d);
	}

	public static long valueOf(Long value) {
		return valueOf(value, 0l);
	}

	public static <N> N valueOf(N value, N valueIfNull) {
		if (value != null) {
			return value;
		} else {
			return valueIfNull;
		}
	}

	public static boolean isDouble(String value) {
		return StringUtils.contains(value, '.');
	}

	public static int max(int value, int... values) {
		int max = value;

		for (int v : values) {
			max = Math.max(max, v);
		}

		return max;
	}

	public static int min(int value, int... values) {
		int min = value;

		for (int v : values) {
			min = Math.min(min, v);
		}

		return min;
	}

	/**
	 * Rollover value to between min and max, inclusive
	 * @param value
	 * @param min
	 * @param max
	 * @return
	 */
	public static int rollover(int value, int min, int max) {
		if (min <= value && value <= max) {
			return value;
		}

		int n = (max - min + 1);

		int result = (((value + max) % n) + max);

		if (result > max) {
			result %= n;
		}

		return result;
	}
}
