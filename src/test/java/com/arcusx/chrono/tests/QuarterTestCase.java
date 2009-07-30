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

import com.arcusx.chrono.*;

import junit.framework.*;

/**
 * 
 * 
 * Created on 25.02.2004
 * 
 * @author conni
 * @version $Id$
 * 
 */
public class QuarterTestCase extends TestCase
{
	public void testNewInstance() throws Exception
	{
		Month firstMonth = new Month(2001, MonthOfYear.JANUARY);
		Month lastMonth = new Month(2001, MonthOfYear.MARCH);
		Quarter result = new Quarter(2001, Quarter.FIRST);
		assertEquals(2001, result.getYearValue());
		assertEquals(Quarter.FIRST, result.getQuarterValue());
		assertEquals(3, result.toMonths().size());
		assertEquals(firstMonth, result.getFirstMonth());
		assertEquals(lastMonth, result.getLastMonth());
	}

	public void testValueOf() throws Exception
	{
		Month month = new Month(2001, MonthOfYear.APRIL);
		Quarter result = Quarter.valueOf(month);

		assertEquals(2001, result.getYearValue());
		assertEquals(Quarter.SECOND, result.getQuarterValue());
		assertEquals(3, result.toMonths().size());
		assertEquals(month, result.getFirstMonth());
		assertEquals(month.add(2), result.getLastMonth());
	}

	public void testEquals() throws Exception
	{
		Month month = new Month(2001, MonthOfYear.APRIL);
		Quarter oneQuarter = Quarter.valueOf(month);
		Quarter otherQuarter = new Quarter(2001, Quarter.SECOND);

		assertTrue(oneQuarter.equals(otherQuarter));
		assertTrue(otherQuarter.equals(oneQuarter));
		assertEquals(oneQuarter, otherQuarter);
		assertEquals(otherQuarter, oneQuarter);
	}

	public void testValueOfString() throws Exception
	{
		Month month = new Month(2001, MonthOfYear.APRIL);
		Quarter expected = Quarter.valueOf(month);

		String value = "Quarter{2001.1}";
		Quarter actual = new Quarter(2001, Quarter.SECOND);

		assertEquals(2001, actual.getYearValue());
		assertEquals(Quarter.SECOND, actual.getQuarterValue());
		assertEquals(expected, actual);
	}

	public void testAddition() throws Exception
	{
		Month month = new Month(2001, MonthOfYear.APRIL);
		Quarter result = Quarter.valueOf(month).add(3);

		assertEquals(2002, result.getYearValue());
		assertEquals(Quarter.FIRST, result.getQuarterValue());
		assertEquals(3, result.toMonths().size());
		assertEquals(new Month(2002, MonthOfYear.JANUARY), result.getFirstMonth());
		assertEquals(new Month(2002, MonthOfYear.MARCH), result.getLastMonth());
	}

	public void testSubtraction() throws Exception
	{
		Month month = new Month(2001, MonthOfYear.APRIL);
		Quarter result = Quarter.valueOf(month).subtract(4);

		assertEquals(2000, result.getYearValue());
		assertEquals(Quarter.SECOND, result.getQuarterValue());
		assertEquals(3, result.toMonths().size());
		assertEquals(new Month(2000, MonthOfYear.APRIL), result.getFirstMonth());
		assertEquals(new Month(2000, MonthOfYear.JUNE), result.getLastMonth());
	}
}
