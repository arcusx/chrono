/**
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
 * http://www.arcusx.com
 * mailto:info@arcusx.com
 */

package com.arcusx.chrono.tests;

import java.util.*;
import junit.framework.*;
import com.arcusx.chrono.*;

/**
 * 
 * 
 * 
 * Created 20.05.2003, 15:15:45.
 * 
 * @author conni
 * @version $Id$
 */
public class MonthSequenceTestCase extends TestCase
{

	public void testInnerYearMonths() throws Exception
	{
		MonthSequence period = new MonthSequence(new Month(2000, Calendar.JANUARY), new Month(2000, Calendar.DECEMBER));
		assertEquals(12, period.size());

		period = new MonthSequence(new Month(2000, Calendar.JANUARY), new Month(2000, Calendar.JUNE));
		assertEquals(6, period.size());

		period = new MonthSequence(new Month(2000, Calendar.JULY), new Month(2000, Calendar.DECEMBER));
		assertEquals(6, period.size());

		period = new MonthSequence(new Month(2000, Calendar.DECEMBER), new Month(2000, Calendar.DECEMBER));
		assertEquals(1, period.size());
	}

	public void testYearSpanningMonths() throws Exception
	{
		MonthSequence period = new MonthSequence(new Month(2000, Calendar.JANUARY), new Month(2001, Calendar.DECEMBER));
		assertEquals(24, period.size());

		period = new MonthSequence(new Month(2000, Calendar.JANUARY), new Month(2001, Calendar.DECEMBER));
		assertEquals(24, period.size());
	}

	public void testMonthsIteratorOne()
	{
		MonthSequence months = new MonthSequence(new Month(2000, Month.JANUARY), new Month(2000, Month.JANUARY));
		List monthList = new ArrayList();
		Iterator iter = months.iterator();
		while (iter.hasNext())
		{
			Month curr = (Month) iter.next();
			monthList.add(curr);
		}

		// check count
		assertEquals("Iterating on one months must return 1 month.", 1, monthList.size());

		assertEquals("Month at " + 0 + " must have month " + Month.JANUARY, ((Month) monthList.get(0)).getMonthValue(),
				Month.JANUARY);
	}

	public void testMonthsIteratorMany()
	{
		MonthSequence months = new MonthSequence(new Month(2000, Month.JANUARY), new Month(2000, Month.DECEMBER));
		List monthList = new ArrayList();
		Iterator iter = months.iterator();
		while (iter.hasNext())
		{
			Month curr = (Month) iter.next();
			monthList.add(curr);
		}

		// check count
		assertEquals("Iterating on year must return 12 months.", monthList.size(), 12);

		// check each month
		for (int i = 0; i < monthList.size(); ++i)
		{
			Month curr = (Month) monthList.get(i);
			assertEquals("Month at " + i + " must have month " + i, curr.getMonthValue(), i);
			assertEquals("Month at " + i + " must have year 2000", curr.getYearValue(), 2000);
		}
	}

	public void testContains()
	{
		Day entryDate = new Day(2003, 0, 1); // jan, 1st
		Day exitDate = new Day(2003, 5, 30); // jun, 30st
		MonthSequence period = new MonthSequence(entryDate.getMonth(), exitDate.getMonth());
		Day testStartDay = new Day(2002, 11, 31);
		Day testEndDay = new Day(2003, 6, 1);
		Day testCurrDay = testStartDay;
		while (testCurrDay.beforeOrEqual(testEndDay))
		{
			if (testCurrDay.equals(testStartDay) || testCurrDay.equals(testEndDay))
				assertFalse("Curr month " + testCurrDay.getMonth() + " must not be in period." + period, period
						.contains(testCurrDay.getMonth()));
			else
				assertTrue("Curr month " + testCurrDay.getMonth() + " must be in period." + period, period
						.contains(testCurrDay.getMonth()));

			testCurrDay = testCurrDay.add(1);
		}
	}

	public void testContainsMonths() throws Exception
	{
		MonthSequence year = new MonthSequence(new Month(2003, Month.JANUARY), new Month(2003, Month.DECEMBER));
		MonthSequence firstQuarter = new MonthSequence(new Month(2003, Month.JANUARY), new Month(2003, Month.MARCH));
		MonthSequence secondQuarter = new MonthSequence(new Month(2003, Month.APRIL), new Month(2003, Month.JUNE));
		assertTrue(year.contains(firstQuarter));
		assertTrue(year.contains(secondQuarter));
		assertFalse(firstQuarter.contains(year));
		assertFalse(secondQuarter.contains(year));
	}

