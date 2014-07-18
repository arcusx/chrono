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

import junit.framework.TestCase;

import com.arcusx.chrono.Day;
import com.arcusx.chrono.Month;
import com.arcusx.chrono.MonthOfYear;

/**
 * 
 * 
 * 
 * Created 02.10.2003, 17:16:13.
 * 
 * @author conni
 * @version $Id$
 */
public class DayTestCase extends TestCase
{

	public void testEquals() throws Exception
	{
		Day one = new Day(2000, 0, 1);
		Day otherEq = new Day(2000, 0, 1);
		assertEquals("Days must be equal", one, otherEq);

		Day otherNe = new Day(2000, 0, 2);
		assertFalse("Days must be not equal", one.equals(otherNe));
	}

	public void testBefore() throws Exception
	{
		Day one = new Day(2000, 0, 1);
		Day other = new Day(2000, 0, 2);
		assertTrue("Day must be before", one.before(other));
	}

	public void testBeforeOrEqual() throws Exception
	{
		Day one = new Day(2000, 0, 1);
		Day other = new Day(2000, 0, 2);
		assertTrue("Day must be before or equal", one.beforeOrEqual(other));
	}

	public void testCompareTo() throws Exception
	{
		Day one = new Day(2000, MonthOfYear.JANUARY, 1);
		Day other = new Day(2000, MonthOfYear.JANUARY, 2);
		assertEquals("Day must be before", -1, one.compareTo(other));
		assertEquals("Day must be after", 1, other.compareTo(one));
		assertEquals("Days must be equal", 0, one.compareTo(one));
		assertEquals("Days must be after", 0, other.compareTo(other));
	}

	/**
	 * Bug reported from Flo.
	 */
	public void testNewDay50th1st2004Fail() throws Exception
	{
		try
		{
			new Day(2004, 1, 50);
			fail("50.2.2004 should raise an error");
		}
		catch (IllegalArgumentException ex)
		{
			// all right
		}
	}

	public void testValueOf() throws Exception
	{
		Day x = new Day(2001, 0, 14);
		Day result = Day.valueOf(x.toString());
		assertEquals(x, result);
	}

	public void testEarliestOf() throws Exception
	{
		Day oneDay = new Day(2000, Month.JANUARY, 1);
		Day otherDay = new Day(1990, Month.JULY, 20);

		Day earlierDay = Day.earliestOf(oneDay, otherDay);

		assertNotNull(earlierDay);
		assertEquals(otherDay, earlierDay);
	}

	public void testEarliestOfWithNullValuesNotAllowed() throws Exception
	{
		Day oneDay = new Day(2000, Month.JANUARY, 1);
		Day otherDay = null;

		try
		{
			Day earlierDay = Day.earliestOf(oneDay, otherDay);
			fail("IllegalArgumentException expected but not caught.");
		}
		catch (IllegalArgumentException ex)
		{
			// ok expected
		}

	}

	public void testEarliestOfWithNullValuesAllowed() throws Exception
	{
		Day oneDay = new Day(2000, Month.JANUARY, 1);
		Day otherDay = null;
		Day thirdDay = new Day(1990, Month.JULY, 20);

		Day earlierDay = Day.earliestOf(new Day[] { oneDay, otherDay, thirdDay}, true);
		assertNotNull(earlierDay);
		assertEquals(thirdDay, earlierDay);
	}

	public void testEarliestOfWithOnlyNullValuesAndNullAllowed() throws Exception
	{
		Day oneDay = null;
		Day otherDay = null;
		Day thirdDay = null;

		Day earlierDay = Day.earliestOf(new Day[] { oneDay, otherDay, thirdDay}, true);
		assertNull(earlierDay);
	}
}