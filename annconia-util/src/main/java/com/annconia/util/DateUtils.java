package com.annconia.util;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.SortedSet;
import java.util.TimeZone;
import java.util.TreeSet;

import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;

import org.apache.commons.lang.time.DateFormatUtils;
import org.apache.commons.lang.time.FastDateFormat;
import org.joda.time.DateTime;
import org.joda.time.Years;

/**
 * 
 * @author John Stuppy
 * @author Adam Scherer
 *
 */
public class DateUtils extends org.apache.commons.lang.time.DateUtils {

	private static DatatypeFactory df = null;
	static {
		try {
			df = DatatypeFactory.newInstance();
		} catch (DatatypeConfigurationException dce) {
			throw new IllegalStateException("Exception while obtaining DatatypeFactory instance", dce);
		}
	}

	public static final String YYYY_MM_DD = "yyyy-MM-dd";

	public static final ThreadLocal<SimpleDateFormat> TIME_FORMAT_TL = new ThreadLocal<SimpleDateFormat>() {
		@Override
		protected SimpleDateFormat initialValue() {
			return new SimpleDateFormat("HHmm");
		}
	};

	public static final ThreadLocal<SimpleDateFormat> OUTPUT_MONTH_DAY_TIME_FORMAT_TL = new ThreadLocal<SimpleDateFormat>() {
		@Override
		protected SimpleDateFormat initialValue() {
			return new SimpleDateFormat("MMMM d, yyyy 'at' h:mm a");
		}
	};

	public static final ThreadLocal<SimpleDateFormat> FULL_DATE_TIME_FORMAT_TL = new ThreadLocal<SimpleDateFormat>() {
		@Override
		protected SimpleDateFormat initialValue() {
			return new SimpleDateFormat("MMM dd, yyyy HH:mm:ss z");
		}
	};

	public static final ThreadLocal<SimpleDateFormat> FULL_DATE_TIME_FORMAT = new ThreadLocal<SimpleDateFormat>() {
		@Override
		protected SimpleDateFormat initialValue() {
			return new SimpleDateFormat("MM/dd/yyyy '-' hh:mm");
		}
	};

	public static final ThreadLocal<SimpleDateFormat> FULL_DATE_TIME_FORMAT_SSO = new ThreadLocal<SimpleDateFormat>() {
		@Override
		protected SimpleDateFormat initialValue() {
			return new SimpleDateFormat("yyyy-MM-dd+HH:mm:ss");
		}
	};

	public static final FastDateFormat DATE_FORMAT_MMMddyyyy = FastDateFormat.getInstance("MMM dd, yyyy");
	public static final FastDateFormat DATE_FORMAT_MMddyyyy = FastDateFormat.getInstance("MM/dd/yyyy");
	public static final FastDateFormat DATE_FORMAT_FULL_DATE_TIME = FastDateFormat.getInstance("MM/dd/yyyy '-' hh:mm");

	public static final int DAYS_PER_WEEK = 7;

	public static final long MS_IN_S = 1000;
	public static final long MS_IN_M = 60 * MS_IN_S;
	public static final long MS_IN_H = 60 * MS_IN_M;
	public static final long MS_IN_D = 24 * MS_IN_H;

	private static String WEEK = "week";
	private static String MONTH = "month";
	private static String YEAR = "year";

	public static final Comparator<Date> SAME_DAY_COMPARATOR = new Comparator<Date>() {
		public int compare(Date lhs, Date rhs) {
			if (DateUtils.isSameDay(lhs, rhs)) {
				return 0;
			}

			return lhs.compareTo(rhs);
		}
	};

	public static Calendar toCalendar(Date date) {
		if (date == null) {
			return null;
		}

		Calendar c = Calendar.getInstance();
		c.setTime(date);

		return c;
	}

