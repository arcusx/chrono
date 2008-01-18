/**
 *
 * This software is written by arcus(x) GmbH and subject
 * to a contract between arcus(x) and its customer.
 *
 * This software stays property of arcus(x) unless differing
 * arrangements between arcus(x) and its customer apply.
 *
 * arcus(x) GmbH
 * Bergiusstrasse 27
 * D-22765 Hamburg, Germany
 *
 * Tel.: +49 (0)40.333 102 92
 * Fax.: +49 (0)40.333 102 93
 * http://www.arcusx.com
 * mailto:info@arcusx.com
 *
 */

package com.arcusx.chrono.tests;

import java.util.Calendar;
import java.util.GregorianCalendar;

import junit.framework.TestCase;

import com.arcusx.chrono.DayOfWeek;
import com.arcusx.chrono.MonthOfYear;

/**
 *
 * @author conni
 * @version $Id$
 */
public class DayOfWeekTestCase extends TestCase
{
	private static TestRecord[] testRecords = new TestRecord[] { //
	// new TestRecord(1, MonthOfYear.JANUARY, 1, DayOfWeek.MONDAY, Calendar.MONDAY), //
	// new TestRecord(1500, MonthOfYear.JANUARY, 1, DayOfWeek.MONDAY, Calendar.MONDAY), //
	new TestRecord(1977, MonthOfYear.MAY, 14, DayOfWeek.SATURDAY, Calendar.SATURDAY), //
			new TestRecord(2007, MonthOfYear.NOVEMBER, 18, DayOfWeek.SUNDAY, Calendar.SUNDAY), //
	};

	public DayOfWeekTestCase()
	{
	}

	public void testCalcDayOfWeek()
	{
		for (TestRecord curr : testRecords)
		{
			DayOfWeek calcedDayOfWeek = DayOfWeek.valueOf(curr.year, curr.month, curr.day);
			assertEquals(curr.expectedDayOfWeek, calcedDayOfWeek);

			Calendar cal = GregorianCalendar.getInstance();
			cal.clear();
			cal.set(Calendar.YEAR, curr.year);
			cal.set(Calendar.MONTH, curr.month.getMonthValue());
			cal.set(Calendar.DATE, curr.day);
			int dayOfWeekByCal = cal.get(Calendar.DAY_OF_WEEK);
			assertEquals(curr.expectedGregCalDayOfWeek, dayOfWeekByCal);
		}
	}

	private static class TestRecord
	{
		private int day;
		private MonthOfYear month;
		private int year;
		private DayOfWeek expectedDayOfWeek;
		private int expectedGregCalDayOfWeek;

		public TestRecord(int year, MonthOfYear month, int day, DayOfWeek expectedDayOfWeek,
				int expectedGregCalDayOfWeek)
		{
			this.year = year;
			this.month = month;
			this.day = day;
			this.expectedDayOfWeek = expectedDayOfWeek;
			this.expectedGregCalDayOfWeek = expectedGregCalDayOfWeek;
		}
	}
}