	/**
	 * This has been reported by Flo.
	 */
	public void testNewMonths1() throws Exception
	{
		MonthSequence months = new MonthSequence(new Month(2004, 0), 1);
		final int expectedStartYear = 2004;
		final int expectedStartMonth = 0;
		final int expectedEndYear = 2004;
		final int expectedEndMonth = 0;
		assertEquals(expectedStartYear, months.getFirstMonth().getYearValue());
		assertEquals(expectedStartMonth, months.getFirstMonth().getMonthValue());
		assertEquals(expectedEndYear, months.getLastMonth().getYearValue());
		assertEquals(expectedEndMonth, months.getLastMonth().getMonthValue());
	}

	/**
	 * This has been reported by Flo.
	 */
	public void testNewMonths12() throws Exception
	{
		MonthSequence months = new MonthSequence(new Month(2004, 0), 12);
		final int expectedStartYear = 2004;
		final int expectedStartMonth = 0;
		final int expectedEndYear = 2004;
		final int expectedEndMonth = 11;
		assertEquals(expectedStartYear, months.getFirstMonth().getYearValue());
		assertEquals(expectedStartMonth, months.getFirstMonth().getMonthValue());
		assertEquals(expectedEndYear, months.getLastMonth().getYearValue());
		assertEquals(expectedEndMonth, months.getLastMonth().getMonthValue());
	}

	/**
	 * This has been reported by Flo.
	 */
	/*
	 public void testNewMonthsWithZeroYear() throws Exception
	 {
	 Months months = new Months(new Month(0,0),5);
	 final int expectedStartYear = 0;
	 final int expectedStartMonth = 0;
	 final int expectedEndYear = 0;
	 final int expectedEndMonth = 5;
	 assertEquals(expectedStartYear, months.getFirstMonth().getYearValue());
	 assertEquals(expectedStartMonth, months.getFirstMonth().getMonthValue());
	 assertEquals(expectedEndYear, months.getLastMonth().getYearValue());
	 assertEquals(expectedEndMonth, months.getLastMonth().getMonthValue());
	 }
	 */

	/**
	 * This has been reported by Flo.
	 */
	/*
	 public void testInvalidNegativeCalc1() throws Exception
	 {
	 Months months = new Months(new Month(-10, 1), 10);
	 final int expectedStartYear = -10;
	 final int expectedStartMonth = 1;
	 final int expectedEndYear = -10;
	 final int expectedEndMonth = 11;
	 assertEquals(expectedStartYear, months.getFirstMonth().getYearValue());
	 assertEquals(expectedStartMonth, months.getFirstMonth().getMonthValue());
	 assertEquals(expectedEndYear, months.getLastMonth().getYearValue());
	 assertEquals(expectedEndMonth, months.getLastMonth().getMonthValue());
	 }
	 */

	public void testOpenMonths() throws Exception
	{
		MonthSequence openMonths = new OpenMonthSequence(new Month(2003, 0));

		assertNotNull(openMonths.getFirstMonth());
	}

	public void testOpenMonthsGetLastMonthFails() throws Exception
	{
		MonthSequence openMonths = new OpenMonthSequence(new Month(2003, 0));
		assertNull(openMonths.getLastMonth());
	}

	public void testOpenMonthsContains() throws Exception
	{
		MonthSequence openMonths = new OpenMonthSequence(new Month(2003, 0));
		assertTrue(openMonths.contains(new Day(2003, 0, 1)));
		assertTrue(openMonths.contains(new Month(2003, 0)));
		assertFalse(openMonths.contains(new Day(2002, 0, 1)));
		assertFalse(openMonths.contains(new Month(2002, 0)));
	}

	public void testOpenMonthsEquals() throws Exception
	{
		MonthSequence oneOpenMonths = new OpenMonthSequence(new Month(2003, 0));
		MonthSequence otherOpenMonths = new OpenMonthSequence(new Month(2003, 0));
		assertEquals(oneOpenMonths, otherOpenMonths);
	}

	public void testOpenMonthsLimit() throws Exception
	{
		MonthSequence openMonths = new OpenMonthSequence(new Month(2003, 0));

		// limited min, open max
		Month min = new Month(2003, 1);
		MonthSequence limitedMonths = openMonths.limitBy(min, null);
		MonthSequence expectedMonths = new OpenMonthSequence(new Month(2003, 1));
		assertEquals(expectedMonths, limitedMonths);

		// limited min and max
		min = new Month(2003, 1);
		Month max = new Month(2003, 6);
		limitedMonths = openMonths.limitBy(min, max);
		expectedMonths = new MonthSequence(new Month(2003, 1), new Month(2003, 6));
		assertEquals(expectedMonths, limitedMonths);

		// limited none
		limitedMonths = openMonths.limitBy(null, null);
		expectedMonths = new OpenMonthSequence(new Month(2003, 0));
		assertEquals(expectedMonths, limitedMonths);

		// open min, limited max
		max = new Month(2003, 6);
		limitedMonths = openMonths.limitBy(null, max);
		expectedMonths = new MonthSequence(new Month(2003, 0), max);
		assertEquals(expectedMonths, limitedMonths);
	}