	/**
	* <p>Checks if two date objects are in the same year ignoring time.</p>
	*
	* <p>28 Mar 2002 13:45 and 12 Mar 2002 06:01 would return true.
	* 28 Mar 2002 13:45 and 12 Mar 2005 02:45 would return false.
	* </p>
	* 
	* @param date1  the first date, not altered, not null
	* @param date2  the second date, not altered, not null
	* @return true if they represent the same day
	* @throws IllegalArgumentException if either date is <code>null</code>
	* @since 2.1
	*/
	public static boolean isSameYear(Date date1, Date date2) {
		if (date1 == null || date2 == null) {
			throw new IllegalArgumentException("The date must not be null");
		}
		Calendar cal1 = Calendar.getInstance();
		cal1.setTime(date1);
		Calendar cal2 = Calendar.getInstance();
		cal2.setTime(date2);
		return isSameYear(cal1, cal2);
	}

	/**
	 * <p>Checks if two calendar objects are in the same year ignoring time.</p>
	 *
	 * <p>28 Mar 2002 13:45 and 12 Mar 2002 06:01 would return true.
	 * 28 Mar 2002 13:45 and 12 Mar 2005 02:45 would return false.
	 * </p>
	 * 
	 * @param cal1  the first calendar, not altered, not null
	 * @param cal2  the second calendar, not altered, not null
	 * @return true if they represent the same day
	 * @throws IllegalArgumentException if either calendar is <code>null</code>
	 * @since 2.1
	 */
	public static boolean isSameYear(Calendar cal1, Calendar cal2) {
		if (cal1 == null || cal2 == null) {
			throw new IllegalArgumentException("The calendar must not be null");
		}
		return (cal1.get(Calendar.ERA) == cal2.get(Calendar.ERA) && cal1.get(Calendar.YEAR) == cal2.get(Calendar.YEAR));
	}

	public static boolean safeIsSameDay(Date date1, Date date2) {
		try {
			return DateUtils.isSameDay(date1, date2);
		} catch (Throwable ex) {
			return false;
		}
	}

	public static boolean safeIsSameDay(Calendar cal1, Calendar cal2) {
		try {
			return DateUtils.isSameDay(cal1, cal2);
		} catch (Throwable ex) {
			return false;
		}
	}

	/**
	 * Useful for determining if a date is between the start and end date (or effective and expiration date).
	 * 
	 * @param startDate
	 * @param endDate
	 * @return
	 */
	public static boolean isTodayInRange(Date startDate, Date endDate) {
		if (startDate == null || endDate == null) {
			return false;
		}

		return isDateInRange(Calendar.getInstance().getTime(), startDate, DateUtils.addDays(endDate, 1));
	}

	public static boolean isDateNotInRange(Date date, Date startDate, Date endDate) {
		return !isDateInRange(date, startDate, endDate);
	}

	public static boolean isDateInRange(Date date, Date startDate, Date endDate) {
		if (date == null || startDate == null || endDate == null) {
			return false;
		}

		if (startDate.after(date)) {
			return false;
		}

		if (endDate.before(date)) {
			return false;
		}

		return true;
	}

	public static boolean isDateInRangeOrNull(Date date, Date startDate, Date endDate) {
		if (date == null) {
			return false;
		}

		if (startDate != null) {
			if (date.before(startDate)) {
				return false;
			}
		}

		if (endDate != null) {
			if (date.after(endDate)) {
				return false;
			}
		}

		return true;
	}

	public static boolean isFutureDate(Date date) {
		if (date == null) {
			return false;
		}

		Date today = DateUtils.truncate(Calendar.getInstance(), Calendar.DATE).getTime();
		if (date.after(today)) {
			return true;
		}

		return false;
	}

	public static boolean isPastDate(Date date) {
		if (date == null) {
			return false;
		}

		if (isToday(date)) {
			return false;
		}

		Date today = DateUtils.truncate(Calendar.getInstance(), Calendar.DATE).getTime();
		if (date.before(today)) {
			return true;
		}

		return false;
	}

	public static boolean isToday(Date date) {
		if (date == null) {
			return false;
		}

		return isSameDay(date, new Date());
	}

	public static boolean isNotToday(Date date) {
		return !isToday(date);
	}

	public static boolean isAfter(Date startDate, Date endDate) {
		if (startDate == null) {
			return false;
		}

		if (endDate == null) {
			return false;
		}

		return endDate.after(startDate);
	}

