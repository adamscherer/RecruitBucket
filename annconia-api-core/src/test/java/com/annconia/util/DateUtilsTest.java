package com.annconia.util;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.xml.datatype.XMLGregorianCalendar;

import org.junit.Test;

/**
 * 
 * @author jstuppy
 *
 */
public class DateUtilsTest {

	private static final String[] formats = new String[]{"MM/dd/yyyy", "yyyy-MM-dd"};
	
	@Test
	public void parseDate() throws ParseException {
		Calendar cal = Calendar.getInstance();
		cal.set(2003, 00, 02);
		
		Date Jan_2_2003 = DateUtils.getBeginningOfDay(cal.getTime());
		
		assertNotNull(DateUtils.parseDate("01/02/2003", formats));
		assertNotNull(DateUtils.parseDate("1/2/2003", formats));
		assertNotNull(DateUtils.parseDate("2003-01-02", formats));
		
		assertEquals(Jan_2_2003, DateUtils.parseDate("01/02/2003", formats));
		assertEquals(Jan_2_2003, DateUtils.parseDate("1/2/2003", formats));
		assertEquals(Jan_2_2003, DateUtils.parseDate("2003-01-02", formats));
	}
	
	@Test
	public void getBeginningOfDay() throws ParseException {
		Calendar cal = Calendar.getInstance();
		cal.set(2003, 00, 02);
		
		Date Jan_2_2003 = DateUtils.getBeginningOfDay(cal.getTime());
		
		assertEquals("January 02 2003 00:00:00:0", DateUtils.format(Jan_2_2003, "MMMM dd yyyy HH:mm:ss:S"));
	}
	
	@Test
	public void getEndOfDay() throws ParseException {
		Calendar cal = Calendar.getInstance();
		cal.set(2003, 00, 02);
		
		Date Jan_2_2003 = DateUtils.getEndOfDay(cal.getTime());
		
		assertEquals("January 02 2003 23:59:59:999", DateUtils.format(Jan_2_2003, "MMMM dd yyyy HH:mm:ss:S"));
	}
	
	@Test
	public void asXMLGregorianCalendar() {
		
		Date now = new Date();
		XMLGregorianCalendar xmlCal = DateUtils.asXMLGregorianCalendar(now);
		Calendar cal = Calendar.getInstance();
		cal.setTime(now);
		assertEquals(cal.get(Calendar.MONTH) + 1, xmlCal.getMonth());
		assertEquals(cal.get(Calendar.DAY_OF_MONTH), xmlCal.getDay());
		assertEquals(cal.get(Calendar.YEAR), xmlCal.getYear());
		assertEquals(cal.get(Calendar.MINUTE), xmlCal.getMinute());
		assertEquals(cal.get(Calendar.HOUR_OF_DAY), xmlCal.getHour());
	}
	
	// XXX move to DateRangeTest
//	@Test
//	public void getNumDays() {
//		Date feb_1_2012 = DateUtils.parseDateOrNull("02/01/2012", "MM/dd/yyyy");
//		
//		assertEquals(1, new DateRange(Type.DAILY, feb_1_2012).getNumDays());
//		assertEquals(7, new DateRange(Type.WEEKLY, feb_1_2012).getNumDays());
//		assertEquals(29, new DateRange(Type.MONTHLY, feb_1_2012).getNumDays());
//		assertEquals(366, new DateRange(Type.YEARLY, feb_1_2012).getNumDays());
//	}
	
	private static Date getDay(String str) {
		return DateUtils.parseDateOrNull(str, formats);
	}
	
