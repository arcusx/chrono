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

import junit.framework.TestCase;

import com.arcusx.chrono.Day;
import com.arcusx.chrono.DaySequence;
import com.arcusx.chrono.MonthOfYear;
import com.arcusx.chrono.SimpleDaySequenceFormat;

/**
 * 
 * Created on 12.06.2004, 16:07:51.
 *
 * @author conni
 * @version $Id$
 */
public class SimpleDaySequenceFormatTestCase extends TestCase
{

	public void testParse() throws Exception
	{
		DaySequence days = new DaySequence( new Day(2003,MonthOfYear.JANUARY, 1), new Day(2003,MonthOfYear.JANUARY, 5) );
		DaySequence parsed = SimpleDaySequenceFormat.INSTANCE.parse("2003-01-01-2003-01-05");
		
		assertEquals(days,parsed);
	}

	public void testFormat() throws Exception
	{
		DaySequence days = new DaySequence( new Day(2003,MonthOfYear.JANUARY, 1), new Day(2003,MonthOfYear.JANUARY, 5) );
		String formatted = SimpleDaySequenceFormat.INSTANCE.format(days);
		String expected = "2003-01-01-2003-01-05";
		
		assertEquals(expected,formatted);
	}
}