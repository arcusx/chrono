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
public class MonthSequenceTestCase extends TestCase
{
	public void testAddMonthInOrder() throws Exception
	{
		MonthSequence seq = new MonthSequence();
		seq.addMonth(new Month(2004, MonthOfYear.JANUARY));
		assertEquals((Object) new Months(new Month(2004, MonthOfYear.JANUARY), 1), seq.getMonthsParts().iterator().next());
		seq.addMonth(new Month(2004, MonthOfYear.FEBRUARY));
		assertEquals((Object) new Months(new Month(2004, MonthOfYear.JANUARY), 2), seq.getMonthsParts().iterator().next());
		seq.addMonth(new Month(2004, MonthOfYear.MARCH));
		assertEquals((Object) new Months(new Month(2004, MonthOfYear.JANUARY), 3), seq.getMonthsParts().iterator().next());
		seq.addMonth(new Month(2004, MonthOfYear.APRIL));
		assertEquals((Object) new Months(new Month(2004, MonthOfYear.JANUARY), 4), seq.getMonthsParts().iterator().next());
		seq.addMonth(new Month(2004, MonthOfYear.MAY));
		assertEquals((Object) new Months(new Month(2004, MonthOfYear.JANUARY), 5), seq.getMonthsParts().iterator().next());
		seq.addMonth(new Month(2004, MonthOfYear.JUNE));
		seq.addMonth(new Month(2004, MonthOfYear.JULY));
		seq.addMonth(new Month(2004, MonthOfYear.AUGUST));
		seq.addMonth(new Month(2004, MonthOfYear.SEPTEMBER));
		seq.addMonth(new Month(2004, MonthOfYear.OCTOBER));
		seq.addMonth(new Month(2004, MonthOfYear.NOVEMBER));
		seq.addMonth(new Month(2004, MonthOfYear.DECEMBER));
		assertEquals((Object) new Months(new Month(2004, MonthOfYear.JANUARY), 12), seq.getMonthsParts().iterator().next());
	}

	public void testAddMonthInReverseOrder() throws Exception
	{
		MonthSequence seq = new MonthSequence();
		seq.addMonth(new Month(2004, MonthOfYear.DECEMBER));
		assertEquals((Object) new Months(new Month(2004, MonthOfYear.DECEMBER), 1), seq.getMonthsParts().iterator().next());
		seq.addMonth(new Month(2004, MonthOfYear.NOVEMBER));
		assertEquals((Object) new Months(new Month(2004, MonthOfYear.NOVEMBER), 2), seq.getMonthsParts().iterator().next());
		seq.addMonth(new Month(2004, MonthOfYear.OCTOBER));
		assertEquals((Object) new Months(new Month(2004, MonthOfYear.OCTOBER), 3), seq.getMonthsParts().iterator().next());
	}

	public void testAddPartsInOrder() throws Exception
	{
		MonthSequence seq = new MonthSequence();
		seq.addMonths(new Months(new Month(2004, MonthOfYear.JANUARY), 1));
		assertEquals((Object) new Months(new Month(2004, MonthOfYear.JANUARY), 1), seq.getMonthsParts().iterator().next());
		seq.addMonths(new Months(new Month(2004, MonthOfYear.FEBRUARY), 1));
		assertEquals((Object) new Months(new Month(2004, MonthOfYear.JANUARY), 2), seq.getMonthsParts().iterator().next());
		seq.addMonths(new Months(new Month(2004, MonthOfYear.MARCH), 1));
		assertEquals((Object) new Months(new Month(2004, MonthOfYear.JANUARY), 3), seq.getMonthsParts().iterator().next());
	}

	public void testAddPartsWithSpace() throws Exception
	{
		MonthSequence seq = new MonthSequence();
		seq.addMonths(new Months(new Month(2004, MonthOfYear.JANUARY), 2));
		assertEquals((Object) new Months(new Month(2004, MonthOfYear.JANUARY), 2), seq.getMonthsParts().iterator().next());

		seq.addMonths(new Months(new Month(2004, MonthOfYear.APRIL), 2));
		Iterator iter = seq.getMonthsParts().iterator();
		assertEquals((Object) new Months(new Month(2004, MonthOfYear.JANUARY), 2), iter.next());
		assertEquals((Object) new Months(new Month(2004, MonthOfYear.APRIL), 2), iter.next());
		assertTrue(!iter.hasNext());

		seq.addMonth(new Month(2004, MonthOfYear.MARCH));
		iter = seq.getMonthsParts().iterator();
		assertEquals((Object) new Months(new Month(2004, MonthOfYear.JANUARY), 5), iter.next());
		assertTrue(!iter.hasNext());
	}
	
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