	@Test
	public void closest() {
		Date dec_05_2011 = getDay("12/05/2011");
		Date dec_11_2011 = getDay("12/11/2011");
		Date dec_12_2011 = getDay("12/12/2011");
		Date dec_13_2011 = getDay("12/13/2011");
		Date dec_19_2011 = getDay("12/19/2011");
		Date dec_08_2011 = getDay("12/08/2011");
		Date dec_15_2011 = getDay("12/15/2011");

		Date dec_11_2011_N_M_0 = DateUtils.getNextDay(dec_11_2011, Calendar.MONDAY, false);
		Date dec_11_2011_N_M_1 = DateUtils.getNextDay(dec_11_2011, Calendar.MONDAY, true);
		Date dec_11_2011_P_M_0 = DateUtils.getPriorDay(dec_11_2011, Calendar.MONDAY, false);
		Date dec_11_2011_P_M_1 = DateUtils.getPriorDay(dec_11_2011, Calendar.MONDAY, true);
		Date dec_11_2011_N_R_0 = DateUtils.getNextDay(dec_11_2011, Calendar.THURSDAY, false);
		Date dec_11_2011_N_R_1 = DateUtils.getNextDay(dec_11_2011, Calendar.THURSDAY, true);
		Date dec_11_2011_P_R_0 = DateUtils.getPriorDay(dec_11_2011, Calendar.THURSDAY, false);
		Date dec_11_2011_P_R_1 = DateUtils.getPriorDay(dec_11_2011, Calendar.THURSDAY, true);

		Date dec_12_2011_N_M_0 = DateUtils.getNextDay(dec_12_2011, Calendar.MONDAY, false);
		Date dec_12_2011_N_M_1 = DateUtils.getNextDay(dec_12_2011, Calendar.MONDAY, true);
		Date dec_12_2011_P_M_0 = DateUtils.getPriorDay(dec_12_2011, Calendar.MONDAY, false);
		Date dec_12_2011_P_M_1 = DateUtils.getPriorDay(dec_12_2011, Calendar.MONDAY, true);
		Date dec_12_2011_N_R_0 = DateUtils.getNextDay(dec_12_2011, Calendar.THURSDAY, false);
		Date dec_12_2011_N_R_1 = DateUtils.getNextDay(dec_12_2011, Calendar.THURSDAY, true);
		Date dec_12_2011_P_R_0 = DateUtils.getPriorDay(dec_12_2011, Calendar.THURSDAY, false);
		Date dec_12_2011_P_R_1 = DateUtils.getPriorDay(dec_12_2011, Calendar.THURSDAY, true);

		Date dec_13_2011_N_M_0 = DateUtils.getNextDay(dec_13_2011, Calendar.MONDAY, false);
		Date dec_13_2011_N_M_1 = DateUtils.getNextDay(dec_13_2011, Calendar.MONDAY, true);
		Date dec_13_2011_P_M_0 = DateUtils.getPriorDay(dec_13_2011, Calendar.MONDAY, false);
		Date dec_13_2011_P_M_1 = DateUtils.getPriorDay(dec_13_2011, Calendar.MONDAY, true);
		Date dec_13_2011_N_R_0 = DateUtils.getNextDay(dec_13_2011, Calendar.THURSDAY, false);
		Date dec_13_2011_N_R_1 = DateUtils.getNextDay(dec_13_2011, Calendar.THURSDAY, true);
		Date dec_13_2011_P_R_0 = DateUtils.getPriorDay(dec_13_2011, Calendar.THURSDAY, false);
		Date dec_13_2011_P_R_1 = DateUtils.getPriorDay(dec_13_2011, Calendar.THURSDAY, true);

		assertEquals(dec_12_2011, dec_11_2011_N_M_0);
		assertEquals(dec_12_2011, dec_11_2011_N_M_1);
		assertEquals(dec_05_2011, dec_11_2011_P_M_0);
		assertEquals(dec_05_2011, dec_11_2011_P_M_1);
		assertEquals(dec_15_2011, dec_11_2011_N_R_0);
		assertEquals(dec_15_2011, dec_11_2011_N_R_1);
		assertEquals(dec_08_2011, dec_11_2011_P_R_0);
		assertEquals(dec_08_2011, dec_11_2011_P_R_1);

		assertEquals(dec_19_2011, dec_12_2011_N_M_0);
		assertEquals(dec_12_2011, dec_12_2011_N_M_1);
		assertEquals(dec_05_2011, dec_12_2011_P_M_0);
		assertEquals(dec_12_2011, dec_12_2011_P_M_1);
		assertEquals(dec_15_2011, dec_12_2011_N_R_0);
		assertEquals(dec_15_2011, dec_12_2011_N_R_1);
		assertEquals(dec_08_2011, dec_12_2011_P_R_0);
		assertEquals(dec_08_2011, dec_12_2011_P_R_1);

		assertEquals(dec_19_2011, dec_13_2011_N_M_0);
		assertEquals(dec_19_2011, dec_13_2011_N_M_1);
		assertEquals(dec_12_2011, dec_13_2011_P_M_0);
		assertEquals(dec_12_2011, dec_13_2011_P_M_1);
		assertEquals(dec_15_2011, dec_13_2011_N_R_0);
		assertEquals(dec_15_2011, dec_13_2011_N_R_1);
		assertEquals(dec_08_2011, dec_13_2011_P_R_0);
		assertEquals(dec_08_2011, dec_13_2011_P_R_1);
	}
	
