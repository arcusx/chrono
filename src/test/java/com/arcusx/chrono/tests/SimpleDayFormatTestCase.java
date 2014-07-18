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
 * Fax.: +49 (0)40.333 102 93 
 * http://www.arcusx.com
 * mailto:info@arcusx.com
 *
 */

package com.arcusx.chrono.tests;

import java.util.Locale;

import junit.framework.TestCase;

import com.arcusx.chrono.Day;
import com.arcusx.chrono.DayFormat;
import com.arcusx.chrono.MonthOfYear;
import com.arcusx.chrono.SimpleDayFormat;

/**
 * 
 * Created on 12.06.2004, 16:07:51.
 *
 * @author conni
 * @version $Id$
 */
public class SimpleDayFormatTestCase extends TestCase
{

	public void testParseWithZeros() throws Exception
	{
		Day day = SimpleDayFormat.INSTANCE.parse("2003-03-01");
		assertEquals(new Day(2003, 2, 1), day);
	}

	public void testParseNoZeros() throws Exception
	{
		Day day = SimpleDayFormat.INSTANCE.parse("2003-3-1");
		assertEquals(new Day(2003, 2, 1), day);
	}

	public void testParseWithMonthZero() throws Exception
	{
		Day day = SimpleDayFormat.INSTANCE.parse("2003-03-1");
		assertEquals(new Day(2003, 2, 1), day);
	}

	public void testParseWithDayZero() throws Exception
	{
		Day day = SimpleDayFormat.INSTANCE.parse("2003-3-01");
		assertEquals(new Day(2003, 2, 1), day);
	}

	public void testFormat() throws Exception
	{
		Day day = new Day(2003, 2, 1);
		String dayStr = SimpleDayFormat.INSTANCE.format(day);
		assertEquals("2003-03-01", dayStr);
	}

	public void testParseGermany() throws Exception
	{
		Day expectedDay = new Day(2003, MonthOfYear.FEBRUARY, 1);
		DayFormat dayFormat = DayFormat.newInstanceFor(Locale.GERMANY);
		Day day = dayFormat.parse("01.02.2003");
		assertEquals(expectedDay, day);

		dayFormat = DayFormat.newInstanceFor(Locale.GERMAN);
		day = dayFormat.parse("01.02.2003");
		assertEquals(expectedDay, day);
	}

	public void testParseUk() throws Exception
	{
		Day expectedDay = new Day(2003, MonthOfYear.FEBRUARY, 1);
		DayFormat dayFormat = DayFormat.newInstanceFor(Locale.UK);
		Day day = dayFormat.parse("02/01/2003");
		assertEquals(expectedDay, day);

		dayFormat = DayFormat.newInstanceFor(Locale.ENGLISH);
		day = dayFormat.parse("02/01/2003");
		assertEquals(expectedDay, day);
	}

	public void testParseUs() throws Exception
	{
		Day expectedDay = new Day(2003, MonthOfYear.FEBRUARY, 1);
		DayFormat dayFormat = DayFormat.newInstanceFor(Locale.US);
		Day day = dayFormat.parse("01/02/2003");
		assertEquals(expectedDay, day);
	}

}