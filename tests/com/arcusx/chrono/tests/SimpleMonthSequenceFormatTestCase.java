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

import java.util.*;

import junit.framework.*;

import com.arcusx.chrono.*;

/**
 * 
 * Created on 22.02.2005, 16:51:44.
 *
 * @author conni
 * @version $Id$
 */
public class SimpleMonthSequenceFormatTestCase extends TestCase
{
	public void testParseOneMonth() throws Exception
	{
		MonthSequence seq = SimpleMonthSequenceFormat.INSTANCE.parse("2004/01");
		Iterator iter = seq.getMonthsParts().iterator();
		assertEquals((Object) new Months(new Month(2004, MonthOfYear.JANUARY), 1), iter.next());
		assertTrue(!iter.hasNext());
	}

	public void testParseManyMonth() throws Exception
	{
		MonthSequence seq = SimpleMonthSequenceFormat.INSTANCE.parse("2004/01,2004/02,2004/04,2004/05");
		Iterator iter = seq.getMonthsParts().iterator();
		assertEquals((Object) new Months(new Month(2004, MonthOfYear.JANUARY), 2), iter.next());
		assertEquals((Object) new Months(new Month(2004, MonthOfYear.APRIL), 2), iter.next());
		assertTrue(!iter.hasNext());
	}

	public void testParseOneMonths() throws Exception
	{
		MonthSequence seq = SimpleMonthSequenceFormat.INSTANCE.parse("2004/01-2004/02");
		Iterator iter = seq.getMonthsParts().iterator();
		assertEquals((Object) new Months(new Month(2004, MonthOfYear.JANUARY), 2), iter.next());
		assertTrue(!iter.hasNext());
	}

	public void testParseManyMonths() throws Exception
	{
		MonthSequence seq = SimpleMonthSequenceFormat.INSTANCE.parse("2004/01-2004/02,2004/04-2004/05");
		Iterator iter = seq.getMonthsParts().iterator();
		assertEquals((Object) new Months(new Month(2004, MonthOfYear.JANUARY), 2), iter.next());
		assertEquals((Object) new Months(new Month(2004, MonthOfYear.APRIL), 2), iter.next());
		assertTrue(!iter.hasNext());
	}

	public void testParseManyMonthAndMonthsMixed() throws Exception
	{
		MonthSequence seq = SimpleMonthSequenceFormat.INSTANCE.parse("2004/01-2004/02,2004/04,2004/05,2004/06,2004/08-2004/12");
		Iterator iter = seq.getMonthsParts().iterator();
		assertEquals((Object) new Months(new Month(2004, MonthOfYear.JANUARY), 2), iter.next());
		assertEquals((Object) new Months(new Month(2004, MonthOfYear.APRIL), 3), iter.next());
		assertEquals((Object) new Months(new Month(2004, MonthOfYear.AUGUST), 5), iter.next());
		assertTrue(!iter.hasNext());
	}
}