	@Test
	public void dayBefore() {
		Calendar c = Calendar.getInstance(TimeZone.getTimeZone("GMT"));

		c.set(Calendar.MONTH, 0);
		c.set(Calendar.DATE, 1);
		c.set(Calendar.YEAR, 2012);
		c.set(Calendar.HOUR_OF_DAY, 7);
		c.set(Calendar.MINUTE, 0);
		c.set(Calendar.SECOND, 0);
		
		Date jan_1_2012_gmt = c.getTime();
		
		{
			String gmt = DateUtils.format(jan_1_2012_gmt, "yyyy-MM-dd HH:mm:ss z", TimeZone.getTimeZone("GMT"));
			assertEquals("2012-01-01 07:00:00 GMT", gmt);
		}
		
		{
			Date pst = DateUtils.getDayOn(jan_1_2012_gmt, TimeZone.getTimeZone("America/Los_Angeles"));
			assertEquals("2011-12-31 00:00:00", DateUtils.format(pst, "yyyy-MM-dd HH:mm:ss"));
		}
		
		{
			Date cst = DateUtils.getDayOn(jan_1_2012_gmt, TimeZone.getTimeZone("America/Chicago"));
			assertEquals("2012-01-01 00:00:00", DateUtils.format(cst, "yyyy-MM-dd HH:mm:ss"));
		}
	}
	
	@Test
	public void dayAfter() {
		Calendar c = Calendar.getInstance(TimeZone.getTimeZone("America/Los_Angeles"));

		c.set(Calendar.MONTH, 11);
		c.set(Calendar.DATE, 31);
		c.set(Calendar.YEAR, 2011);
		c.set(Calendar.HOUR_OF_DAY, 22);
		c.set(Calendar.MINUTE, 0);
		c.set(Calendar.SECOND, 0);
		
		Date dec_31_2011_pst = c.getTime();
		
		{
			String pst = DateUtils.format(dec_31_2011_pst, "yyyy-MM-dd HH:mm:ss z", TimeZone.getTimeZone("America/Los_Angeles"));
			assertEquals("2011-12-31 22:00:00 PST", pst);
		}
		
		{
			Date mst = DateUtils.getDayOn(dec_31_2011_pst, TimeZone.getTimeZone("America/Denver"));
			assertEquals("2011-12-31 00:00:00", DateUtils.format(mst, "yyyy-MM-dd HH:mm:ss"));
		}
		
		{
			Date cst = DateUtils.getDayOn(dec_31_2011_pst, TimeZone.getTimeZone("America/Chicago"));
			assertEquals("2012-01-01 00:00:00", DateUtils.format(cst, "yyyy-MM-dd HH:mm:ss"));
		}
		
		{
			Date gmt = DateUtils.getDayOn(dec_31_2011_pst, TimeZone.getTimeZone("GMT"));
			assertEquals("2012-01-01 00:00:00", DateUtils.format(gmt, "yyyy-MM-dd HH:mm:ss"));
		}
	}
	