	public static boolean isBefore(Date startDate, Date endDate) {
		if (startDate == null) {
			return false;
		}

		if (endDate == null) {
			return false;
		}

		return endDate.before(startDate);
	}

	public static boolean isNotBefore(Date startDate, Date endDate) {
		return !isBefore(startDate, endDate);
	}

	public static boolean isAfterToday(Date date) {
		return DateUtils.isAfter(new Date(), date) && DateUtils.isNotToday(date);
	}

	public static boolean isYesterday(Date date) {
		if (date == null) {
			return false;
		}

		return isSameDay(date, addDays(-1));
	}

	public static boolean isThisWeek(Date date) {
		if (date == null) {
			return false;
		}

		DateTime dt = new DateTime(date);
		DateTime current = new DateTime();

		return dt.getYear() == current.getYear() && dt.getWeekOfWeekyear() == current.getWeekOfWeekyear();
	}

	public static boolean isThisMonth(Date date) {
		if (date == null) {
			return false;
		}

		DateTime dt = new DateTime(date);
		DateTime current = new DateTime();

		return dt.getYear() == current.getYear() && dt.getMonthOfYear() == current.getMonthOfYear();
	}

	public static boolean isThisYear(Date date) {
		if (date == null) {
			return false;
		}

		return isSameYear(date, new Date());
	}

	public static Date addDays(int days) {
		return addDays(new Date(), days);
	}

	public static Date addWeeks(int weeks) {
		return addWeeks(new Date(), weeks);
	}

	public static Date addMonths(int months) {
		return addMonths(new Date(), months);
	}

	public static Date addHours(int hours) {
		return addHours(new Date(), hours);
	}

	public static Date addMinutes(int minutes) {
		return addMinutes(new Date(), minutes);
	}

	public static Date maxNotNull(Date... dates) {
		Date max = null;

		if (CollectionUtils.isNotEmpty(dates)) {
			for (Date date : dates) {
				if (date == null)
					continue;

				if (max == null || date.after(max)) {
					max = date;
				}
			}
		}

		return clone(max);
	}

	public static Date minNotNull(Date... dates) {
		Date min = null;

		if (CollectionUtils.isNotEmpty(dates)) {
			for (Date date : dates) {
				if (date == null) {
					continue;
				}

				if (min == null || date.before(min)) {
					min = date;
				}
			}
		}

		return clone(min);
	}

	public static Date clone(Date date) {
		if (date == null)
			return null;

		return (Date) date.clone();
	}

	public static Date getToday() {
		return getBeginningOfDay(new Date());
	}

	public static Date getYesterday() {
		return getBeginningOfDay(addDays(-1));
	}

	public static Date getTomorrow() {
		return getBeginningOfDay(addDays(1));
	}

	public static Date getBeginningOfDay(Date date) {
		return getBeginningOfDay(date, TimeZone.getDefault());
	}

	public static Date getBeginningOfDay(Date date, TimeZone timeZone) {
		if (date == null) {
			return null;
		}

		Calendar system = Calendar.getInstance();
		system.setTime(date);

		int year = system.get(Calendar.YEAR);
		int month = system.get(Calendar.MONTH);
		int day = system.get(Calendar.DATE);

		Calendar calendar = Calendar.getInstance(timeZone);

		calendar.set(Calendar.YEAR, year);
		calendar.set(Calendar.MONTH, month);
		calendar.set(Calendar.DATE, day);
		calendar.set(Calendar.HOUR_OF_DAY, 0);
		calendar.set(Calendar.MINUTE, 0);
		calendar.set(Calendar.SECOND, 0);
		calendar.set(Calendar.MILLISECOND, 0);

		return calendar.getTime();
	}

	public static Date getBeginningOfNextDay(Date date) {
		return getBeginningOfNextDay(date, TimeZone.getDefault());
	}

	public static Date getBeginningOfNextDay(Date date, TimeZone timeZone) {
		if (date == null) {
			return null;
		}

		return getBeginningOfDay(addDays(date, 1), timeZone);
	}

	public static Date getEndOfNextDay(Date date) {
		return getEndOfNextDay(date, TimeZone.getDefault());
	}

