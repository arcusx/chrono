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
public class MonthsTestCase extends TestCase
{
	public void testInnerYearMonths() throws Exception
	{
		Months period = new Months(new Month(2000, Calendar.JANUARY), new Month(2000, Calendar.DECEMBER));
		assertEquals(12, period.size());

		period = new Months(new Month(2000, Calendar.JANUARY), new Month(2000, Calendar.JUNE));
		assertEquals(6, period.size());

		period = new Months(new Month(2000, Calendar.JULY), new Month(2000, Calendar.DECEMBER));
		assertEquals(6, period.size());

		period = new Months(new Month(2000, Calendar.DECEMBER), new Month(2000, Calendar.DECEMBER));
		assertEquals(1, period.size());
	}

	public void testYearSpanningMonths() throws Exception
	{
		Months period = new Months(new Month(2000, Calendar.JANUARY), new Month(2001, Calendar.DECEMBER));
		assertEquals(24, period.size());

		period = new Months(new Month(2000, Calendar.JANUARY), new Month(2001, Calendar.DECEMBER));
		assertEquals(24, period.size());
	}

	public void testMonthsIteratorOne()
	{
		Months months = new Months(new Month(2000, Month.JANUARY), new Month(2000, Month.JANUARY));
		List monthList = new ArrayList();
		Iterator iter = months.iterator();
		while (iter.hasNext())
		{
			Month curr = (Month) iter.next();
			monthList.add(curr);
		}

		// check count
		assertEquals("Iterating on one months must return 1 month.", monthList.size(), 1);

		assertEquals("Month at " + 0 + " must have month " + Month.JANUARY, ((Month) monthList.get(0)).getMonthValue(), Month.JANUARY);
	}

	public void testMonthsIteratorMany()
	{
		Months months = new Months(new Month(2000, Month.JANUARY), new Month(2000, Month.DECEMBER));
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
		Months period = new Months(entryDate.getMonth(), exitDate.getMonth());
		Day testStartDay = new Day(2002, 11, 31);
		Day testEndDay = new Day(2003, 6, 1);
		Day testCurrDay = testStartDay;
		while (testCurrDay.beforeOrEqual(testEndDay))
		{
			if (testCurrDay.equals(testStartDay) || testCurrDay.equals(testEndDay))
				assertFalse("Curr month " + testCurrDay.getMonth() + " must not be in period." + period, period.contains(testCurrDay.getMonth()));
			else
				assertTrue("Curr month " + testCurrDay.getMonth() + " must be in period." + period, period.contains(testCurrDay.getMonth()));
				
			testCurrDay = testCurrDay.add( 1 );
		}
	}}