	@Test
	public void getDayOfNull() {
		Date now = new Date();
		
		Date dayOf = DateUtils.getDayOn(now, null);
		
		Date today = DateUtils.getBeginningOfDay(now);
		
		assertEquals(today, dayOf);
	}
	
	@Test
	public void getDaysBetween() {
		Date dec_1_2011 = DateUtils.parseDateOrNull("2011-12-01", DateUtils.YYYY_MM_DD);
		Date dec_2_2011 = DateUtils.parseDateOrNull("2011-12-02", DateUtils.YYYY_MM_DD);
		Date dec_3_2011 = DateUtils.parseDateOrNull("2011-12-03", DateUtils.YYYY_MM_DD);
		Date dec_4_2011 = DateUtils.parseDateOrNull("2011-12-04", DateUtils.YYYY_MM_DD);

		assertEquals(0, DateUtils.getDaysBetween(dec_1_2011, dec_1_2011));
		assertEquals(1, DateUtils.getDaysBetween(dec_1_2011, dec_2_2011));
		assertEquals(2, DateUtils.getDaysBetween(dec_1_2011, dec_3_2011));
		assertEquals(3, DateUtils.getDaysBetween(dec_1_2011, dec_4_2011));

		assertEquals(3, DateUtils.getDaysBetween(dec_4_2011, dec_1_2011));

		Date mar_09_2012 = DateUtils.parseDateOrNull("2012-03-09", DateUtils.YYYY_MM_DD);
		Date mar_10_2012 = DateUtils.parseDateOrNull("2012-03-10", DateUtils.YYYY_MM_DD);
		Date mar_11_2012 = DateUtils.parseDateOrNull("2012-03-11", DateUtils.YYYY_MM_DD);
		Date mar_12_2012 = DateUtils.parseDateOrNull("2012-03-12", DateUtils.YYYY_MM_DD);

		assertEquals(0, DateUtils.getDaysBetween(mar_09_2012, mar_09_2012));
		assertEquals(1, DateUtils.getDaysBetween(mar_09_2012, mar_10_2012));
		assertEquals(2, DateUtils.getDaysBetween(mar_09_2012, mar_11_2012));
		assertEquals(3, DateUtils.getDaysBetween(mar_09_2012, mar_12_2012));

		assertEquals(3, DateUtils.getDaysBetween(mar_12_2012, mar_09_2012));
	}
	
	@Test
	public void getMonthsBetween() {
		Date dec_1_2011 = DateUtils.parseDateOrNull("2011-12-01", DateUtils.YYYY_MM_DD);
		Date jan_2_2012 = DateUtils.parseDateOrNull("2012-01-02", DateUtils.YYYY_MM_DD);
		Date feb_2_2012 = DateUtils.parseDateOrNull("2012-02-02", DateUtils.YYYY_MM_DD);
		Date mar_2_2012 = DateUtils.parseDateOrNull("2012-03-02", DateUtils.YYYY_MM_DD);

		assertEquals(0, DateUtils.getMonthsBetwen(dec_1_2011, dec_1_2011));
		assertEquals(1, DateUtils.getMonthsBetwen(dec_1_2011, jan_2_2012));
		assertEquals(2, DateUtils.getMonthsBetwen(dec_1_2011, feb_2_2012));
		assertEquals(3, DateUtils.getMonthsBetwen(dec_1_2011, mar_2_2012));

		assertEquals(1, DateUtils.getMonthsBetwen(jan_2_2012, feb_2_2012));
		assertEquals(1, DateUtils.getMonthsBetwen(feb_2_2012, mar_2_2012));
	}
	