	public void testHeadDownMonthLimit() throws Exception
	{
		try
		{
			MonthSequence months = new MonthSequence(new Month(2003, Month.JANUARY), 12);
			months.limitBy(new Month(2003, Month.MAY), new Month(2003, Month.JANUARY));

			fail("Limit by wrong period should raise an exception.");
		}
		catch (Exception ex)
		{
			// all right
		}
	}

	public void testOneMonthLimit() throws Exception
	{
		MonthSequence months = new MonthSequence(new Month(2003, Month.JANUARY), 12);
		MonthSequence limitedMonths = months.limitBy(new Month(2003, Month.MAY), new Month(2003, Month.MAY));
		MonthSequence expectedMonths = new MonthSequence(new Month(2003, Month.MAY), 1);
		assertEquals(expectedMonths, limitedMonths);
	}

	public void testEquals() throws Exception
	{
		MonthSequence one = new MonthSequence(new Month(2003, Month.JANUARY), new Month(2003, Month.DECEMBER));
		MonthSequence other = new MonthSequence(new Month(2003, Month.JANUARY), new Month(2003, Month.DECEMBER));
		assertEquals(one, other);
		assertEquals(other, one);
	}

	public void testNotEquals() throws Exception
	{
		MonthSequence one = new MonthSequence(new Month(2003, Month.JANUARY), new Month(2003, Month.DECEMBER));
		MonthSequence other = new MonthSequence(new Month(2003, Month.FEBRUARY), new Month(2003, Month.DECEMBER));
		assertFalse(one.equals(other));
		assertFalse(other.equals(one));
	}

	public void testNotEqualsOpenAndLimitedMonths() throws Exception
	{
		MonthSequence one = new OpenMonthSequence(new Month(2002, Month.DECEMBER));
		MonthSequence other = new MonthSequence(new Month(2002, Month.DECEMBER), new Month(2003, Month.DECEMBER));
		assertFalse(one.equals(other));
		assertFalse(other.equals(one));
	}

	public void testValueOf() throws Exception
	{
		MonthSequence x = new MonthSequence(new Month(2001, 0), new Month(2025, 8));
		MonthSequence result = MonthSequence.valueOf(x.toString());
		assertEquals(x, result);
	}

	public void testClosedSequencesOverlap() throws Exception
	{
		MonthSequence one = new MonthSequence(new Month(2003, Month.JANUARY), new Month(2003, Month.DECEMBER));
		MonthSequence other = new MonthSequence(new Month(2003, Month.FEBRUARY), new Month(2003, Month.DECEMBER));
		assertTrue(one.overlaps(other));
		assertTrue(other.overlaps(one));
	}

	public void testClosedSequencesNoOverlap() throws Exception
	{
		MonthSequence one = new MonthSequence(new Month(2003, Month.JANUARY), new Month(2003, Month.MARCH));
		MonthSequence other = new MonthSequence(new Month(2003, Month.APRIL), new Month(2003, Month.DECEMBER));
		assertFalse(one.overlaps(other));
		assertFalse(other.overlaps(one));
	}

	public void testOpenSequenceOverlapsClosedSequence() throws Exception
	{
		MonthSequence one = new MonthSequence(new Month(2003, Month.JANUARY), new Month(2003, Month.DECEMBER));
		MonthSequence other = new OpenMonthSequence(new Month(2003, Month.APRIL));
		assertTrue(other.overlaps(one));
		//		assertTrue(one.overlaps(other));
	}

	public void testOpenSequenceNotOverlapsClosedSequence() throws Exception
	{
		MonthSequence one = new MonthSequence(new Month(2003, Month.JANUARY), new Month(2003, Month.MARCH));
		MonthSequence other = new OpenMonthSequence(new Month(2003, Month.APRIL));
		assertFalse(other.overlaps(one));
		//		assertFalse(one.overlaps(other));
	}

	public void testOpenSequencesOverlap() throws Exception
	{
		MonthSequence one = new OpenMonthSequence(new Month(2003, Month.JANUARY));
		MonthSequence other = new OpenMonthSequence(new Month(2003, Month.APRIL));
		assertTrue(one.overlaps(other));
		assertTrue(other.overlaps(one));
	}
}