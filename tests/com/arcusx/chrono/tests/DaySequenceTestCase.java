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

import com.arcusx.chrono.*;

import junit.framework.*;

/**
 *
 * @author david
 * @version $Id$
 */
public class DaySequenceTestCase extends TestCase
{

	protected void setUp() throws Exception
	{
		super.setUp();
	}

	public void testSizeEmpty() throws Exception
	{
		Day first = new Day(2006, Month.JANUARY, 1);
		Day last = new Day(2006, Month.JANUARY, 1);

		DaySequence seq = new DaySequence(first, last);
		assertTrue(seq.size() == 1);
	}

	public void testSizeOne() throws Exception
	{
		Day first = new Day(2006, Month.JANUARY, 1);
		Day last = new Day(2006, Month.JANUARY, 2);

		DaySequence seq = new DaySequence(first, last);
		assertTrue(seq.size() == 2);
	}

	public void testSizeSome() throws Exception
	{
		Day first = new Day(2006, Month.JANUARY, 1);
		Day last = new Day(2006, Month.JANUARY, 18);

		DaySequence seq = new DaySequence(first, last);
		assertTrue(seq.size() == 18);
	}

	public void testSizeOverMonth() throws Exception
	{
		Day first = new Day(2006, Month.JANUARY, 1);
		Day last = new Day(2006, Month.FEBRUARY, 1);

		DaySequence seq = new DaySequence(first, last);
		assertTrue(seq.size() == 32);
	}

	public void testSizeOverSomeMonth() throws Exception
	{
		Day first = new Day(2006, Month.JANUARY, 1);
		Day last = new Day(2006, Month.MARCH, 18);

		DaySequence seq = new DaySequence(first, last);
		assertTrue(seq.size() == 77); // Jan: 31 + Feb: 28 + Mar: 18
	}

	public void testSizeAgainOverSomeMonth() throws Exception
	{

		Day first = new Day(2006, Month.JANUARY, 2);
		Day last = new Day(2006, Month.MARCH, 18);

		DaySequence seq = new DaySequence(first, last);
		assertTrue(seq.size() == 76); // Jan: 30 + Feb: 28 + Mar: 18

	}

	public void testSizeOverYear() throws Exception
	{
		Day first = new Day(2006, Month.JANUARY, 1);
		Day last = new Day(2007, Month.APRIL, 22);

		// 01.01.2006 till 22.02.2007
		// 2006: 365 + 2007: Jan: 31 + Feb: 28 + Mar: 31 + Apr: 22 = 477

		DaySequence seq = new DaySequence(first, last);
		assertTrue(seq.size() == 477);
	}

	public void testSizeMultipleYears() throws Exception
	{
		Day first = new Day(2006, Month.JANUARY, 1);
		Day last = new Day(2009, Month.APRIL, 22);

		// 01.01.2006 till 22.02.2007
		// 2006: 365 +
		// 2007: 365 +
		// 2008: 366 +
		// 2009: Jan: 31 + Feb: 28 + Mar: 31 + Apr: 22 = 112
		// = 1208

		DaySequence seq = new DaySequence(first, last);
		assertTrue(seq.size() == 1208);
	}

	public void testSizeLeapYear() throws Exception
	{
		Day first = new Day(2008, Month.FEBRUARY, 27);
		Day last = new Day(2008, Month.MARCH, 1);

		DaySequence seq = new DaySequence(first, last);
		assertTrue(seq.size() == 4);
	}

	public void testSizeOverLeapYearStart() throws Exception
	{
		Day first = new Day(2004, Month.FEBRUARY, 27);
		Day last = new Day(2005, Month.MARCH, 1);

		DaySequence seq = new DaySequence(first, last);
		System.err.println(seq.size());
		assertTrue(seq.size() == 369);
	}

	public void testSizeOverLeapYearEnd() throws Exception
	{
		Day first = new Day(2007, Month.FEBRUARY, 27);
		Day last = new Day(2008, Month.MARCH, 1);

		DaySequence seq = new DaySequence(first, last);
		assertTrue(seq.size() == 369);
	}

	public void testCountDaysinMonths() throws Exception
	{

		Day first = new Day(2006, Month.FEBRUARY, 1);
		Day last = new Day(2007, Month.JANUARY, 31);

		MonthSequence months = new MonthSequence(first.getMonth(), last.getMonth());
		DaySequence seq = new DaySequence(first, last);

		assertTrue(seq.calcDays(months) == 365);
	}

	public void testCountDaysinMonthsLeapYear() throws Exception
	{

		Day first = new Day(2004, Month.FEBRUARY, 1);
		Day last = new Day(2005, Month.JANUARY, 31);

		MonthSequence months = new MonthSequence(first.getMonth(), last.getMonth());
		DaySequence seq = new DaySequence(first, last);

		assertTrue(seq.calcDays(months) == 366);
	}
}