	public static Date getEndOfNextDay(Date date, TimeZone timeZone) {
		if (date == null) {
			return null;
		}

		return getEndOfDay(addDays(date, 1), timeZone);
	}

	public static Date getBeginningOfPreviousDay(Date date) {
		if (date == null) {
			return null;
		}

		return getBeginningOfDay(addDays(date, -1));
	}

	public static Date getEndOfPreviousDay(Date date) {
		if (date == null) {
			return null;
		}

		return getEndOfDay(addDays(date, -1));
	}

	public static Date getEndOfDay(Date date) {
		return getEndOfDay(date, TimeZone.getDefault());
	}

	public static Date getEndOfDay(Date date, TimeZone timeZone) {
		if (date == null) {
			return null;
		}

		Date bod = getBeginningOfDay(date, timeZone);

		Calendar cal = Calendar.getInstance(timeZone);
		cal.setTime(bod);

		cal.add(Calendar.DATE, 1);
		cal.set(Calendar.MILLISECOND, -1);

		return cal.getTime();
	}

	// TODO revisit
	public static Calendar getEndOfDay(Calendar input) {
		if (input == null) {
			return null;
		}

		Calendar cal = (Calendar) input.clone();
		cal.setTime(cal.getTime());

		cal.add(Calendar.DATE, 1);

		cal.set(Calendar.HOUR_OF_DAY, 0);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.SECOND, 0);
		cal.set(Calendar.MILLISECOND, -1);