	@Test
	public void getMinutesBetween() {
		Date dec_1_2011 = DateUtils.parseDateOrNull("2011-12-01 00:00:00", "yyyy-MM-dd HH:mm:ss");
		Date dec_2_2011 = DateUtils.parseDateOrNull("2011-12-02 00:00:00", "yyyy-MM-dd HH:mm:ss");
		Date mar_11_2012 = DateUtils.parseDateOrNull("2012-03-11 00:00:00", "yyyy-MM-dd HH:mm:ss");
		Date mar_12_2012 = DateUtils.parseDateOrNull("2012-03-12 00:00:00", "yyyy-MM-dd HH:mm:ss");

		assertEquals(0, DateUtils.getTimeBetween(dec_1_2011, dec_1_2011, Calendar.MINUTE, true));
		assertEquals(24*60, DateUtils.getTimeBetween(dec_1_2011, dec_2_2011, Calendar.MINUTE, true));
		assertEquals(23*60, DateUtils.getTimeBetween(mar_11_2012, mar_12_2012, Calendar.MINUTE, true));
	}
	
	private static final Pattern START_DATE_REPLACEMENT = Pattern.compile("%D_([^%]+)%");
	
	@Test
	public void multiformat() {
		String body = "yyyy-mm-dd = %D_yyyy-MM-dd%; long = %D_MMMM dd, yyyy @ hh:mm:ss a%";
		Matcher matcher = START_DATE_REPLACEMENT.matcher(body);
		
		SimpleDateFormat format = new SimpleDateFormat();
		
		while (matcher.find()) {
			format.applyPattern(matcher.group(1));
			body = StringUtils.replace(body, matcher.group(), format.format(new Date()));
		}
		
		System.out.println(body);
	}
	
	@Test
	public void getEllapsedTime() throws InterruptedException {
		System.out.println("Ellaped time since now = " + DateUtils.getEllapsedTime(new Date()));
		
		Date today = DateUtils.getToday();
		long sinceToday = DateUtils.getEllapsedTime(today);
		System.out.println("Ellaped time since today = " + sinceToday);
		assertTrue("Expected getEllapsedTime(today) to be >= 0", sinceToday >= 0);

		Date tomorrow = DateUtils.getTomorrow();
		long sinceTomorrow = DateUtils.getEllapsedTime(tomorrow);
		System.out.println("Ellaped time since tomorrow = " + sinceTomorrow);
		assertTrue("Expected getEllapsedTime(tomorrow) to be < 0 or tomorrow is now today (run at midnight-ish)", sinceTomorrow < 0 || DateUtils.isToday(tomorrow));

		
		long sinceNull = DateUtils.getEllapsedTime(null);
		System.out.println("Ellaped time since NULL = " + sinceNull);
		assertEquals("Expected getEllapsedTime(null) to be = Long.MAX_VALUE", Long.MAX_VALUE, sinceNull);
	}
	
	@Test
	public void ageNullNull() {
		assertEquals(-1, DateUtils.getAge(null, null));
	}
	
	@Test
	public void ageInvalid() {
		assertEquals(-1, DateUtils.getAge(d("2012-02-27"), d("2012-02-26")));
	}
	
	@Test
	public void ageAfterBirthday() {
		assertEquals(20, DateUtils.getAge(d("1990-06-15"), d("2010-12-31")));
	}
	
	@Test
	public void ageAfterBirthday2() {
		assertEquals(20, DateUtils.getAge(d("1990-06-15"), d("2010-06-31")));
	}
	
	@Test
	public void ageOnBirthday() {
		assertEquals(20, DateUtils.getAge(d("1990-06-15"), d("2010-06-15")));
	}
	
	@Test
	public void ageBirthdayBirthday() {
		assertEquals(19, DateUtils.getAge(d("1990-06-15"), d("2010-01-01")));
	}
	
	@Test
	public void ageBirthdayBirthday2() {
		assertEquals(19, DateUtils.getAge(d("1990-06-15"), d("2010-06-01")));
	}
	
	private static Date d(String str) {
		return DateUtils.parseDateOrNull(str, DateUtils.YYYY_MM_DD);
	}
	
}