		return cal;
	}

	public static Date getNextDay(Date date, int dayOfWeek, boolean inclusive) {
		return getNearestDay(date, dayOfWeek, 1, inclusive);
	}

	public static Date getNextDay(Calendar calendar, int dayOfWeek, boolean inclusive) {
		return getNearestDay(calendar, dayOfWeek, 1, inclusive);
	}

	public static Date getPriorDay(Date date, int dayOfWeek, boolean inclusive) {
		return getNearestDay(date, dayOfWeek, -1, inclusive);
	}

	public static Date getPriorDay(Calendar calendar, int dayOfWeek, boolean inclusive) {
		return getNearestDay(calendar, dayOfWeek, -1, inclusive);
	}

	public static Date getStartOfWeek(Date date) {
		return DateUtils.getPriorDay(date, Calendar.MONDAY, true);
	}

	public static Date getStartOfNextWeek(Date date) {
		return DateUtils.getNextDay(date, Calendar.MONDAY, false);
	}

	public static Date getStartOfWeek(Calendar calendar) {
		return DateUtils.getPriorDay(calendar, Calendar.MONDAY, true);
	}

	public static Date getStartOfNextWeek(Calendar calendar) {
		return DateUtils.getNextDay(calendar, Calendar.MONDAY, false);
	}

	public static final SortedSet<Integer> DAYS_OF_WEEK = Collections.unmodifiableSortedSet(new TreeSet<Integer>(Arrays.asList(Calendar.SUNDAY, Calendar.MONDAY, Calendar.TUESDAY, Calendar.WEDNESDAY, Calendar.THURSDAY, Calendar.FRIDAY, Calendar.SATURDAY)));

	public static boolean isValidDayOfWeek(int dayOfWeek) {
		return CollectionUtils.contains(DAYS_OF_WEEK, dayOfWeek);
	}

	public static String getDayOfWeekString(int dayOfWeek) {
		switch (dayOfWeek) {
		case Calendar.SUNDAY:
			return "Sunday";
		case Calendar.MONDAY:
			return "Monday";
		case Calendar.TUESDAY:
			return "Tuesday";
		case Calendar.WEDNESDAY:
			return "Wednesday";
		case Calendar.THURSDAY:
			return "Thursday";
		case Calendar.FRIDAY:
			return "Friday";
		case Calendar.SATURDAY:
			return "Saturday";
		}

		return "NotFound";
	}

	protected static Date getNearestDay(Date date, int dayOfWeek, int direction, boolean inclusive) {
		if (date == null) {
			return null;
		}

		if (!isValidDayOfWeek(dayOfWeek)) {
			return null;
		}

		date = DateUtils.getBeginningOfDay(date);

		Calendar cal = Calendar.getInstance();
		cal.setTime(date);

		return getNearestDay(cal, dayOfWeek, direction, inclusive);
	}

	protected static Date getNearestDay(Calendar calendar, int dayOfWeek, int direction, boolean inclusive) {
		if (calendar == null) {
			return null;
		}

		if (!isValidDayOfWeek(dayOfWeek)) {
			return null;
		}

		if (!inclusive) {
			calendar.add(Calendar.DATE, direction >= 0 ? 1 : -1);
		}

		while (calendar.get(Calendar.DAY_OF_WEEK) != dayOfWeek) {
			calendar.add(Calendar.DATE, direction >= 0 ? 1 : -1);
		}

		return calendar.getTime();
	}

	public static Date subtract1Millisecond(Date date) {
		if (date == null) {
			return null;
		}

		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);

		calendar.set(Calendar.MILLISECOND, -1);

		return calendar.getTime();
	}

	/**
	 * @param o Must be of type XMLGregorianCalendar, Calendar, or Date
	 */
	public static final String formatMessageDate(Object o) {
		Calendar c = null;
		if (o instanceof XMLGregorianCalendar) {
			c = ((XMLGregorianCalendar) o).toGregorianCalendar();
		} else if (o instanceof Date) {
			c = Calendar.getInstance();
			c.setTime((Date) o);
		} else {
			c = (Calendar) o;
		}

		Calendar today = Calendar.getInstance();
		if (DateUtils.isSameDay(c, today)) {
			return DateFormatUtils.format(c, "h:mm a").toLowerCase();
		} else if (DateUtils.isSameYear(c, today)) {
			return DateFormatUtils.format(c, "MMM d");
		}

		return DateFormatUtils.format(c, "MM/dd/yy");
	}

	/**
	 * Converts a java.util.Date into an instance of XMLGregorianCalendar
	 *
	 * @param date Instance of java.util.Date or a null reference
	 * @return XMLGregorianCalendar instance whose value is based upon the
	 *  value in the date parameter. If the date parameter is null then
	 *  this method will simply return null.
	 */
	public static XMLGregorianCalendar asXMLGregorianCalendar(java.util.Date date) {
		if (date == null) {
			return null;
		} else {
			GregorianCalendar gc = new GregorianCalendar();
			gc.setTimeInMillis(date.getTime());
			return df.newXMLGregorianCalendar(gc);
		}
	}

	public static String formatActivityTime(Date activityDate) {
		return format(activityDate, TIME_FORMAT_TL.get());
	}

	public static String formatActivityDateAndTime(Date activityDate) {
		return format(activityDate, OUTPUT_MONTH_DAY_TIME_FORMAT_TL.get());
	}

	public static String formatChartDate(Date date) {
		return DATE_FORMAT_MMddyyyy.format(date);
	}

	public static String formatChartDate(Calendar calendar) {
		return DATE_FORMAT_MMddyyyy.format(calendar);
	}

	private static String format(Date activityDate, DateFormat defaultFormat) {
		try {
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(activityDate);

			Calendar today = Calendar.getInstance();
			today.setTime(new Date());

			if (isToday(activityDate)) {
				long diff = today.getTimeInMillis() - calendar.getTimeInMillis();
				long minDiff = diff / (1000 * 60);
				if (minDiff < 60) {
					if (minDiff <= 1)
						return new StringBuilder().append("1 Minute Ago").toString();
					else
						return new StringBuilder().append(minDiff).append(" Minutes Ago").toString();
				} else {
					long hourDiff = minDiff / 60;
					if (hourDiff <= 1)
						return new StringBuilder().append("1 Hour Ago").toString();
					else
						return new StringBuilder().append(hourDiff).append(" Hours Ago").toString();
				}
			}

			return defaultFormat.format(activityDate);
		} catch (Throwable ex) {
			return "";
		}
	}

	public static String format(Date date, String pattern) {
		try {
			return format(date, pattern, TimeZone.getDefault());
		} catch (Throwable ex) {
			return "";
		}
	}

	public static String format(Date date, String pattern, TimeZone timeZone) {
		if (timeZone == null) {
			throw new IllegalArgumentException("TimeZone cannot be null");
		}

		try {
			DateFormat df = new SimpleDateFormat(pattern);
			df.setTimeZone(timeZone);
			return df.format(date);
		} catch (Throwable ex) {
			return "";
		}
	}

	public static int getDaysRemaining(Date date) {
		if (date == null) {
			return 0;
		}

		Calendar today = DateUtils.truncate(Calendar.getInstance(), Calendar.DATE);
		if (date.before(today.getTime())) {
			return 0;
		}

		Calendar end = Calendar.getInstance();
		end.setTime(date);
		end = DateUtils.truncate(end, Calendar.DATE);

		// get positive value so that the difference is calculated as positive number.
		return (int) (Math.abs(today.getTimeInMillis() - end.getTimeInMillis()) / MILLIS_PER_DAY);
	}

	public static int getDaysBeforeStart(Date date) {
		return getDaysBetween(new Date(), date, true);
	}

	public static int getDaysPassed(Date date) {
		return getDaysBetween(date, new Date(), true);
	}

	public static int getDaysBetween(Date date1, Date date2) {
		return getDaysBetween(date1, date2, false);
	}

	public static int getWeeksBetween(Date date1, Date date2) {
		return (int) Math.floor(getDaysBetween(date1, date2, false) / 7);
	}

	public static long getTimeBetween(Date date1, Date date2, int time, boolean order) {
		if (date1 == null || date2 == null) {
			return 0;
		}

		if (order) {
			if (date1.after(date2)) {
				return 0;
			}
		} else if (date1.after(date2)) {
			Date temp = date1;
			date1 = date2;
			date2 = temp;
		}

		long diffInMs = date2.getTime() - date1.getTime();

		switch (time) {
		case Calendar.MILLISECOND:
			return diffInMs;
		case Calendar.SECOND:
			return diffInMs / MS_IN_S;
		case Calendar.MINUTE:
			return diffInMs / MS_IN_M;
		case Calendar.HOUR:
			return diffInMs / MS_IN_H;
		default:
			Calendar cal = Calendar.getInstance();
			cal.setTime(date1);

			long count = 0;

			while (!cal.getTime().after(date2)) {
				count += 1;
				cal.add(time, 1);
			}

			return Math.max(0, count - 1);
		}
	}

	public static int getMonthsBetwen(Date date1, Date date2) {
		if (date1 == null || date2 == null) {
			return 0;
		}

		if (date2.before(date1)) {
			Date temp = date1;
			date1 = date2;
			date2 = temp;
		}

		date1 = DateUtils.getBeginningOfDay(date1);
		date2 = DateUtils.getEndOfDay(date2);

		Calendar c = Calendar.getInstance();
		c.setTime(date1);

		int months = 0;

		while (!c.getTime().after(date2)) {
			c.add(Calendar.MONTH, 1);
			months += 1;
		}

		return Math.max(months - 1, 0);
	}

	protected static int getDaysBetween(Date date1, Date date2, boolean order) {
		if (date1 == null || date2 == null) {
			return 0;
		}

		date1 = DateUtils.truncate(date1, Calendar.DATE);
		date2 = DateUtils.truncate(date2, Calendar.DATE);

		if (order) {
			if (date1.after(date2)) {
				return 0;
			}
		} else if (date1.after(date2)) {
			Date temp = date1;
			date1 = date2;
			date2 = temp;
		}

		Calendar cal = Calendar.getInstance();
		cal.setTime(date1);

		int numDays = 0;

		while (!cal.getTime().after(date2)) {
			numDays += 1;
			cal.add(Calendar.DATE, 1);
		}

		return numDays - 1;
	}

	public static Date randomDate(Date startDate, Date endDate) {

		Calendar start = Calendar.getInstance();
		start.setTime(startDate);

		Calendar end = Calendar.getInstance();
		end.setTime(endDate);

		int year = randomBetween(start.get(Calendar.YEAR), end.get(Calendar.YEAR));
		int dayOfYear = randomBetween(start.get(Calendar.DAY_OF_YEAR), end.get(Calendar.DAY_OF_YEAR));

		Calendar random = Calendar.getInstance();
		random.set(Calendar.YEAR, year);
		random.set(Calendar.DAY_OF_YEAR, dayOfYear);

		return random.getTime();
	}

	protected static int randomBetween(int start, int end) {
		return start + (int) Math.round(Math.random() * (end - start));
	}

	public static double getTimeUnitCount(String timeUnit, int numberOfDays) {
		if (timeUnit.equals(WEEK))
			return getWeeksIn(numberOfDays);
		else if (timeUnit.equals(MONTH))
			return getMonthsIn(numberOfDays);
		if (timeUnit.equals(YEAR))
			return getYearsIn(numberOfDays);
		else
			return numberOfDays;
	}

	public static int getTimeUnitCountRounded(String timeUnit, int numberOfDays) {
		double units = getTimeUnitCount(timeUnit, numberOfDays);
		return new Double(units).intValue();
	}

	public static double getMonthsIn(int numberOfDays) {
		return (double) numberOfDays / 30D;
	}

	public static double getWeeksIn(int numberOfDays) {
		return (double) numberOfDays / 7D;
	}

	public static double getYearsIn(int numberOfDays) {
		return (double) numberOfDays / 365D;
	}

	public static int getDaysInMonth(int year, int month) {
		Calendar calendar = Calendar.getInstance(TimeZone.getTimeZone("GMT"));
		calendar.set(year, month, 1);

		return calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
	}

	public static int getAge(Date birthDate) {
		DateTime today = new DateTime(getToday()).dayOfYear().roundFloorCopy();
		DateTime dob = new DateTime(birthDate).dayOfYear().roundFloorCopy();

		return Years.yearsBetween(dob, today).getYears();
	}

	public static int getAge(Date birthDate, Date now) {
		if (birthDate == null || now == null || birthDate.after(now)) {
			return -1; // TODO error or null?
		}

		DateTime dob = new DateTime(birthDate).dayOfYear().roundFloorCopy();
		DateTime today = new DateTime(now).dayOfYear().roundFloorCopy();

		return Years.yearsBetween(dob, today).getYears();
	}

	/**
	 * Day of Week starts at 1 for Monday and ends with 7 for Sunday.
	 */
	public static int getDayOfWeek(Date date) {
		DateTime dateTime = new DateTime(date);
		return dateTime.getDayOfWeek();
	}

	/**
	 * Day of Week starts at 1 for Sunday and ends with 7 for Saturday.
	 */
	public static int getSundayBasedDayOfWeek(Date date) {
		DateTime dateTime = new DateTime(date);
		return getSundayBasedDayOfWeek(dateTime);
	}

	public static int getSundayBasedDayOfWeek(DateTime dateTime) {
		int day = dateTime.getDayOfWeek();
		if (day == 7) {
			return 1;
		}
		return day + 1;
	}

	public static Comparator<Date> getComparator(final boolean nullIsBOT) {
		return new Comparator<Date>() {

			public int compare(Date lhs, Date rhs) {
				if (lhs == rhs) { // both null
					return 0;
				}

				if (lhs == null) {
					return nullIsBOT ? -1 : 1;
				}

				if (rhs == null) {
					return nullIsBOT ? 1 : -1;
				}

				return lhs.compareTo(rhs);
			}
		};
	}

	public static int compare(Date lhs, Date rhs, boolean nullIsBOT) {
		return getComparator(nullIsBOT).compare(lhs, rhs);
	}

	public static Date parseDate(String str, String parsePattern) throws ParseException {
		return parseDate(str, new String[] { parsePattern });
	}

	public static Date parseDateOrNull(String str, String parsePattern) {
		return parseDateOrNull(str, new String[] { parsePattern });
	}

	public static Date parseDateOrNull(String str, String[] parsePatterns) {
		try {
			return parseDate(str, parsePatterns);
		} catch (Throwable e) {
			return null;
		}
	}

	public static String formatSSODate(Date date) {
		return format(date, FULL_DATE_TIME_FORMAT_SSO.get());
	}

	public static boolean isNotSameDay(Date date1, Date date2) {
		return !DateUtils.isSameDay(date1, date2);
	}

	public static boolean isNotSameDay(Calendar cal1, Calendar cal2) {
		return !DateUtils.isSameDay(cal1, cal2);
	}

	public static boolean isNotSameYear(Date date1, Date date2) {
		return !DateUtils.isNotSameYear(date1, date2);
	}

	public static boolean isNotSameYear(Calendar cal1, Calendar cal2) {
		return !DateUtils.isNotSameYear(cal1, cal2);
	}

	public static boolean isInBetween(Date date1, Date date2, Date input) {
		return DateUtils.isOnOrAfter(date1, input) && DateUtils.isOnOrBefore(date2, input);
	}

	public static boolean isNotInBetween(Date date1, Date date2, Date input) {
		return !isInBetween(date1, date2, input);
	}

	public static boolean isOnOrBefore(Date startDate, Date endDate) {
		return isBefore(startDate, endDate) || isSameDay(startDate, endDate);
	}

	public static boolean isOnOrAfter(Date startDate, Date endDate) {
		return isAfter(startDate, endDate) || isSameDay(startDate, endDate);
	}

	public static Date parseDate(int month, int dayOfMonth) {
		try {
			Calendar cal = Calendar.getInstance();
			DateTime current = new DateTime();
			cal.set(current.getYear(), month, dayOfMonth);
			return cal.getTime();
		} catch (Throwable ex) {
			return null;
		}
	}

	public static Date nextDateThanToday(int month, int dayOfMonth) {
		try {
			Calendar cal = Calendar.getInstance();
			DateTime current = new DateTime();
			cal.set(current.getYear(), month, dayOfMonth);
			if (isBefore(cal.getTime(), current.toDate())) {
				return cal.getTime();
			} else {
				cal = Calendar.getInstance();
				cal.set(current.getYear() + 1, month, dayOfMonth);
				return cal.getTime();
			}

		} catch (Throwable ex) {
			return null;
		}
	}

	public static int getDaysInTimeUnit(String timeUnit) {
		if (timeUnit.equals(WEEK))
			return 7;
		else
			return 1;
	}

	public static Date defaultIfNull(Date primary, Date defaultValue) {
		if (primary != null)
			return primary;
		return defaultValue;
	}

	public static String toStandardFormat(Date date) {
		try {
			return DATE_FORMAT_MMddyyyy.format(date);
		} catch (Throwable ex) {
			return "-";
		}
	}

	public static String toReportFormat(Date date) {
		try {
			return DATE_FORMAT_MMMddyyyy.format(date);
		} catch (Throwable ex) {
			return null;
		}
	}

	public static Date getDayOn(Date date, TimeZone timeZone) {
		if (date == null) {
			return null;
		}

		if (timeZone == null) {
			timeZone = TimeZone.getDefault();
		}

		final String YYYY_MM_DD = "yyyy-MM-dd";

		String formatted = DateUtils.format(date, YYYY_MM_DD, timeZone);

		Date parsed = DateUtils.parseDateOrNull(formatted, YYYY_MM_DD);

		return DateUtils.getBeginningOfDay(parsed);
	}

	public static int get(Date date, int property) {
		if (date == null) {
			return -1;
		}

		Calendar c = Calendar.getInstance();

		c.setTime(date);

		return c.get(property);
	}

	public static int getTimeZoneOffsetDifference(TimeZone from, TimeZone to) {
		from = ObjectUtils.coalesce(from, TimeZone.getDefault());
		to = ObjectUtils.coalesce(to, TimeZone.getDefault());

		if (from.hasSameRules(to)) {
			return 0;
		}

		return (int) ((to.getRawOffset() - from.getRawOffset()) / MS_IN_M);
	}

	public static TimeZone getTimeZoneOrDefault(String id) {
		try {
			return TimeZone.getTimeZone(id);
		} catch (Throwable ex) {
			return TimeZone.getDefault();
		}
	}

	/**
	 * Get ellapsed time.
	 * 
	 * @param activityDate
	 * @return Long.MAX_VALUE or ellapsed time (negatives are allowed for future dates)
	 */
	public static long getEllapsedTime(Date activityDate) {
		if (activityDate == null) {
			return Long.MAX_VALUE;
		}

		return System.currentTimeMillis() - activityDate.getTime();
	}

}
